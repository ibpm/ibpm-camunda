package com.github.ibpm.engine.conf;

import com.github.ibpm.engine.event.impl.HistoricProcessInstanceEventing;
import org.camunda.bpm.engine.impl.history.event.HistoricProcessInstanceEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.history.event.HistoryEventTypes;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SpringEventBusListener {

    @EventListener
    public void onHistoryEvent(HistoryEvent historyEvent) {
        handleEventIntern(historyEvent);
    }

    private void handleEventIntern(HistoryEvent historyEvent) {
        if (historyEvent instanceof HistoricProcessInstanceEventEntity) {
            HistoricProcessInstanceEventing eventing = new HistoricProcessInstanceEventing((HistoricProcessInstanceEventEntity) historyEvent);
            /*if (HistoryEventTypes.PROCESS_INSTANCE_START.getEventName().equals(historyEvent.getEventType())) {
                eventing.start();
            } else */if (HistoryEventTypes.PROCESS_INSTANCE_END.getEventName().equals(historyEvent.getEventType())) {
                eventing.end();
            }
        }
    }
}
