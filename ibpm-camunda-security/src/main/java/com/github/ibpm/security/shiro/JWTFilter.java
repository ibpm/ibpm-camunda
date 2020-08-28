package com.github.ibpm.security.shiro;

import com.github.ibpm.common.constant.CommonConstants;
import com.github.ibpm.common.exception.RTException;
import com.github.ibpm.common.result.CommonResult;
import com.github.ibpm.config.bean.SpringContextAware;
import com.github.ibpm.config.entity.ApiLog;
import com.github.ibpm.config.service.ApiLogService;
import com.github.ibpm.config.util.BeanUtil;
import com.github.ibpm.config.util.IPUtil;
import com.github.ibpm.config.util.ServletUtil;
import com.github.ibpm.security.model.JWTToken;
import com.github.ibpm.sys.exception.JWTDecodedException;
import com.github.ibpm.sys.exception.JWTExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * json web token filter of shiro
 */
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {

    /**
     * step1:validate token
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        if (StringUtils.isBlank(WebUtils.toHttp(request).getHeader(CommonConstants.token))) {
            return printError(response, 1002);
        }
        return super.preHandle(request, response);
    }

    /**
     * setp2.1onAccessDenied will be executed when return false
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    /**
     * step2.2:has token,allow login
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        try{
            executeLogin(request, response);
        } catch (JWTDecodedException e) {
            return printError(response, 1001);
        } catch (JWTExpiredException e) {
            return printError(response, 1000);
        } catch (RTException e) {
            return printError(response, e.getCode());
        }
        return true;
    }

    /**
     * step3:login
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        String accessToken = WebUtils.toHttp(request).getHeader(CommonConstants.token);
        JWTToken token = new JWTToken(accessToken);
        SecurityUtils.getSubject().login(token);  // jumt to real
        return true;
    }

    /**
     * print exception to front-end
     * @param response
     * @param code
     * @return
     */
    private boolean printError(ServletResponse response, int code) {
        try {
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            CommonResult result = CommonResult.put(code);
            String json = BeanUtil.bean2JsonStr(result);
            response.getWriter().print(json);
            saveLog(result);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * save error log
     * @param result
     */
    private void saveLog(CommonResult result) {
        ApiLog apiLog = ApiLog.builder()
                .code(result.getCode())
                .msg(result.getMsg())
                .duration(result.getDuration())
                .handleTime(result.getHandleTime())
                .className(this.getClass().getName())
                .param(ServletUtil.getHeader(CommonConstants.token))
                .ip(IPUtil.getIpAddr())
                .build();
        SpringContextAware.getBean(ApiLogService.class).save(apiLog);
    }
}
