package io.teknek.dysfx;

import io.teknek.dysfx.multiple.Product;
import io.teknek.dysfx.multiple.Product0;

import java.util.Objects;

/**
 * For collections the null problem is pervasive. Some sets take a null, others do not. Other methods might return a null
 * is that a not found or a real value. here we burrow and old concept 1b mistake null does not exist, we are left
 * with a Union of (Something | None ) but NULL needs to be a real value, ina sorted set it has so sort, in a hashmap
 * it needs to be a key. Maybe is three valued.
 * @param <T>
 */
public interface Maybe<T> extends Product {

    static <T> Something<T> definitely(T t){
        return new Something<>(Objects.requireNonNull(t, "this must be supplied"));
    }
    static <T> Null<T> nullValue(){ return Null.INSTANCE; }
    static <T> Nothing<T> nothing() { return Nothing.INSTANCE; }

    /**
     *
     * @param t a potentially null value
     * @return Maybe.Null if null else Maybe.Something(t) containing input
     */
    static <T> Maybe<T> possibly(T t){
        if (t == null){
            return Null.INSTANCE;
        } else {
            return new Something<>(t);
        }
    }

    /**
     *
     * @return if the input is null return a null else throw
     */
    static <T> Null<T> assuredlyNull(T t){
        if (t == null){
            return Null.INSTANCE;
        }
        else throw new IllegalArgumentException("This must be a null reference");
    }

}

