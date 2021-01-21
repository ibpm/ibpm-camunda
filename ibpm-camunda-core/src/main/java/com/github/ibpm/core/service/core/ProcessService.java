package com.github.ibpm.core.service.core;

import com.github.ibpm.common.param.core.process.ProcessDefinitionKeyParam;
import com.github.ibpm.common.result.core.process.ProcessModel;
import com.github.ibpm.config.util.BeanUtil;
import com.github.ibpm.core.dao.core.ProcessMapper;
import com.github.ibpm.sys.service.BaseServiceAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@Transactional
public class ProcessService extends BaseServiceAdapter {

    @Autowired
    private ProcessMapper mapper;

    /**
     * get process by processDefinitionKey
     * @param param
     * @return
     */
    public ProcessModel get(ProcessDefinitionKeyParam param) {
        Map<String, Object> paramMap = BeanUtil.bean2Map(param);
        return mapper.get(paramMap);
    }

    public ProcessModel get(String processDefinitionKey) {
        return mapper.get(BeanUtil.bean2Map(new ProcessDefinitionKeyParam().setProcessDefinitionKey(processDefinitionKey)));
    }

}
