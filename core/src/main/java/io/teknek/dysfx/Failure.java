package io.teknek.dysfx;

import io.teknek.dysfx.exception.WrappedThrowable;

import java.util.Optional;
import java.util.function.Function;

public class Failure<T> implements Try<T> {
    protected final Throwable thrown;

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
    public Maybe<T> toMaybe() {
        return Maybe.nothing();
    }

    @Override
    public Optional<T> toOption() {
        return Optional.empty();
    }

    @Override
    public T sneakyGet() {
        ThrowControl.sneakyThrow(thrown);
        return null;
    }

    @Override
    public Object productElement(int n) {
        if (n != 0) {
            throw new IllegalArgumentException("Failure has arity of 1 not " + n);
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
