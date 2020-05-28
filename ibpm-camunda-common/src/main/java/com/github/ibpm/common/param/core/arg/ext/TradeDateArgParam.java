package com.github.ibpm.common.param.core.arg.ext;

import com.github.ibpm.common.param.CommonParam;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

@ToString
public class TradeDateArgParam implements CommonParam {

    protected String calendarName;

    protected String expression;

    private static Pattern tradeDatePattern = Pattern.compile("^([TWMSHY]{1})((([+-])([0123456789]*))?)(( ((([+-])([0123456789]*))?))?)$");;

    @Override
    public String validate() {
        if (StringUtils.isNotBlank(expression)) {
            if (!tradeDatePattern.matcher(expression).find()) {
                return "7040";
            }
        } else {
            return "7040";
        }
        return null;
    }

    public String getCalendarName() {
        return calendarName;
    }

    public TradeDateArgParam setCalendarName(String calendarName) {
        this.calendarName = calendarName;
        return this;
    }

    public String getExpression() {
        return expression;
    }

    public TradeDateArgParam setExpression(String expression) {
        this.expression = expression;
        return this;
    }
}
