package io.github.avivcarmis.confEager.exceptions;

public class ConfEagerInstantiationException extends ConfEagerException {

    public ConfEagerInstantiationException(Throwable cause) {
        super("could not instantiate confEager object class", cause);
    }

}
