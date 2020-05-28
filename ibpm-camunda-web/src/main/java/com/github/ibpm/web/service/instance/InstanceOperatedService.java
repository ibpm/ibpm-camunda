package com.github.ibpm.web.service.instance;

import com.github.ibpm.common.enums.InstanceStatus;
import com.github.ibpm.common.param.IdsParam;
import com.github.ibpm.common.param.core.instance.InstanceIdParam;
import com.github.ibpm.common.property.StatusProperty;
import com.github.ibpm.common.result.core.instance.Instance;
import com.github.ibpm.config.util.BeanUtil;
import com.github.ibpm.core.service.core.JobService;
import com.github.ibpm.core.service.instance.InstanceService;
import com.github.ibpm.web.ext.app.ExecutorHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class InstanceOperatedService {

    @Autowired
    private InstanceService instanceService;

    @Autowired
    private ExecutorHandler executorHandler;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private JobService jobService;

    public String getInstanceArgs(InstanceIdParam param) {
        Instance instance = instanceService.getByInstId(param.getProcInstId());
        Map<String, Object> argMap = jobService.getArgsAsMap(instance.getJobName());
        if (argMap.size() == 0) {
            return null;
        }
        HistoricVariableInstanceQuery varQuery = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(param.getProcInstId())
                .activityInstanceIdIn(param.getProcInstId());
        List<HistoricVariableInstance> vars = varQuery.list();
        if (vars.size() == 0) {
            return BeanUtil.bean2JsonStr(argMap);
        }
        /**
         * Using instance arg value to replace job arg value, based on the same key in job arg
         */
        for (Map.Entry<String, Object> entry : argMap.entrySet()) {
            for (HistoricVariableInstance var : vars) {
                if (entry.getKey().equals(var.getName())) {
                    entry.setValue(var.getValue());
                    break;
                }
            }
        }
        return BeanUtil.bean2JsonStr(argMap);
    }

    public Void terminate(IdsParam param) {
        List<Instance> instances = instanceService.getByIds(param.getIds());
        for (int i = instances.size() - 1; i >= 0; i--) {
            Instance instance = instances.get(i);
            if (!isDoing(instance.getStatus())) {
                instances.remove(i);
                continue;
            }
            int code = 930;
            long current = System.currentTimeMillis();
            instance.setCode(code)
                    .setMsg(StatusProperty.getValue(code))
                    .setStatus(InstanceStatus.TERMINATED.getStatus())
                    .setEndTime(current)
                    .setDuration(current - instance.getStartTime());
            instanceService.updateDone(instance);
            if (StringUtils.isBlank(instance.getProcInstId())) {
                instances.remove(i);
            }
        }
        for (Instance instance : instances) {
            executorHandler.terminate(instance);
        }
        return null;
    }

    private boolean isDoing(int status) {
        return status == InstanceStatus.RUNNING.getStatus();
    }
}
