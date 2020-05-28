package com.github.ibpm.common.param.core.calendar;

import com.github.ibpm.common.param.CommonParam;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;

@ToString
public class CalendarNameParam implements CommonParam {

    protected String calendarName;

    @Override
    public String validate() {
        if (StringUtils.isBlank(calendarName)) {
            return "6700";
        }
        return null;
    }

    public String getCalendarName() {
        return calendarName;
    }

    public CalendarNameParam setCalendarName(String calendarName) {
        this.calendarName = calendarName;
        return this;
    }
}
