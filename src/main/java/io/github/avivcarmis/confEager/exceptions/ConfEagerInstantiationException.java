package io.github.avivcarmis.confEager.exceptions;

/**
 * Created by Mamot on 6/26/2017.
 */
public class ConfEagerInstantiationException extends ConfEagerException {

    public ConfEagerInstantiationException(Throwable cause) {
        super("could not instantiate confEager object class", cause);
    }

}
