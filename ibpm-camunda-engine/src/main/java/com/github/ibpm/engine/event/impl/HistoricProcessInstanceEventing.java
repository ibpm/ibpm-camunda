package com.github.ibpm.engine.event.impl;

import com.github.ibpm.common.enums.InstanceStatus;
import com.github.ibpm.common.result.core.instance.Instance;
import com.github.ibpm.config.bean.SpringContextAware;
import com.github.ibpm.engine.event.EndEventing;
import com.github.ibpm.engine.event.EventingAdapter;
import com.github.ibpm.engine.service.instance.InstanceService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.impl.history.event.HistoricProcessInstanceEventEntity;

@Slf4j
public class HistoricProcessInstanceEventing extends EventingAdapter<HistoricProcessInstanceEventEntity> implements EndEventing<HistoricProcessInstanceEventEntity> {

    public HistoricProcessInstanceEventing(HistoricProcessInstanceEventEntity historyEvent) {
        super(historyEvent);
    }

    @Override
    public void end() {
        InstanceService instanceService = SpringContextAware.getBean(InstanceService.class);
        instanceService.updateDone(
                new Instance()
                        .setProcessInstanceId(historyEvent.getProcessInstanceId())
                        .setStatus(InstanceStatus.DONE.getStatus()));
    }
}
