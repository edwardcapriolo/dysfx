package io.teknek.dysfx;

import io.teknek.dysfx.exception.WrappedThrowable;
import io.teknek.dysfx.multiple.Product1;

import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Try<T> implements Product1<T> {

    public static <X> Try<X> of(Supplier<X> supplier, ThrowControl t){
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

    public static <X> Try<X> of(Supplier<X> supplier){
        try {
            X x = supplier.get();
            return new Success<>(x);
        } catch (RuntimeException e){
            return new Failure<>(e);
        }
    }

    public static <X> Try<X> ofCallable(Callable<X> callable){
        try {
            X x = callable.call();
            return new Success<>(x);
        } catch (Exception e){
            return new Failure<>(e);
        }
    }

    public abstract boolean isSuccess();
    public abstract T getOrElse(T t);
    public abstract T orElse(Try<T> odElse);
    public abstract T get() throws WrappedThrowable;

    public abstract <U> Try<U> map(Function<T, U> mapper);

    public abstract <U> Try<U> flatMap(Function<T, Try<U>> mapper);
    /**
     * @throws Throwable if failure a Sneaky exception will be thrown
     * @return the value if results otherwise throw the exception in the failure
     */

    public abstract T sneakyGet();
}
