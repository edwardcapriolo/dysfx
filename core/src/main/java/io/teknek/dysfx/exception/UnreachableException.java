package io.teknek.dysfx.exception;

/**
 * Use as an alternative to assert. This code should never be thrown. Unlike an IllegalArgumentException which
 * could have other meaning.
 */
public class UnreachableException extends RuntimeException {
    public UnreachableException(String message) {
        super(message);
    }

    public UnreachableException(String message, Throwable cause) {
        super(message, cause);
    }
}
