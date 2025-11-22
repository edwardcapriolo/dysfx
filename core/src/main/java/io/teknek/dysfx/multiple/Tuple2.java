package io.teknek.dysfx.multiple;

public class Tuple2<T1,T2> extends Tuple1<T1> implements Product2<T1,T2>{

    public static <X1,X2> Tuple2<X1, X2> of(X1 left, X2 right){
        return new Tuple2<>(left, right);
    }

    Tuple2(){}
    public Tuple2(T1 t1, T2 t2){
        data = new Object[2];
        data[0] = t1;
        super.data[1] = t2;
    }

    public T2 getT2(){
        return (T2) data[1];
    }

    public T1 left(){
        return getT1();
    }

    public T2 right(){
        return getT2();
    }
}
