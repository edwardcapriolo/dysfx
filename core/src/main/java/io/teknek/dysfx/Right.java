package io.teknek.dysfx;

import java.util.Iterator;

public non-sealed class Right<L,R> implements Either<L, R>{
    private R right;
    public Right(R right){
        this.right = right;
    }

    @Override
    public boolean isLeft() {
        return false;
    }

    @Override
    public boolean isRight() {
        return true;
    }

    @Override
    public boolean contains(R r) {
        if (right == null) { return false;}
        return right.equals(r);
    }

    @Override
    public Object productElement(int n) {
        if (n == 0){
            return right;
        }
        throw new ArrayIndexOutOfBoundsException("Right has arity of 0 not " +  n);
    }

    @Override
    public Iterator<Object> productIterator() {
        return Either.super.productIterator();
    }
}


