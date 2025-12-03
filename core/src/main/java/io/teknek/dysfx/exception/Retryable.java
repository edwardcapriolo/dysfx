package io.teknek.dysfx.exception;

sealed interface Retryable permits Recoverable, NotRecoverable {
    //boolean isRecoverable();
}
