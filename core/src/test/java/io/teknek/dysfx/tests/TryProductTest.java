package io.teknek.dysfx.tests;

import io.teknek.dysfx.Failure;
import io.teknek.dysfx.Success;
import io.teknek.dysfx.Try;
import io.teknek.dysfx.multiple.Product1;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TryProductTest {
    @Test
    void successProduct(){
        Try<Integer> t= Try.of(() -> 4);
        assertTrue(t.isSuccess());
        assertInstanceOf(Success.class, t);
        assertInstanceOf(Product1.class, t);
        Product1<Integer> p = (Product1<Integer>) t;
        assertEquals(1, p.productArity());
    }

    @Test
    void failedProduct(){
        Try<Integer> t= Try.of(() -> Integer.parseInt("bingo"));
        assertFalse(t.isSuccess());
        assertInstanceOf(Failure.class, t);
        assertInstanceOf(Product1.class, t);
        Product1<Integer> p = (Product1<Integer>) t;
        assertEquals(1, p.productArity());
    }
}
