package com.github.ibpm.core.service.core;

import com.github.ibpm.common.param.core.calendar.CalendarNameParam;
import com.github.ibpm.common.param.core.calendar.DayCalendarListParam;
import com.github.ibpm.common.result.core.calendar.CalendarInfo;
import com.github.ibpm.common.result.core.calendar.DayCalendarConfig;
import com.github.ibpm.core.dao.core.CalendarMapper;
import com.github.ibpm.sys.service.BaseServiceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarService extends BaseServiceAdapter {

    @Autowired
    private CalendarMapper mapper;

    public List<CalendarInfo> list() {
        return mapper.list();
    }

    public CalendarInfo get(CalendarNameParam param) {
        return mapper.get(param);
    }

    public List<DayCalendarConfig> listDay(DayCalendarListParam param) {
        return mapper.listDay(param);
    }
}
