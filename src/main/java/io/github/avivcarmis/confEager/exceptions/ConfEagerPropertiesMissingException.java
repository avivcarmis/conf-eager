package io.github.avivcarmis.confEager.exceptions;

import io.github.avivcarmis.confEager.ConfEager;

import java.util.List;

/**
 * Created by Mamot on 6/26/2017.
 */
public class ConfEagerPropertiesMissingException extends ConfEagerException {

    public ConfEagerPropertiesMissingException(ConfEager confEager, List<String> propertyNames) {
        super("the following required config properties are missing from " +
                confEager.getClass().getSimpleName() + ": " + String.join(", ", propertyNames));
    }

}
