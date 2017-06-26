package io.github.avivcarmis.confEager;

import io.github.avivcarmis.confEager.exceptions.ConfEagerIllegalPropertyValue;
import io.github.avivcarmis.confEager.exceptions.ConfEagerReadBeforeWriteException;

/**
 * Created by avivc on 1/22/2017.
 */
abstract public class ConfEagerProperty<T> {

    private final boolean _required;

    private String _propertyName;

    private boolean _populated;

    private T _value;

    public ConfEagerProperty(ConfEager.DefaultValue<T> defaultValue, ConfEager.PropertyName propertyName) {
        if (defaultValue != null) {
            _required = false;
            _populated = true;
            _value = defaultValue.getValue();
        }
        else {
            _required = true;
            _populated = false;
            _value = null;
        }
        if (propertyName != null) {
            _propertyName = propertyName.getName();
        }
    }

    public ConfEagerProperty(ConfEager.PropertyName propertyName, ConfEager.DefaultValue<T> defaultValue) {
        this(defaultValue, propertyName);
    }

    public ConfEagerProperty(ConfEager.PropertyName propertyName) {
        this(null, propertyName);
    }

    public ConfEagerProperty(ConfEager.DefaultValue<T> defaultValue) {
        this(defaultValue, null);
    }

    public ConfEagerProperty() {
        this((ConfEager.DefaultValue<T>) null, null);
    }

    public T get() {
        if (!_populated) {
            throw new ConfEagerReadBeforeWriteException();
        }
        return _value;
    }

    void update(String value) {
        try {
            this._value = map(value);
        } catch (Throwable t) {
            throw new ConfEagerIllegalPropertyValue(t);
        }
        _populated = true;
    }

    void setFieldName(String fieldName) {
        if (_propertyName == null) {
            _propertyName = fieldName;
        }
    }

    String getPropertyName() {
        return _propertyName;
    }

    boolean isRequired() {
        return _required;
    }

    abstract protected T map(String value);

}
