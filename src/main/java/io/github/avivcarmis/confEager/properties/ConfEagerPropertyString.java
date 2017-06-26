package io.github.avivcarmis.confEager.properties;

import io.github.avivcarmis.confEager.ConfEager;
import io.github.avivcarmis.confEager.properties.utils.ConfEagerPropertyPrimitive;
import io.github.avivcarmis.confEager.properties.utils.ConfEagerValueMapper;

/**
 * Out of the box {@link io.github.avivcarmis.confEager.ConfEagerProperty}
 * that maps {@link String} values.
 */
public class ConfEagerPropertyString extends ConfEagerPropertyPrimitive<String> {

    public ConfEagerPropertyString(ConfEager.DefaultValue<String> defaultValue, ConfEager.PropertyName propertyName) {
        super(defaultValue, propertyName, ConfEagerValueMapper.STRING_MAPPER);
    }

    public ConfEagerPropertyString(ConfEager.PropertyName propertyName, ConfEager.DefaultValue<String> defaultValue) {
        super(propertyName, defaultValue, ConfEagerValueMapper.STRING_MAPPER);
    }

    public ConfEagerPropertyString(ConfEager.PropertyName propertyName) {
        super(propertyName, ConfEagerValueMapper.STRING_MAPPER);
    }

    public ConfEagerPropertyString(ConfEager.DefaultValue<String> defaultValue) {
        super(defaultValue, ConfEagerValueMapper.STRING_MAPPER);
    }

    public ConfEagerPropertyString() {
        super(ConfEagerValueMapper.STRING_MAPPER);
    }

}
