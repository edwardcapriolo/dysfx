package io.teknek.dysfx.tests;

import io.teknek.dysfx.Try;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TryTransformTest {

    @Test
    void successTransform(){
        Try<Integer> t = Try.of(() -> 4);
        assertTrue(t.isSuccess());
        Try<String> next = t.transform(input -> Try.of(() -> (input * 2) + ""),
                ex -> Try.of(()-> 9 + "")
                );
        assertEquals("8", next.get());
    }

    @Test
    void failedTransform(){
        Try<Integer> t = Try.of(() -> Integer.parseInt("five"));
        assertFalse(t.isSuccess());
        Try<String> next = t.transform(input -> Try.of(() -> (input * 2) + ""),
                ex -> Try.of(()-> 9 + "")
        );
        assertEquals("9", next.get());
    }

}
