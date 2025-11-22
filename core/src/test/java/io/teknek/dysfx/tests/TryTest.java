package io.teknek.dysfx.tests;

import io.teknek.dysfx.Failure;
import io.teknek.dysfx.Success;
import io.teknek.dysfx.ThrowControl;
import io.teknek.dysfx.Try;
import org.junit.jupiter.api.Test;

import java.io.IOError;

import static org.junit.jupiter.api.Assertions.*;

public class TryTest {

    @Test
    void basic(){
        Try<Integer> integerTry = Try.of(() -> 4);
        assertInstanceOf(Success.class, integerTry);
        assertEquals(4, integerTry.get());
    }
    @Test
    void callable(){
        Try<Integer> integerTry = Try.ofCallable(() -> 4);
        assertInstanceOf(Success.class, integerTry);
        assertEquals(4, integerTry.get());
    }

    @Test
    void explicitExceptions(){
        assertThrows(IOError.class, ()-> Try.of(() -> {
            throw new IOError(new Exception("bla"));
        }));

        //We dont know that this method will throw on IO error
        //we want our try to catch it
        Try<Integer> i = Try.of(() -> {
            ThrowControl.sneakyThrow(new IOError(new Exception("")));
            return null;
        }, t -> {});
        assertInstanceOf(Failure.class, i);
    }

    @Test
    void mapAndFlatMap(){
        Try<Integer> result = Try.of(() -> divide(10, 2))
                .map(r -> r * 2)
                .flatMap(r -> Try.of(() -> add(r, 5)));
        assertEquals(15, result.sneakyGet());
    }

    @Test
    void mapAndFlatMapWithThrow(){
        Try<Integer> result = Try.of(() -> divide(10, 2))
                .map(r -> r * 2)
                .flatMap(r -> Try.of(() -> add(r, 5)))
                .flatMap( x ->  Try.of( () -> divide(x, 0)));
        assertEquals(Failure.class, result.getClass());
    }
    
    static int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }

    static int add(int a, int b) {
        return a + b;
    }
}
