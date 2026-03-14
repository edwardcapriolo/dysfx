package io.teknek.dysfx.multiple;

import java.util.Iterator;

public interface Product<T> {
    int productArity();
    T productElement(int n);

    default Iterator<T> productIterator(){
        final int[] current = {0};
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return current[0] < productArity();
            }

            @Override
            public T next() {
                return productElement(current[0]++);
            }
        };
    }

}

