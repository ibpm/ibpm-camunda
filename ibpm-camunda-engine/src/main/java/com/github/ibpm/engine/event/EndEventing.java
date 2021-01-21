package com.github.ibpm.engine.event;

import org.camunda.bpm.engine.impl.history.event.HistoryEvent;

/**
 * when end event happened,may update the data of db...
 * @param <T>
 */
public interface EndEventing<T extends HistoryEvent> {

    void end();
}
