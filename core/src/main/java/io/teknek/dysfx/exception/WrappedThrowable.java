package io.teknek.dysfx.exception;

import javax.annotation.Nonnull;

public class WrappedThrowable extends RuntimeException {

    public WrappedThrowable(@Nonnull Throwable t){
        super(t);
    }
}
