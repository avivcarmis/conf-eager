package io.github.avivcarmis.confEager;

import io.github.avivcarmis.confEager.exceptions.ConfEagerIllegalPropertyValue;
import io.github.avivcarmis.confEager.exceptions.ConfEagerReadBeforeWriteException;

/**
 * Represents a configuration property.
 * Concrete classes that inherit this class should be used as fields of a
 * {@link ConfEager} implementation.
 *
 * Custom property types may easily be created by inheriting this class and
 * implementing {@link #map(String)}.
 */
abstract public class ConfEagerProperty<T> {

    // Fields

    private final boolean _required;

    private String _propertyName;

    private boolean _populated;

    private T _value;

    // Constructors

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

    // Public

    /**
     * @return the configuration property value currently in memory
     * @throws ConfEagerReadBeforeWriteException in case the property hasn't received any value yet
     */
    public T get() {
        if (!_populated) {
            throw new ConfEagerReadBeforeWriteException();
        }
        return _value;
    }

    // Private

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

    /**
     * @param value string representation of the value
     * @return the parsed value to be stored.
     */
    abstract protected T map(String value);

}
