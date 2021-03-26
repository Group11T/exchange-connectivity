package com.io.t11.exchangeconnectivity.error;

public class MalFormedOrderException extends Exception {

    private static final long serialVersionUID = 5861310537366287163L;

    public MalFormedOrderException() {
        super();
    }

    public MalFormedOrderException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MalFormedOrderException(final String message) {
        super(message);
    }

    public MalFormedOrderException(final Throwable cause) {
        super(cause);
    }
}
