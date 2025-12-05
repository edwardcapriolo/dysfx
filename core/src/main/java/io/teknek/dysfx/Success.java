package io.teknek.dysfx;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public non-sealed class Success<T> implements Try<T> {

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

    @Override
    public <U> Try<U> transform(Function<T, Try<U>> ifSuccess, Function<Throwable, Try<U>> ifFailure) {
        try {
            return ifSuccess.apply(result);
        } catch (RuntimeException e){
            return new Failure<>(e);
        }
    }

    @Override
    public void forEach(Consumer<T> action) {
        action.accept(result);
    }

    public T sneakyGet(){
        return this.result;
    }

    @Override
    public Either<Throwable, T> toEither() {
        return new Right<>(this.result);
    }

    @Override
    public Object productElement(int n) {
        if (n != 0) {
            throw new IllegalArgumentException("Success has an arity of 1 not " + n);
        }
        return this.result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Success<?> success = (Success<?>) o;
        return Objects.equals(result, success.result);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(result);
    }
}
