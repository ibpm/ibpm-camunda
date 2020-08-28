package com.github.ibpm.web.service.core;

import com.github.ibpm.common.constant.CommonConstants;
import com.github.ibpm.common.exception.RTException;
import com.github.ibpm.common.param.core.calendar.DayCalendarRemoveParam;
import com.github.ibpm.common.param.core.calendar.DayCalendarSaveParam;
import com.github.ibpm.common.util.DateUtil;
import com.github.ibpm.config.util.BeanUtil;
import com.github.ibpm.core.dao.core.CalendarMapper;
import com.github.ibpm.engine.util.IoUtils;
import com.github.ibpm.sys.service.BaseServiceAdapter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CalendarOperatedService extends BaseServiceAdapter {

    @Autowired
    private CalendarMapper mapper;

    public Void saveDay(DayCalendarSaveParam param) {
        mapper.saveDay(BeanUtil.bean2Map(param));
        return null;
    }

    public Void removeDay(DayCalendarRemoveParam param) {
        mapper.deleteDay(param);
        return null;
    }

    public Void importDate(String calendarName, MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            InputStreamReader isr = new InputStreamReader(is, "GB18030");
            String lines = IoUtils.readerAsString(isr);
            String[] lineArray;
            if (lines.contains("\r\n")) {
                lineArray = lines.split("\r\n");
            } else if (lines.contains("\n")) {
                lineArray = lines.split("\n");
            } else {
                lineArray = new String[]{ lines };
            }
            if (lineArray.length == 0) {
                return null;
            }
            Integer[] days = new Integer[lineArray.length];
            List<Map<String, Object>> paramList = new ArrayList<>(lineArray.length);
            for (int i = 0; i < lineArray.length; i++) {
                String line = lineArray[i];
                if (StringUtils.isBlank(line)) {
                    throw new RTException(6706, i+1);
                }
                Map<String, Object> dayParamMap = new HashMap<>();
                dayParamMap.put(KEY_CALENDAR_NAME, calendarName);
                String dateStr;
                if (line.contains("|")) {
                    String[] columns = line.split("\\|");
                    if (columns.length > 2) {
                        throw new RTException(6711, line);
                    }
                    dateStr = columns[0].trim();
                    dayParamMap.put(KEY_REMARK, columns[1].trim());
                } else {
                    dateStr = line.trim();
                }
                if (dateStr.length() != 8 || DateUtil.parse(dateStr, CommonConstants.yyyyMMdd) == null) {
                    throw new RTException(6705, dateStr);
                }
                int day = Integer.parseInt(dateStr);
                days[i] = day;
                dayParamMap.put(KEY_DAY_NUM, day);
                paramList.add(dayParamMap);
            }
            // delete existed days
            Map<String, Object> paramMap = new HashMap<>(2);
            paramMap.put(KEY_CALENDAR_NAME, calendarName);
            paramMap.put(KEY_DAY_NUMS, days);
            mapper.deleteDays(paramMap);
            mapper.addDays(paramList);
        } catch (IOException e) {
            throw new RTException(6710, e);
        }
        return null;
    }

    private static final String KEY_CALENDAR_NAME = "calendarName";

    private static final String KEY_DAY_NUMS = "dayNums";

    private static final String KEY_DAY_NUM = "dayNum";

    private static final String KEY_REMARK = "remark";


}
