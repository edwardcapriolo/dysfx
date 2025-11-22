package io.teknek.dysfx.multiple;

import java.util.Iterator;

public interface Product {
    int productArity();
    Object productElement(int n);

    default Iterator<Object> productIterator(){
        final int[] current = {0};
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return current[0] < productArity();
            }

            @Override
            public Object next() {
                return productElement(current[0]++);
            }
        };
    }

}

