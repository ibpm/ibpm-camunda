package com.github.ibpm.common.param.core.calendar;

import com.github.ibpm.common.constant.CommonConstants;
import com.github.ibpm.common.util.DateTimeUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DayCalendarRemoveParam extends CalendarNameParam {

    protected Integer dayNum;

    @Override
    public String validate() {
        if (DateTimeUtil.parseDateStr(String.valueOf(dayNum), CommonConstants.yyyyMMdd) == null) {
            return "6705";
        }
        return super.validate();
    }
}
