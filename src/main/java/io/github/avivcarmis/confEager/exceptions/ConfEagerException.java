package io.github.avivcarmis.confEager.exceptions;

/**
 * Created by Mamot on 6/26/2017.
 */
public class ConfEagerException extends RuntimeException {

    public ConfEagerException() {}

    public ConfEagerException(String message) {
        super(message);
    }

    public ConfEagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfEagerException(Throwable cause) {
        super(cause);
    }

}
