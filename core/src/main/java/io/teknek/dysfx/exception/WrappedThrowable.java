package io.teknek.dysfx.exception;

public class WrappedThrowable extends RuntimeException{

    public WrappedThrowable(Throwable t){
        super(t);
    }
}
