package io.github.avivcarmis.confEager.properties;

import io.github.avivcarmis.confEager.ConfEager;
import io.github.avivcarmis.confEager.properties.utils.ConfEagerPropertyPrimitive;
import io.github.avivcarmis.confEager.properties.utils.ConfEagerValueMapper;

/**
 * Out of the box {@link io.github.avivcarmis.confEager.ConfEagerProperty}
 * that maps {@link Double} values.
 */
public class ConfEagerPropertyDouble extends ConfEagerPropertyPrimitive<Double> {

    public ConfEagerPropertyDouble(ConfEager.DefaultValue<Double> defaultValue, ConfEager.PropertyName propertyName) {
        super(defaultValue, propertyName, ConfEagerValueMapper.DOUBLE_MAPPER);
    }

    public ConfEagerPropertyDouble(ConfEager.PropertyName propertyName, ConfEager.DefaultValue<Double> defaultValue) {
        super(propertyName, defaultValue, ConfEagerValueMapper.DOUBLE_MAPPER);
    }

    public ConfEagerPropertyDouble(ConfEager.PropertyName propertyName) {
        super(propertyName, ConfEagerValueMapper.DOUBLE_MAPPER);
    }

    public ConfEagerPropertyDouble(ConfEager.DefaultValue<Double> defaultValue) {
        super(defaultValue, ConfEagerValueMapper.DOUBLE_MAPPER);
    }

    public ConfEagerPropertyDouble() {
        super(ConfEagerValueMapper.DOUBLE_MAPPER);
    }

}
