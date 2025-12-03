package io.teknek.dysfx;

import java.io.Serializable;
import java.util.function.Consumer;

import io.teknek.dysfx.multiple.Product1;

public sealed interface Either<L, R> extends Product1, Serializable permits Left, Right {

    static <U,V> Left<U,V> Left(U l){
        return left(l);
    }

    static <U,V> Right<U,V> Right(V r){
        return right(r);
    }

    static <U,V> Left<U,V> left(U l){
        return new Left<>(l);
    }
    static <U,V> Right<U,V> right(V r){
        return new Right<>(r);
    }
    boolean isLeft();
    boolean isRight();
    boolean contains(R r);

    /**
     *
     * @param action execute side effect only if a Right
     */
    void forEach(Consumer<R> action);
}

