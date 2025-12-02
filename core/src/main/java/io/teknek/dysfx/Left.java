package io.teknek.dysfx;

import java.util.Iterator;

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
