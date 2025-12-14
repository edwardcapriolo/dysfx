package io.teknek.dysfx;

import io.teknek.dysfx.multiple.Product1;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Iterator;

/* this is a wrapper for the null for a data structure that can not hold a null. */
public class Null<T> implements Maybe<T>, Product1<T> {
    public static final Null INSTANCE = new Null();

    @Override
    public int hashCode() {
        return 15;
    }

    /** usually null != null but in the scope of placing a null value in a collection in needs a true equality.*/
    @Override
    public boolean equals(Object other) {
        if (other == INSTANCE){
            return true;
        } return false;
    }

    @Override
    public String toString() {
        return "Null{}";
    }

    @Override
    public Object productElement(int n) {
        if (n == 1){
            return INSTANCE;
        }
        else throw new ArrayIndexOutOfBoundsException("Null is of size 1 not " + n);
    }

    @Override
    public @Nonnull Iterator<Object> productIterator() {
        return Collections.<Object>singletonList(INSTANCE).iterator();
    }
}
