package io.github.avivcarmis.confEager;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * May be used to filter fields from a {@link ConfEager} class.
 */
public interface ConfEagerFieldFilter {

    /**
     * @param field field to test
     * @return false to ignore this field, true otherwise.
     */
    boolean test(Field field);

    /**
     * An out-of-the-box mapper that ignores static fields,
     * this is the default filter
     */
    ConfEagerFieldFilter NON_STATIC = field -> !Modifier.isStatic(field.getModifiers());

}
