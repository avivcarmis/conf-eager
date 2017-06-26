package io.github.avivcarmis.confEager;

import io.github.avivcarmis.confEager.exceptions.ConfEagerInstantiationException;
import io.github.avivcarmis.confEager.exceptions.ConfEagerPropertiesMissingException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents a source of configuration,
 * this may be for example system properties or environment variables which
 * are available by default, or MySQL, Consul or any other source, by simply
 * extending this class and implementing methods.
 *
 * To create a custom source, implement {@link #getValueOrNull(String)} method,
 * and call {@link #notifyUpdate()} whenever the data in the source has changed,
 * this will automatically update all the {@link ConfEager} classes that are bound
 * to this source.
 */
abstract public class ConfEagerSource {

    // Fields

    private final ConcurrentHashMap<ConfEager, List<ConfEagerProperty>> _confEagerMapping;

    // Constructors

    public ConfEagerSource() {
        _confEagerMapping = new ConcurrentHashMap<>();
    }

    // Public

    /**
     * Receive a {@link ConfEager} class, bind it to the current source,
     * and returns the bound instance.
     *
     * Whenever the source changes, all bound instances are automatically
     * updated.
     *
     * @param confEagerObjectClass class to instantiate and bind.
     * @param <T>                  type of the ConfEager class
     * @return and instantiated and bound ConfEager object.
     */
    public <T extends ConfEager> T bind(Class<T> confEagerObjectClass) {
        T t;
        try {
            t = confEagerObjectClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ConfEagerInstantiationException(e);
        }
        bind(t);
        return t;
    }

    /**
     * Receive a {@link ConfEager} instance and bind it to the current source.
     *
     * Whenever the source changes, all bound instances are automatically updated.
     *
     * @param confEagerObject instance to bind
     */
    public void bind(ConfEager confEagerObject) {
        _confEagerMapping.computeIfAbsent(confEagerObject, confEager -> {
            List<ConfEagerProperty> properties = ConfEagerReflectionUtils.findProperties(confEagerObject);
            populate(confEagerObject, properties);
            return properties;
        });
    }

    // Private

    /**
     * Must be called whenever the source data changes, in order to propagate
     * changes to all bound {@link ConfEager} instances.
     */
    protected void notifyUpdate() {
        for (Map.Entry<ConfEager, List<ConfEagerProperty>> entry : _confEagerMapping.entrySet()) {
            populate(entry.getKey(), entry.getValue());
        }
    }

    private void populate(ConfEager confEagerObject, List<ConfEagerProperty> properties) {
        List<String> missingProperties = new LinkedList<>();
        for (ConfEagerProperty property : properties) {
            String env = confEagerObject.defaultEnvironment();
            String propertyName = (env == null ? "" : env) + property.getPropertyName();
            String value = getValueOrNull(propertyName);
            if (value == null) {
                if (property.isRequired()) {
                    missingProperties.add("`" + propertyName + "`");
                }
            }
            else {
                property.update(value);
            }
        }
        if (missingProperties.size() > 0) {
            throw new ConfEagerPropertiesMissingException(confEagerObject, missingProperties);
        }
    }

    /**
     * Extracts the String value of a property. No parsing needed.
     *
     * @param propertyName the name of the property to extract.
     * @return the String value of a property
     */
    abstract public String getValueOrNull(String propertyName);

}
