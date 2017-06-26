package io.github.avivcarmis.confEager.sources;

import io.github.avivcarmis.confEager.ConfEagerSource;

/**
 * Out of the box source to map System Properties
 */
public class ConfEagerSourceSystemProperties extends ConfEagerSource {

    public static final ConfEagerSource INSTANCE = new ConfEagerSourceSystemProperties();

    @Override
    public String getValueOrNull(String propertyName) {
        return System.getProperty(propertyName);
    }

}
