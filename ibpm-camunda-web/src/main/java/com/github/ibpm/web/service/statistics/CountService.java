package com.github.ibpm.web.service.statistics;

import com.github.ibpm.common.param.statistics.CountInstanceParam;
import com.github.ibpm.common.result.MapResult;
import com.github.ibpm.config.util.BeanUtil;
import com.github.ibpm.web.dao.statistics.CountMapper;
import com.github.ibpm.sys.service.BaseServiceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CountService extends BaseServiceAdapter {

    @Autowired
    private CountMapper mapper;

    public List<MapResult<String, Integer>> getUserCount() {
        return mapper.getUserCount();
    }

    public List<MapResult<String, Integer>> getProcessCount() {
        Map<String, Object> paramMap = BeanUtil.bean2Map(null);
        return mapper.getProcessCount(paramMap);
    }

    public List<MapResult<String, Integer>> getInstanceCount(CountInstanceParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        return mapper.getInstanceCount(paramMap);
    }

}
