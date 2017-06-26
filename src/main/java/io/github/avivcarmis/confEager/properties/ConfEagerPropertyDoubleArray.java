package io.github.avivcarmis.confEager.properties;

import io.github.avivcarmis.confEager.ConfEager;
import io.github.avivcarmis.confEager.properties.utils.ConfEagerPropertyPrimitiveArray;
import io.github.avivcarmis.confEager.properties.utils.ConfEagerValueMapper;

/**
 * Out of the box {@link io.github.avivcarmis.confEager.ConfEagerProperty}
 * that maps {@link Double} array values.
 */
public class ConfEagerPropertyDoubleArray extends ConfEagerPropertyPrimitiveArray<Double> {

    public ConfEagerPropertyDoubleArray(ConfEager.DefaultValue<Double[]> defaultValue, ConfEager.PropertyName propertyName) {
        super(defaultValue, propertyName, ConfEagerValueMapper.DOUBLE_MAPPER, Double.class);
    }

    public ConfEagerPropertyDoubleArray(ConfEager.PropertyName propertyName, ConfEager.DefaultValue<Double[]> defaultValue) {
        super(propertyName, defaultValue, ConfEagerValueMapper.DOUBLE_MAPPER, Double.class);
    }

    public ConfEagerPropertyDoubleArray(ConfEager.PropertyName propertyName) {
        super(propertyName, ConfEagerValueMapper.DOUBLE_MAPPER, Double.class);
    }

    public ConfEagerPropertyDoubleArray(ConfEager.DefaultValue<Double[]> defaultValue) {
        super(defaultValue, ConfEagerValueMapper.DOUBLE_MAPPER, Double.class);
    }

    public ConfEagerPropertyDoubleArray() {
        super(ConfEagerValueMapper.DOUBLE_MAPPER, Double.class);
    }

}
