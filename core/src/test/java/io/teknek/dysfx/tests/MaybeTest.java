package io.teknek.dysfx.tests;

import io.teknek.dysfx.Maybe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MaybeTest {

    @Test
    public void nothingShouldNotEqualSomething(){
        assertNotEquals(Maybe.nothing(), Maybe.definitely(5));
    }

    @Test
    public void somethingShouldEqualItself(){
        assertEquals(Maybe.definitely(5), Maybe.definitely(5));
    }

    @Test
    void nullEquality(){
        assertEquals(Maybe.assuredlyNull(null), Maybe.assuredlyNull(null));
        assertNotEquals(Maybe.assuredlyNull(null), Maybe.definitely(5));
    }

    @Test
    void somethingEquality(){
        assertEquals(Maybe.definitely(5), Maybe.definitely(5));
        assertNotEquals(Maybe.definitely(5), Maybe.definitely(4));
        assertNotEquals(Maybe.definitely(5), Maybe.assuredlyNull(null));
        assertNotEquals(Maybe.definitely(5), Maybe.nothing());
    }

    @Test
    void somethingHashing(){
        assertEquals(Maybe.definitely(5).hashCode(), Maybe.definitely(5).hashCode());
        assertNotEquals(Maybe.definitely(5).hashCode(), Maybe.definitely(4).hashCode());
    }

    @Test
    void nothingEquality(){
        assertNotEquals(Maybe.nothing(), Maybe.definitely(5));
        assertEquals(Maybe.nothing(), Maybe.nothing());
    }
}