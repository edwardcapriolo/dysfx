package io.teknek.dysfx.multiple;

public class Tuple3<T1, T2, T3> extends Tuple2<T1,T2> implements Product3<T1, T2, T3>{
    public static <R1, R2, R3> Tuple3<R1, R2, R3> of (R1 t1, R2 t2, R3 t3){
        return new Tuple3<>(t1, t2, t3);
    }

    public Tuple3(T1 t1, T2 t2, T3 t3){
        super();
        data = new Object[] { t1, t2, t3 };
    }

    public T3 getT3(){
        return (T3) data[2];
    }

}
