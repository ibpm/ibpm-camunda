package com.github.ibpm.engine.event;

import org.camunda.bpm.engine.impl.history.event.HistoryEvent;

/**
 * when start event happened,may add a data to db...
 * @param <T>
 */
public interface StartEventing<T extends HistoryEvent> {

    void start();
}
