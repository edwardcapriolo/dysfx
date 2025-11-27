package io.teknek.dysfx;

import java.util.Optional;
import java.util.function.Function;

public class Success<T> implements Try<T> {

    protected final T result;

    public Success(T t) {
        result = t;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public T getOrElse(T t) {
        return this.result;
    }

    @Override
    public T orElse(Try<T> odElse) {
        return result;
    }

    @Override
    public T get() {
        return result;
    }

    @Override
    public Maybe<T> toMaybe() {
        return Maybe.possibly(result);
    }

    @Override
    public Optional<T> toOption() {
        return Optional.ofNullable(result);
    }

    @Override
    public <U> Try<U> map(Function<T, U> mapper) {
        try {
            return new Success<>(mapper.apply(result));
        } catch (RuntimeException e) {
            return new Failure<>(e);
        }
    }

    public <U> Try<U> flatMap(Function<T, Try<U>> mapper) {
        try {
            return mapper.apply(result);
        } catch (RuntimeException e) {
            return new Failure<>(e);
        }
    }

    public T sneakyGet(){
        return this.result;
    }

    @Override
    public Object productElement(int n) {
        if (n != 0) {
            throw new IllegalArgumentException("Success has an arity of 1 not " + n);
        }
        return this.result;
    }

}
