package io.teknek.dysfx;

import io.teknek.dysfx.exception.UnreachableException;
import io.teknek.dysfx.exception.WrappedThrowable;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public non-sealed class Failure<T> implements Try<T> {
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
    public T orElse(@Nonnull Try<T> odElse) {
        return odElse.get();
    }

    @Override
    public T get() {
        throw new WrappedThrowable(thrown);
    }

    @Nonnull
    @Override
    public Maybe<T> toMaybe() {
        return Maybe.nothing();
    }

    @Nonnull
    @Override
    public Optional<T> toOption() {
        return Optional.empty();
    }

    @Override
    public T sneakyGet() {
        ThrowControl.sneakyThrow(thrown);
        throw new UnreachableException("Sneaky get will always throw. This is unreachable.");
    }

    @Nonnull
    @Override
    public Either<Throwable, T> toEither(){
        return new Left<>(thrown);
    }

    @Nonnull
    @Override
    public Try<T> filter(@Nonnull Predicate<T> predicate) {
        return this;
    }

    @Override
    public Object productElement(int n) {
        if (n != 0) {
            throw new IllegalArgumentException("Failure has arity of 1 not " + n);
        }
        return this.thrown;
    }

    @Nonnull
    @Override
    public <U> Try<U> map(@Nonnull Function<T, U> mapper) {
        return new Failure<>(thrown);
    }

    @Nonnull
    @Override
    public <U> Try<U> flatMap(@Nonnull Function<T, Try<U>> mapper) {
        return new Failure<>(thrown);
    }

    @Nonnull
    @Override
    public <U> Try<U> transform(@Nonnull Function<T, Try<U>> ifSuccess, @Nonnull Function<Throwable, Try<U>> ifFailure) {
        try {
            return ifFailure.apply(thrown);
        } catch (RuntimeException e){
            return new Failure<>(e);
        }
    }

    @Override
    public void forEach(@Nonnull Consumer<T> action) {
        //failures do nothing with action
    }

    @Override
    public String toString() {
        return "Failure{" +
                "thrown=" + thrown +
                '}';
    }
}
