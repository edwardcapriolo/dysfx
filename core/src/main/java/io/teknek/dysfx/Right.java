package io.teknek.dysfx;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public non-sealed class Right<L,R> implements Either<L, R>{
    private final R right;
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
    public void forEach(@Nonnull Consumer<R> action) {
        action.accept(right);
    }

    @Override
    public boolean exists(@Nonnull Predicate<R> predicate) {
        Objects.requireNonNull(predicate, "predicate must be non null");
        return predicate.test(right);
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


