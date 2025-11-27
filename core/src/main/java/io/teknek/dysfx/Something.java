package io.teknek.dysfx;

import io.teknek.dysfx.multiple.Product1;

import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;

public class Something<T> implements Maybe<T>, Product1<T> {
    protected final T something;

    public Something(T t){
        this.something = t;
    }

    public T get() {
        return something;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Something<?> something1 = (Something<?>) o;
        return Objects.equals(something, something1.something);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(something);
    }

    @Override
    public String toString() {
        return "Something{" +
                "something=" + something +
                '}';
    }

    @Override
    public Object productElement(int n) {
        if (n == 1){
            return something;
        }
        else throw new ArrayIndexOutOfBoundsException("Something is of size 1 not " + n);
    }

    @Override
    public Iterator<Object> productIterator() {
        return Collections.<Object>singletonList(something).iterator();
    }
}