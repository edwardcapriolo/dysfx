package io.teknek.dysfx.tests;

import io.teknek.dysfx.*;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TryOptionalTest {

    @Test
    void emptyButNot(){
        //null is a legitimate value
        Try<Object> aTry = Try.of(() -> null);
        assertTrue(aTry.isSuccess());
        Optional<Object> results = aTry.toOption();
        assertTrue(results.isEmpty());
    }

    @Test
    void aFailedOption(){
        Try<Object> aTry = Try.of(() -> Integer.parseInt("stuff"));
        assertFalse(aTry.isSuccess());
        Optional<Object> results = aTry.toOption();
        assertTrue(results.isEmpty());
    }

    @Test
    void aSuccessToOption(){
        Try<Object> aTry = Try.of(() -> Integer.parseInt("30"));
        assertTrue(aTry.isSuccess());
        Optional<Object> results = aTry.toOption();
        assertEquals(30, results.get());
    }
}
