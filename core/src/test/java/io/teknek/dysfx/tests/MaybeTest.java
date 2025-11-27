package io.teknek.dysfx.tests;

import io.teknek.dysfx.Maybe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MaybeTest {

    @Test
    public void nothingShouldNotEqualSomething(){
        assertNotEquals(Maybe.nothing(), Maybe.definitely(5));
    }

    @Test
    public void somethingShouldEqualItself(){
        assertEquals(Maybe.definitely(5), Maybe.definitely(5));
    }
}