package io.github.avivcarmis.confEager.exceptions;

public class ConfEagerReadBeforeWriteException extends ConfEagerException {

    public ConfEagerReadBeforeWriteException() {
        super("confEager property not populated before read");
    }

}
