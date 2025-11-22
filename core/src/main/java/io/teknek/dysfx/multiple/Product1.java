package io.teknek.dysfx.multiple;

public interface Product1<T> extends Product0 {
    @Override
    default int productArity(){
        return 1;
    }

}
