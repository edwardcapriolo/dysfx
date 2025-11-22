package io.teknek.dysfx;

import io.teknek.dysfx.exception.WrappedThrowable;

import java.util.function.Function;

public class Failure<T> extends Try<T> {
    private final Throwable thrown;

    public Failure(Throwable t) {
        this.thrown = t;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public T getOrElse(T t) {
        return t;
    }

    @Override
    public T orElse(Try<T> odElse) {
        return odElse.get();
    }

    @Override
    public T get() {
        throw new WrappedThrowable(thrown);
    }

    @Override
    public T sneakyGet() {
        ThrowControl.sneakyThrow(thrown);
        return null;
    }

    @Override
    public Object productElement(int n) {
        if (n != 0) {
            throw new IllegalArgumentException("");
        }
        return this.thrown;
    }

    @Override
    public <U> Try<U> map(Function<T, U> mapper) {
        return new Failure<>(thrown);
    }

    @Override
    public <U> Try<U> flatMap(Function<T, Try<U>> mapper) {
        return new Failure<>(thrown);
    }
}
