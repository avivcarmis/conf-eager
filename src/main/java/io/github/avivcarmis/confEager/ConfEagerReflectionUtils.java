package io.github.avivcarmis.confEager;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Reflection utilities to scan and find the property fields
 */
class ConfEagerReflectionUtils {

    // Constants

    private static final Logger LOGGER = Logger.getLogger(ConfEager.class.getName());

    // Static

    static List<ConfEagerProperty> findProperties(ConfEager confEager) {
        List<ConfEagerProperty> result = new LinkedList<>();
        Class<?> currentClass = confEager.getClass();
        while (currentClass != null && !currentClass.equals(Object.class)) {
            Field[] currentClassFields = currentClass.getDeclaredFields();
            for (Field field : currentClassFields) {
                ConfEagerProperty property = validatePropertyField(confEager, field);
                if (property != null) {
                    result.add(property);
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        return result;
    }

    private static ConfEagerProperty validatePropertyField(ConfEager confEager, Field field) {
        if (!confEager.defaultFieldFilter(field) || !ConfEagerProperty.class.isAssignableFrom(field.getType())) {
            return null;
        }
        field.setAccessible(true);
        Object value;
        try {
            value = field.get(confEager);
        } catch (IllegalAccessException e) {
            LOGGER.severe("could not access property " + field.getName() + " on confEager class " +
                    confEager.getClass().getSimpleName() + ": " + e.getMessage());
            return null;
        }
        if (value == null) {
            LOGGER.severe("property " + field.getName() + " on confEager class " + confEager.getClass().getSimpleName() +
                    " must not be null to be populated");
            return null;
        }
        ConfEagerProperty result = (ConfEagerProperty) value;
        result.setFieldName(field.getName());
        return result;
    }

}