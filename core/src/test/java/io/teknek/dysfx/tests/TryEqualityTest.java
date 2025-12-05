package io.teknek.dysfx.tests;

import io.teknek.dysfx.Try;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TryEqualityTest {

    Try<Integer> four = Try.of(() -> 4);
    Try<Integer> alsoFour = Try.of(() -> 4);
    Try<Integer> five = Try.of(() -> 5);

    Try<Integer> failure1 = Try.of(()-> Integer.parseInt("four"));
    Try<Integer> failure2 = Try.of(()-> Integer.parseInt("four"));

    @Test
    void successEquals(){
        assertEquals(four, alsoFour);
        assertNotEquals(four, five);
        assertEquals(four, four);
    }

    @Test
    void failureEquals(){
        assertNotEquals(failure1, failure2);
        assertEquals(failure1, failure1);
    }

    @Test
    void failureSuccess(){
        assertNotEquals(four, failure1);
    }

    @Test
    void hashCodes(){
        assertEquals(four.hashCode(), alsoFour.hashCode());
        assertEquals(four.hashCode(), alsoFour.hashCode());
    }


}
