package io.teknek.dysfx.multiple;

public interface Product2<T, T2> extends Product1<T> {
    default int productArity() {
        return 2;
    }
}
