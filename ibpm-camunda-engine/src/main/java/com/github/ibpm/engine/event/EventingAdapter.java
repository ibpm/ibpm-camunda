package com.github.ibpm.engine.event;

import org.camunda.bpm.engine.impl.history.event.HistoryEvent;

public abstract class EventingAdapter<T extends HistoryEvent> {

    protected T historyEvent;

    public EventingAdapter(T historyEvent) {
        this.historyEvent = historyEvent;
    }
}
