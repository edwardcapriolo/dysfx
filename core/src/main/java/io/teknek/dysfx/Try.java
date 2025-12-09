package io.teknek.dysfx;

import io.teknek.dysfx.exception.WrappedThrowable;
import io.teknek.dysfx.multiple.Product1;

import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public sealed interface Try<T> extends Product1<T>, Serializable permits Success, Failure  {

    static <U> Success<U> Success(U u){
        return new Success<>(u);
    }

    static <T extends Throwable> Failure<T> Failure(T t){
        return new Failure<>(t);
    }

    static <X> Try<X> of(Supplier<X> supplier, ThrowControl throwControl){
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

    static <X> Try<X> of(Supplier<X> supplier){
        try {
            X x = supplier.get();
            return new Success<>(x);
        } catch (RuntimeException e){
            return new Failure<>(e);
        }
    }

    /**
     * To remain idiomatic with Callable which throws Exception. This Try will catch exception
     * @param callable
     * @return a try wrapping the callable
     * @param <X> The type the try will return on success
     */
    static <X> Try<X> ofCallable(Callable<X> callable){
        try {
            X x = callable.call();
            return new Success<>(x);
        } catch (Exception e){
            return new Failure<>(e);
        }
    }

    static <X> Try<X> ofCallable(Callable<X> callable, ThrowControl throwControl){
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

    static <X> Try<X> ofCheckedSupplier(CheckedSupplier<X> checked){
        try {
            X x = checked.get();
            return new Success<>(x);
        } catch (Throwable t){
            return new Failure<>(t);
        }
    }

    static <X> Try<X> ofCheckedSupplier(CheckedSupplier<X> checked, ThrowControl throwControl){
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
    T getOrElse(T t);
    T orElse(Try<T> odElse);
    T get() throws WrappedThrowable;

    Maybe<T> toMaybe();
    /**
     * Use toMaybe if the result can be null!
     * @return Some if the result is Success or Not Null else Optional.empty()
     */
    Optional<T> toOption();
    <U> Try<U> map(Function<T, U> mapper);

    <U> Try<U> flatMap(Function<T, Try<U>> mapper);

    /** apply isSuccess if the try is success, isFailure of failed**/
    <U> Try<U> transform(Function<T,Try<U>> ifSuccess,
                         Function<Throwable, Try<U>> ifFailure);


    //def fold[U](fa: Throwable => U, fb: T => U): U
    //<U> U fold(Function<Throwable, U> ifFailure, Function<T, U> ifSuccess);
    void forEach(Consumer<T> action);
    T sneakyGet();
    Either<Throwable,T> toEither();

    /**
     *
     * @return converts to a failure if the predicate is not satisfied
     */
    Try<T> filter(Predicate<T> predicate);
}
