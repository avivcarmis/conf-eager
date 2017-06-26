package io.github.avivcarmis.confEager.properties;

import io.github.avivcarmis.confEager.ConfEager;
import io.github.avivcarmis.confEager.properties.utils.ConfEagerPropertyPrimitiveArray;
import io.github.avivcarmis.confEager.properties.utils.ConfEagerValueMapper;

/**
 * Out of the box {@link io.github.avivcarmis.confEager.ConfEagerProperty}
 * that maps {@link Boolean} array values.
 */
public class ConfEagerPropertyBooleanArray extends ConfEagerPropertyPrimitiveArray<Boolean> {

    public ConfEagerPropertyBooleanArray(ConfEager.DefaultValue<Boolean[]> defaultValue, ConfEager.PropertyName propertyName) {
        super(defaultValue, propertyName, ConfEagerValueMapper.BOOLEAN_MAPPER, Boolean.class);
    }

    public ConfEagerPropertyBooleanArray(ConfEager.PropertyName propertyName, ConfEager.DefaultValue<Boolean[]> defaultValue) {
        super(propertyName, defaultValue, ConfEagerValueMapper.BOOLEAN_MAPPER, Boolean.class);
    }

    public ConfEagerPropertyBooleanArray(ConfEager.PropertyName propertyName) {
        super(propertyName, ConfEagerValueMapper.BOOLEAN_MAPPER, Boolean.class);
    }

    public ConfEagerPropertyBooleanArray(ConfEager.DefaultValue<Boolean[]> defaultValue) {
        super(defaultValue, ConfEagerValueMapper.BOOLEAN_MAPPER, Boolean.class);
    }

    public ConfEagerPropertyBooleanArray() {
        super(ConfEagerValueMapper.BOOLEAN_MAPPER, Boolean.class);
    }

}
