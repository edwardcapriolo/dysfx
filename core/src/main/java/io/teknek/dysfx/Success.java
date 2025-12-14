package io.teknek.dysfx;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public non-sealed class Success<T> implements Try<T> {

    @Nullable
    protected final T result;

    public Success(@Nullable T t) {
        result = t;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public @Nullable T getOrElse(@Nullable T t) {
        return result;
    }

    @Override
    public @Nullable T orElse(@Nonnull Try<T> orElse) {
        return result;
    }

    @Override
    public @Nullable T get() {
        return result;
    }

    @Override
    public @Nonnull Maybe<T> toMaybe() {
        return Maybe.possibly(result);
    }

    @Override
    public @Nonnull Optional<T> toOption() {
        return Optional.ofNullable(result);
    }

    @Override
    public <U> @Nonnull Try<U> map(@Nonnull Function<T, U> mapper) {
        Objects.requireNonNull(mapper, "Mapper can not be null");
        try {
            return new Success<>(mapper.apply(result));
        } catch (RuntimeException e) {
            return new Failure<>(e);
        }
    }

    public <U> @Nonnull Try<U> flatMap(@Nonnull Function<T, Try<U>> mapper) {
        Objects.requireNonNull(mapper, "Mapper can not be null");
        try {
            return mapper.apply(result);
        } catch (RuntimeException e) {
            return new Failure<>(e);
        }
    }

    @Override
    public <U> @Nonnull Try<U> transform(@Nonnull Function<T, Try<U>> ifSuccess,
                                         @Nonnull Function<Throwable, Try<U>> ifFailure) {
        try {
            return ifSuccess.apply(result);
        } catch (RuntimeException e){
            return new Failure<>(e);
        }
    }

    @Override
    public void forEach(@Nonnull Consumer<T> action) {
        Objects.requireNonNull(action, "Mapper can not be null");
        action.accept(result);
    }

    public @Nullable T sneakyGet(){
        return result;
    }

    @Override
    public @Nonnull Either<Throwable, T> toEither() {
        return new Right<>(result);
    }

    @Nonnull
    @Override
    public Try<T> filter(@Nonnull  Predicate<T> predicate) {
        Objects.requireNonNull(predicate, "Predicate can not be null");
        return predicate.test(result) ? this
                : new Failure<>(new NoSuchElementException("Predicate does not hold for " + result));
    }

    @Override
    public @Nullable Object productElement(int n) {
        if (n != 0) {
            throw new IllegalArgumentException("Success has an arity of 1 not " + n);
        }
        return result;
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

    @Override
    public String toString() {
        return "Success{" +
                "result=" + result +
                '}';
    }
}
