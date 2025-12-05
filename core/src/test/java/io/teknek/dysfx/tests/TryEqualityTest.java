package io.teknek.dysfx.tests;

import io.teknek.dysfx.Try;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TryEqualityTest {

    Try<Integer> four = Try.of(() -> 4);
    Try<Integer> alsoFour = Try.of(() -> 4);
    Try<Integer> five = Try.of(() -> 5);
    
    @Test
    void equals(){
        assertEquals(four, alsoFour);
        assertNotEquals(four, five);
    }

    @Test
    void hashCodes(){
        assertEquals(four.hashCode(), alsoFour.hashCode());
        assertEquals(four.hashCode(), alsoFour.hashCode());
    }
}
