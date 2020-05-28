package com.github.ibpm.core.service.core;

import com.github.ibpm.common.enums.ArgType;
import com.github.ibpm.common.exception.RTException;
import com.github.ibpm.common.param.core.arg.ArgListParam;
import com.github.ibpm.common.param.core.arg.ArgNameParam;
import com.github.ibpm.common.param.core.arg.ArgNamesParam;
import com.github.ibpm.common.param.core.arg.ArgSaveParam;
import com.github.ibpm.common.param.core.arg.ext.TradeDateArgParam;
import com.github.ibpm.common.result.core.arg.Arg;
import com.github.ibpm.config.util.BeanUtil;
import com.github.ibpm.core.dao.core.ArgMapper;
import com.github.ibpm.core.engine.DateCalculatorFactory;
import com.github.ibpm.core.engine.date.DateHandler;
import com.github.ibpm.sys.service.BaseServiceAdapter;
import com.github.ibpm.sys.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class ArgService extends BaseServiceAdapter {

    @Autowired
    private ArgMapper mapper;

    public Arg get(ArgNameParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        return mapper.get(paramMap);
    }

    public Arg add(ArgSaveParam param) {
        Arg arg = get(new ArgNameParam().setArgName(param.getArgName()));
        if (arg != null) {
            throw new DuplicateKeyException(param.getArgName());
        }
        arg = toArg(param);
        mapper.add(arg);
        return arg;
    }

    public void addBatch(List<Arg> args) {
        mapper.addBatch(args);
    }

    public Arg update(ArgSaveParam param) {
        Arg oldArg = get(new ArgNameParam().setArgName(param.getArgName()));
        if (oldArg == null) {
            return add(param);
        }
        Arg updatedArg = toArg(param);
        mapper.update(updatedArg);
        return updatedArg;
    }

    public Map<String, Object> list(ArgListParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        PageHelper.startPage(param.getCurrentPage(), param.getPageSize());
        Page<Arg> list = (Page<Arg>) mapper.list(paramMap);
        return PageUtil.toResultMap(list);
    }

    public Void remove(ArgNamesParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        mapper.delete(paramMap);
        return null;
    }

    public Map<String, Object> listStartsWith(String argNamePrefix) {
        Arg arg = new Arg().setArgName(argNamePrefix);
        List<Arg> args = mapper.listStartsWith(BeanUtil.bean2Map(arg));
        return transArgsToMap(args);
    }

    private Arg toArg(ArgSaveParam param) {
        return new Arg()
                .setArgName(param.getArgName())
                .setArgType(param.getArgType())
                .setArgValue(param.getArgValue())
                .setGenericType(param.getGenericType())
                .setAttribute(param.getAttribute())
                .setRemark(param.getRemark());
    }

    public Integer getTradeDate(TradeDateArgParam param) {
        String expression = param.getExpression().trim();
        DateHandler dateHandler = DateCalculatorFactory.getDateHandler(expression.substring(0, 1));
        dateHandler.setCalendarName(param.getCalendarName());
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine scriptEngine = manager.getEngineByName("js");
        if (expression.length() > 1) {
            String[] arr = expression.substring(1).split(" ");
            try {
                if (arr[0].length() > 1) {
                    dateHandler.setPeriodOffset((Integer) scriptEngine.eval(arr[0].trim()));
                }
                if (arr.length > 1 && arr[1].length() > 1) {
                    dateHandler.setDayOrder((Integer) scriptEngine.eval(arr[1].trim()));
                }
            } catch (ScriptException e) {
                throw new RTException(e);
            }
        }
        return dateHandler.calculateTradeDate();
    }

    public Map<String, Object> transArgsToMap(List<Arg> args) {
        Map<String, Object> varMap = new HashMap<>();
        for (Arg arg : args) {
            if (arg.getArgValue() != null) {
                if (ArgType.get(arg.getArgType()) == null) {
                    arg.setArgType(ArgType.STRING.getValue());
                }
                Object realArgValue;
                switch (ArgType.get(arg.getArgType())) {
                    case TRADE_DATE:
                        realArgValue = getTradeDate(
                                new TradeDateArgParam()
                                        .setCalendarName(arg.getAttribute())
                                        .setExpression(arg.getArgValue()));
                        break;
                    case LIST:
                        realArgValue = toListValue(arg);
                        break;
                    case MAP:
                        realArgValue = toMapValue(arg);
                        break;
                    default: //string date datetime time
                        realArgValue = toGenericValue(arg.getArgType(), arg.getArgValue());
                        break;
                }
                varMap.put(arg.getArgName(), realArgValue);
            }
        }
        return varMap;
    }

    private Object toGenericValue(int argType, String argValue) {
        if (ArgType.get(argType) == null) {
            argType = ArgType.STRING.getValue();
        }
        try {
            switch (ArgType.get(argType)) {
                case BOOLEAN:
                    return Boolean.parseBoolean(argValue);
                case INTEGER:
                    return Integer.parseInt(argValue);
                case DOUBLE:
                    return Double.parseDouble(argValue);
                case LONG:
                    return Long.parseLong(argValue);
                default: //string date datetime time
                    return argValue;
            }
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
            return argValue;
        }
    }

    private List<Object> toListValue(Arg arg) {
        List<Object> list = new ArrayList<>();
        String[] values = arg.getArgValue().split(",,");
        for (String value : values) {
            list.add(toGenericValue(arg.getGenericType(), value));
        }
        return list;
    }

    private Map<String, Object> toMapValue(Arg arg) {
        Map<String, Object> map = new HashMap<>();
        String[] entries = arg.getArgValue().split(",,");
        for (String entry : entries) {
            String[] keyValueArray = entry.split("::");
            if (keyValueArray.length > 1) {
                map.put(keyValueArray[0], toGenericValue(arg.getGenericType(), keyValueArray[1]));
            }
        }
        return map;
    }

}
