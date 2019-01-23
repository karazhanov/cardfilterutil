package org.karazhanov.exceptions;

/**
 * @author karazhanov on 23.01.19.
 */
public class WrongParamException extends RuntimeException {
    public WrongParamException(String message) {
        super(message);
    }

    public WrongParamException(String message, Throwable cause) {
        super(message, cause);
    }
}
