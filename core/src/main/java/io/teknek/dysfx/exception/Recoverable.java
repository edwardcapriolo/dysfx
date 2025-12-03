package io.teknek.dysfx.exception;

public non-sealed interface Recoverable extends Retryable {
    @Override
    default boolean isRecoverable(){
        return true;
    }
}

