package io.teknek.dysfx.multiple;

import java.util.Collections;
import java.util.Iterator;

public class Tuple0 implements Product0{
    @Override
    public Object productElement(int n) {
        throw new IndexOutOfBoundsException(0);
    }

    @Override
    public Iterator<Object> productIterator() {
        return Collections.emptyIterator();
    }
}
