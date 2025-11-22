package io.teknek.dysfx;

import java.util.function.Function;

public class Success<T> extends Try<T> {

    private final T result;

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
        return this.result;
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
            throw new IllegalArgumentException("");
        }
        return this.result;
    }


}
