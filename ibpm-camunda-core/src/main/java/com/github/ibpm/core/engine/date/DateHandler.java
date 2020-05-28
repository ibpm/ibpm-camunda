package com.github.ibpm.core.engine.date;

public interface DateHandler {

    Integer calculateTradeDate();

    void setCalendarName(String calendarName);

    void setPeriodOffset(int periodOffset);

    void setDayOrder(int dayOrder);
}
