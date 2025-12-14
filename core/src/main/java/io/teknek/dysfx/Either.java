package io.teknek.dysfx;

import java.io.Serializable;
import java.util.function.Consumer;

import io.teknek.dysfx.multiple.Product1;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public sealed interface Either<L, R> extends Product1, Serializable permits Left, Right {

    static <U,V> Left<U,V> Left(U l){
        return left(l);
    }

    static <U,V> Right<U,V> Right(@Nullable V r){
        return right(r);
    }

    static <U,V> Left<U,V> left(@Nullable U l){
        return new Left<>(l);
    }
    static <U,V> Right<U,V> right(@Nullable V r){
        return new Right<>(r);
    }
    boolean isLeft();
    boolean isRight();
    boolean contains(@Nullable R r);

    /**
     *
     * @param action execute side effect only if a Right
     */
    void forEach(@Nonnull Consumer<R> action);
}

