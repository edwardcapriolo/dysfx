package io.teknek.dysfx.multiple;

import java.util.Arrays;
import java.util.Objects;

public class Tuple1<T1> implements Product1<T1> {

    public static <X> Tuple1<X> of(X t){
        return new Tuple1<>(t);
    }
    protected Object[] data;

    Tuple1(){}
    public Tuple1(T1 t1){
        data = new Object[1];
        data[0] = t1;
    }

    @Override
    public Object productElement(int n) {
        return data[n];
    }

    @SuppressWarnings("unchecked")
    public T1 getT1(){
        return (T1) data[0];
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tuple1<?> tuple1 = (Tuple1<?>) o;
        return Objects.deepEquals(data, tuple1.data);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(this.data);
    }
}
