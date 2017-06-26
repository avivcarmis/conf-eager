package io.github.avivcarmis.confEager.exceptions;

/**
 * Created by Mamot on 6/26/2017.
 */
public class ConfEagerReadBeforeWriteException extends ConfEagerException {

    public ConfEagerReadBeforeWriteException() {
        super("confEager property not populated before read");
    }

}
