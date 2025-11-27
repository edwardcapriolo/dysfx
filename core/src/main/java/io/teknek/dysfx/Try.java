package io.teknek.dysfx;

import io.teknek.dysfx.exception.WrappedThrowable;
import io.teknek.dysfx.multiple.Product1;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Try<T> extends Product1<T> {
    
    static <X> Try<X> of(Supplier<X> supplier, ThrowControl t){
        try {
            return of(supplier);
        } catch (Throwable tr){
            try {
                t.handle(tr);
            } catch (Throwable reallyThrow){
                t.sneakyThrows(tr);
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
    /**
     * @throws Throwable if failure a Sneaky exception will be thrown
     * @return the value if results otherwise throw the exception in the failure
     */

    T sneakyGet();
}
