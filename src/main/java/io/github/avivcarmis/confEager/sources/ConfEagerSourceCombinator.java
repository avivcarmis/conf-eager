package io.github.avivcarmis.confEager.sources;

import io.github.avivcarmis.confEager.ConfEagerSource;

import java.util.Arrays;
import java.util.List;

/**
 * Out of the box source to receive other sources,
 * and chain them one after the other for each property,
 * until it is found in either of them.
 */
public class ConfEagerSourceCombinator extends ConfEagerSource {

    private final List<ConfEagerSource> _sources;

    public ConfEagerSourceCombinator(List<ConfEagerSource> sources) {
        _sources = sources;
    }

    public ConfEagerSourceCombinator(ConfEagerSource... sources) {
        _sources = Arrays.asList(sources);
    }

    @Override
    public String getValueOrNull(String propertyName) {
        for (ConfEagerSource source : _sources) {
            String value = source.getValueOrNull(propertyName);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

}
