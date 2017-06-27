package io.github.avivcarmis.confEager;

import io.github.avivcarmis.confEager.properties.ConfEagerPropertyBoolean;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Represents a configuration class.
 * Each inheritor will declare it's configuration properties as the class fields,
 * each property must inherit {@link ConfEagerProperty}.
 *
 * Each declared property is looked for in the source by it's field name. To override it,
 * use {@link #propertyName(String)} method to explicitly declare the property name to look for.
 *
 * Each declared property is required by default, to change it, use {@link #defaultValue(Object)}
 * method to explicitly declare what value should be used in case no value appears in the source.
 *
 * By default, static fields are ignored. Which fields get ignored are controlled by the
 * {@link #defaultFieldFilter(Field)} method which may be overridden.
 *
 * Additionally, a prefix may be set to all of the property names by overriding
 * {@link #defaultEnvironment()} method. This may be used to discriminate between
 * different development environments.
 */
abstract public class ConfEager {

    // Private

    protected String defaultEnvironment() {
        return "";
    }

    protected boolean defaultFieldFilter(Field field) {
        return !Modifier.isStatic(field.getModifiers());
    }

    // Static

    protected static <T> DefaultValue<T> defaultValue(T value) {
        return new DefaultValue<>(value);
    }

    protected static PropertyName propertyName(String name) {
        return new PropertyName(name);
    }

    public static class DefaultValue<T> {

        private final T _value;

        private DefaultValue(T value) {
            _value = value;
        }

        T getValue() {
            return _value;
        }

    }

    public static class PropertyName {

        private final String _name;

        private PropertyName(String name) {
            _name = name;
        }

        String getName() {
            return _name;
        }

    }

}
