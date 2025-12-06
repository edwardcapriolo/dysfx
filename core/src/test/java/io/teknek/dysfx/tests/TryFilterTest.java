package io.teknek.dysfx.tests;

import io.teknek.dysfx.Success;
import io.teknek.dysfx.Try;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TryFilterTest {

    @Test
    void filterPass(){
        assertEquals( new Success<>(4), Try.of(() -> 4).filter(x -> x == 4));
    }

    @Test
    void filterFail(){
        Try<Integer> y = Try.of(() -> 4).filter(x -> x == 5);
        assertFalse(y.isSuccess());
    }
}
