package io.teknek.dysfx;

import io.teknek.dysfx.multiple.Product0;

import java.util.Collections;
import java.util.Iterator;

/**
 * The equivalent of None, a data structure could never hold Nothing, but a method could return it to signify the result was
 * not found
 *
 * @param <T>
 */
public class Nothing<T> implements Maybe<T>, Product0 {
    public static final Nothing INSTANCE = new Nothing();

    @Override
    public String toString() {
        return "Nothing{}";
    }

    @Override
    public Object productElement(int n) {
        throw new ArrayIndexOutOfBoundsException("Nothing is of size 0 not " + n);
    }

    @Override
    public Iterator<Object> productIterator() {
        return Collections.emptyIterator();
    }
}
