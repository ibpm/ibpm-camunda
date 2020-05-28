package com.github.ibpm.sys.aspect;

import com.github.ibpm.common.constant.CommonConstants;
import com.github.ibpm.common.constant.GlobalConstants;
import com.github.ibpm.common.exception.RTException;
import com.github.ibpm.common.param.CommonParam;
import com.github.ibpm.common.param.sys.login.LoginParam;
import com.github.ibpm.common.result.CommonResult;
import com.github.ibpm.common.result.sys.user.User;
import com.github.ibpm.config.annotation.IgnoreLogResult;
import com.github.ibpm.config.entity.ApiLog;
import com.github.ibpm.config.util.AspectUtil;
import com.github.ibpm.config.util.IPUtil;
import com.github.ibpm.config.util.ServletUtil;
import com.github.ibpm.config.util.StringUtil;
import com.github.ibpm.sys.ext.jwt.JWTService;
import com.github.ibpm.sys.holder.UserHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;

@Slf4j
@Component
@Aspect
public class ControllerAspect {

    @Autowired
    private JWTService jwtService;

    private static Validator validator = Validation
            .byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory().getValidator();

    @Pointcut("(execution(public * " + GlobalConstants.basePackageLocation + "*.controller..*Controller.*(..)))"
    )
    public void aroundController() {

    }

    @Around("aroundController()")
    public CommonResult around(ProceedingJoinPoint joinPoint) throws Throwable {
        ApiLog apiLog = new ApiLog();
        CommonResult result = checkParam(joinPoint, apiLog);
        if (result != null) {
            return result;
        }
        return executeMethod(joinPoint, apiLog);
    }

    private CommonResult executeMethod(ProceedingJoinPoint joinPoint, ApiLog apiLog) throws Throwable {
        CommonResult result = null;
        Instant begin = Instant.now();
        try {
            result = (CommonResult) joinPoint.proceed();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            IgnoreLogResult ignoreLogResult = method.getAnnotation(IgnoreLogResult.class);
            if (result != null && result.getResult() != null && (ignoreLogResult == null || !ignoreLogResult.value())) {
                apiLog.setResult(result.getResult().toString());
            }
            return result;
        } catch (RTException rte) {
            result = CommonResult.error(rte);
            throw rte;
        } catch (Exception e){
            result = CommonResult.error(e.getMessage());
            log.error(e.getMessage(), e);
            throw e;
        }finally {
            if (result != null) {
                Instant end = Instant.now();
                result.setDuration(String.valueOf(Duration.between(begin, end).toMillis() / 1000.0));
            }
            UserHolder.clear();
            AspectUtil.saveLog(apiLog, result);
        }
    }

    private CommonResult checkParam(ProceedingJoinPoint joinPoint, ApiLog apiLog) {
        CommonResult result = null;
        preHandleLog(joinPoint, apiLog);
        Object[] args = joinPoint.getArgs();
        boolean isLogin = false;
        if (args != null) {
            for (Object arg : args) {
                if (arg != null) {
                    Set<ConstraintViolation<Object>> violations = validator.validate(arg);
                    if (!violations.isEmpty()) {
                        ConstraintViolation<Object> violation = violations.iterator().next();
                        String codeStr = violation.getMessage();
                        return CommonResult.put(Integer.valueOf(codeStr));
                    }
                    if (arg instanceof CommonParam) {
                        CommonParam baseParam = (CommonParam) arg;
                        baseParam.preHandle();
                        String codeStr = baseParam.validate();
                        if (codeStr != null) {
                            try {
                                int code = Integer.parseInt(codeStr);
                                return CommonResult.put(code);
                            } catch (NumberFormatException e) {
                                return CommonResult.error(codeStr);
                            }
                        }
                        if (!isLogin && arg instanceof LoginParam) {
                            isLogin = true;
                        }
                    }
                }
            }
        }
        User user = UserHolder.get();
        if (user == null && !isLogin) {
            String token = ServletUtil.getHeader(CommonConstants.token);
            if (StringUtils.isNotBlank(token)) {
                user = jwtService.parseToken(token);
                UserHolder.set(user);
            }
        }
        if (user != null) {
            apiLog.setUserName(user.getUserName());
        }
        return result;
    }

    private void preHandleLog(ProceedingJoinPoint joinPoint, ApiLog apiLog) {
        apiLog.setIp(IPUtil.getIpAddr());
        apiLog.setParam(StringUtil.formatToJsonStr(joinPoint.getArgs()));

        Class<?> clazz = joinPoint.getTarget().getClass();
        Tag api = clazz.getAnnotation(Tag.class);
        if (api != null) {
            apiLog.setTag(api.name());
        }
        apiLog.setClassName(clazz.getName());

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Operation apiOperation = method.getAnnotation(Operation.class);
        if (apiOperation != null) {
            apiLog.setOperation(apiOperation.description());
        }
        apiLog.setMethod(method.getName());
        AspectUtil.resolvePath(method, apiLog);
    }
}
