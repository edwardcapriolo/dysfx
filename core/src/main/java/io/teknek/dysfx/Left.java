package io.teknek.dysfx;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

public non-sealed class Left<L,R> implements Either<L,R> {
    private L left;
    public Left(L left){
        this.left = left;
    }

    @Override
    public boolean isLeft() {
        return true;
    }

    @Override
    public boolean isRight() {
        return false;
    }

    @Override
    public boolean contains(R r) {
        return false;
    }

    @Override
    public void forEach(@Nonnull Consumer<R> action) {
        //do nothing
    }

    @Override
    public boolean exists(@Nonnull Predicate<R> predicate) {
        return false;
    }

    @Override
    public Object productElement(int n) {
        if (n == 0){
            return left;
        }
        throw new ArrayIndexOutOfBoundsException("Left has arity of 0 not " + n);
    }

    @Override
    public Iterator<Object> productIterator() {
        return Either.super.productIterator();
    }
}
