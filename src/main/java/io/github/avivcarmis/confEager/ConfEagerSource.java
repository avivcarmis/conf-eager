package io.github.avivcarmis.confEager;

import io.github.avivcarmis.confEager.exceptions.ConfEagerInstantiationException;
import io.github.avivcarmis.confEager.exceptions.ConfEagerPropertiesMissingException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Mamot on 6/25/2017.
 */
abstract public class ConfEagerSource {

    private final List<ConfEagerBinding> _confEagerBindings;

    public ConfEagerSource() {
        _confEagerBindings = new LinkedList<>();
    }

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

    public void bind(ConfEager confEagerObjectClass) {
        ConfEagerBinding binding = new ConfEagerBinding(confEagerObjectClass);
        _confEagerBindings.add(binding);
        populate(binding);
    }

    protected void notifyUpdate() {
        for (ConfEagerBinding confEagerBinding : _confEagerBindings) {
            populate(confEagerBinding);
        }
    }

    private void populate(ConfEagerBinding binding) {
        List<String> missingProperties = new LinkedList<>();
        for (ConfEagerProperty property : binding._properties) {
            String env = binding._confEagerObject.defaultEnvironment();
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
            throw new ConfEagerPropertiesMissingException(binding._confEagerObject, missingProperties);
        }
    }

    abstract public String getValueOrNull(String propertyName);

}
