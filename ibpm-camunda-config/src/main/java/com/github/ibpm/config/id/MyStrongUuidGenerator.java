package com.github.ibpm.config.id;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

/**
 * forked from StrongUuidGenerator
 */
public class MyStrongUuidGenerator implements MyIdGenerator {

    // different ProcessEngines on the same classloader share one generator.
    protected static TimeBasedGenerator timeBasedGenerator;

    public MyStrongUuidGenerator() {
        ensureGeneratorInitialized();
    }

    protected void ensureGeneratorInitialized() {
        if (timeBasedGenerator == null) {
            synchronized (MyStrongUuidGenerator.class) {
                if (timeBasedGenerator == null) {
                    timeBasedGenerator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
                }
            }
        }
    }

    @Override
    public String getNextId() {
        return timeBasedGenerator.generate().toString();
    }
}
