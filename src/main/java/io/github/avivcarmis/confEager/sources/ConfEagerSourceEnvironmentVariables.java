package io.github.avivcarmis.confEager.sources;

import io.github.avivcarmis.confEager.ConfEagerSource;

/**
 * Out of the box source to map Environment Variables
 */
public class ConfEagerSourceEnvironmentVariables extends ConfEagerSource {

    public static final ConfEagerSource INSTANCE = new ConfEagerSourceEnvironmentVariables();

    @Override
    public String getValueOrNull(String propertyName) {
        return System.getenv(propertyName);
    }

}
