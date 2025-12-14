package io.teknek.dysfx;

import io.teknek.dysfx.exception.WrappedThrowable;
import io.teknek.dysfx.multiple.Product1;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A Try is a monad that allows the flow control of Java try catch to be controlled by the user. Users supply a "block"
 * of code during Try creation in the form of a Supplier, CheckedSupplier, or Callable. The try is evaluated if an exception
 * is thrown a Failure, if the code completes a Success.
 * ThrowControl is used to control which Throwable are captured inside the Try.
 * @param <T> The type a Successful try contains
 */
public sealed interface Try<T> extends Product1<T>, Serializable permits Success, Failure  {

    static <U> @Nonnull Success<U> Success(@Nullable U u){
        return new Success<>(u);
    }

    static <T extends Throwable> @Nonnull Failure<T> Failure(@Nullable T t){
        return new Failure<>(t);
    }

    static <X> @Nonnull Try<X> of(@Nonnull Supplier<X> supplier, @Nonnull ThrowControl throwControl){
        Objects.requireNonNull(supplier, "supplier can not be null");
        Objects.requireNonNull(throwControl, "throwControl can not be null");
        try {
            return of(supplier);
        } catch (Throwable tr){
            try {
                throwControl.handle(tr);
            } catch (Throwable reallyThrow){
                throwControl.sneakyThrows(tr);
            }
            return new Failure<>(tr);
        }
    }

    static <X> @Nonnull Try<X> of(@Nonnull Supplier<X> supplier){
        Objects.requireNonNull(supplier, "supplier can not be null");
        try {
            return new Success<>(supplier.get());
        } catch (RuntimeException e){
            return new Failure<>(e);
        }
    }

    /**
     * To remain idiomatic with Callable which throws Exception, this Try will catch exception
     * @param callable
     * @return a try wrapping the callable
     * @param <X> The type the try will return on success
     */
    static <X> @Nonnull Try<X> ofCallable(@Nonnull Callable<X> callable){
        Objects.requireNonNull(callable, "callable can not be null");
        try {
            X x = callable.call();
            return new Success<>(x);
        } catch (Exception e){
            return new Failure<>(e);
        }
    }

    static <X> @Nonnull Try<X> ofCallable(@Nonnull Callable<X> callable, @Nonnull ThrowControl throwControl){
        Objects.requireNonNull(callable, "callable can not be null");
        Objects.requireNonNull(throwControl, "throwControl can not be null");
        try {
            X x = callable.call();
            return new Success<>(x);
        } catch (Throwable tr){
            try {
                throwControl.handle(tr);
            } catch (Throwable reallyThrow){
                throwControl.sneakyThrows(tr);
            }
            return new Failure<>(tr);
        }
    }

    static <X> @Nonnull Try<X> ofCheckedSupplier(@Nonnull CheckedSupplier<X> checked){
        Objects.requireNonNull(checked, "checked supplier can not be null");
        try {
            X x = checked.get();
            return new Success<>(x);
        } catch (Throwable t){
            return new Failure<>(t);
        }
    }

    static <X> @Nonnull Try<X> ofCheckedSupplier(@Nonnull CheckedSupplier<X> checked,
                                                 @Nonnull ThrowControl throwControl){
        Objects.requireNonNull(checked, "checked supplier can not be null");
        Objects.requireNonNull(throwControl, "throwControl can not be null");
        try {
            X x = checked.get();
            return new Success<>(x);
        } catch (Throwable tr){
            try {
                throwControl.handle(tr);
            } catch (Throwable reallyThrow){
                throwControl.sneakyThrows(tr);
            }
            return new Failure<>(tr);
        }
    }


    boolean isSuccess();
    @Nullable T getOrElse(@Nullable T t);
    @Nullable T orElse(@Nonnull Try<T> odElse);
    @Nullable T get() throws WrappedThrowable;

    /**
     *
     * @return If this try is a Failure a sneaky exception will be thrown to the caller else if Success the result of
     * the successful computation.
     */
    @Nullable T sneakyGet();

    @Nonnull Maybe<T> toMaybe();
    /**
     * Use toMaybe if the result can be null!
     * @return Some if the result is Success or Not Null else Optional.empty()
     */
    @Nonnull Optional<T> toOption();
    <U> @Nonnull Try<U> map(@Nonnull Function<T, U> mapper);

    <U> @Nonnull Try<U> flatMap(@Nonnull Function<T, Try<U>> mapper);

    /** apply isSuccess if the try is success, isFailure of failed**/
    <U> @Nonnull Try<U> transform(@Nonnull Function<T,Try<U>> ifSuccess,
                                  @Nonnull Function<Throwable, Try<U>> ifFailure);


    //def fold[U](fa: Throwable => U, fb: T => U): U
    //<U> U fold(Function<Throwable, U> ifFailure, Function<T, U> ifSuccess);
    void forEach(@Nonnull Consumer<T> action);

    @Nonnull Either<Throwable,T> toEither();

    /**
     *
     * @return converts to a failure if the predicate is not satisfied
     */
    @Nonnull Try<T> filter(@Nonnull Predicate<T> predicate);
}
