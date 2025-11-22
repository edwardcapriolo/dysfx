package io.teknek.dysfx.multiple;

public interface Product3<T1, T2, T3> extends Product2<T1, T2>  {

    @Override
    default int productArity() {
        return 3;
    }
}
