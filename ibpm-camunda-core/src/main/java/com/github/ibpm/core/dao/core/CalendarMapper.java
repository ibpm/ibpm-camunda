package com.github.ibpm.core.dao.core;

import com.github.ibpm.common.param.core.calendar.CalendarNameParam;
import com.github.ibpm.common.param.core.calendar.DayCalendarListParam;
import com.github.ibpm.common.param.core.calendar.DayCalendarRemoveParam;
import com.github.ibpm.common.result.core.calendar.CalendarInfo;
import com.github.ibpm.common.result.core.calendar.DayCalendarConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CalendarMapper {

    CalendarInfo get(CalendarNameParam param);

    List<CalendarInfo> list();

    void saveDay(Map<String,Object> paramMap);

    int deleteDay(DayCalendarRemoveParam param);

    List<DayCalendarConfig> listDay(DayCalendarListParam param);

    int deleteDays(Map<String, Object> paramMap);

    void addDays(List<Map<String, Object>> paramMap);
}