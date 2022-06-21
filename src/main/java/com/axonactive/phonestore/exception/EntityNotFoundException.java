package com.axonactive.phonestore.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends Exception {

    private final String errorKey;

    public EntityNotFoundException(String errorKey) {
        super();
        this.errorKey = errorKey;
    }

    public EntityNotFoundException(String message, Throwable cause, String errorKey) {
        super(message, cause);
        this.errorKey = errorKey;
    }

    public EntityNotFoundException(String message, String errorKey) {
        super(message);
        this.errorKey = errorKey;
    }

    public EntityNotFoundException(Throwable cause, String errorKey) {
        super(cause);
        this.errorKey = errorKey;
    }
}
