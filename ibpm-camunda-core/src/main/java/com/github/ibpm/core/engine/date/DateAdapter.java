package com.github.ibpm.core.engine.date;

import com.github.ibpm.common.constant.CommonConstants;
import com.github.ibpm.common.param.core.calendar.DayCalendarListParam;
import com.github.ibpm.common.result.core.calendar.DayCalendarConfig;
import com.github.ibpm.common.util.DateTimeUtil;
import com.github.ibpm.config.bean.SpringContextAware;
import com.github.ibpm.core.service.core.CalendarService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public abstract class DateAdapter implements DateHandler {

    protected String calendarName;

    protected Calendar cal = Calendar.getInstance();

    protected Calendar lowerCal;

    protected Calendar upperCal;

    protected int periodOffset;

    protected int dayOrder;

    @Override
    public Integer calculateTradeDate() {
        addPeriodOffset();
        if (dayOrder == 0) {
            return getDate(cal.getTime());
        }
        computeRange();
        List<Integer> tradeDays = computeTradeDays();
        if (Math.abs(dayOrder) < tradeDays.size()) {
            if (dayOrder > 0) {
                return tradeDays.get(dayOrder - 1);
            } else if (dayOrder < 0) {
                return tradeDays.get(tradeDays.size() + dayOrder);
            }
        }
        return null;
    }

    protected abstract void addPeriodOffset();

    protected abstract void computeRange();

    protected Calendar copyCalendar(Calendar cal) {
        Calendar copiedCalendar = Calendar.getInstance();
        copiedCalendar.setTime(cal.getTime());
        return copiedCalendar;
    }

    protected List<Integer> computeTradeDays() {
        List<Integer> tradeDays = new ArrayList<>();
        List<Integer> excludedDates = getExcludedDates();
        Calendar _lowerCal = copyCalendar(lowerCal);
        if (excludedDates.isEmpty()) {
            while (!_lowerCal.getTime().after(upperCal.getTime())) {
                tradeDays.add(getDate(_lowerCal.getTime()));
                _lowerCal.add(Calendar.DAY_OF_YEAR, 1);
            }
        } else {
            int index = 0;
            while (index < excludedDates.size() && !_lowerCal.getTime().after(upperCal.getTime())) {
                int current = getDate(_lowerCal.getTime());
                while (current < excludedDates.get(index)) {
                    tradeDays.add(current);
                    current++;
                }
                index++;
                _lowerCal.add(Calendar.DAY_OF_YEAR, 1);
            }
        }
        return tradeDays;
    }

    protected List<Integer> getExcludedDates() {
        if (calendarName == null) {
            return new ArrayList<>(0);
        }
        CalendarService calendarService = SpringContextAware.getBean(CalendarService.class);
        DayCalendarListParam dayCalendarListParam = new DayCalendarListParam()
                .setLowerDayNum(getDate(lowerCal.getTime()))
                .setUpperDayNum(getDate(upperCal.getTime()));
        dayCalendarListParam.setCalendarName(calendarName);
        List<DayCalendarConfig> dayCalendarConfigs = calendarService.listDay(dayCalendarListParam);
        return dayCalendarConfigs.stream().map(DayCalendarConfig::getDayNum).collect(Collectors.toList());
    }

    protected int getDate(Date date) {
        return Integer.parseInt(DateTimeUtil.formatDate(date, CommonConstants.yyyyMMdd));
    }

    public void setPeriodOffset(int periodOffset) {
        this.periodOffset = periodOffset;
    }

    public void setDayOrder(int dayOrder) {
        this.dayOrder = dayOrder;
    }

    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }
}
