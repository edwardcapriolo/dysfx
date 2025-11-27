package io.teknek.dysfx.tests;

import io.teknek.dysfx.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TryMaybeTest {

    @Test
    void nullReturningTry(){
        //null is a legitimate value
        Try<Object> aTry = Try.of(() -> null);
        assertTrue(aTry.isSuccess());
        Maybe<Object> results = aTry.toMaybe();
        assertInstanceOf(Null.class, results);
        if (results instanceof Null<Object> t){
            assertEquals(1, t.productArity());
            assertEquals(Null.INSTANCE, t.productIterator().next());
        } else {
            fail();
        }
    }

    @Test
    void aFailingMaybe(){
        // a throwing maybe should become a nothing
        Try<Object> aTry = Try.of(() -> Integer.parseInt("thirty"));
        assertFalse(aTry.isSuccess());
        Maybe<Object> results = aTry.toMaybe();
        assertInstanceOf(Nothing.class, results);
        if (results instanceof Nothing<Object> t){
            assertEquals(0, t.productArity());
        } else {
            fail();
        }
    }

    @Test
    void aSuccessMaybe(){
        // a success maybe should be a something
        Try<Object> aTry = Try.of(() -> Integer.parseInt("30"));
        assertTrue(aTry.isSuccess());
        Maybe<Object> results = aTry.toMaybe();
        assertInstanceOf(Something.class, results);
        if (results instanceof Something<Object> t){
            assertEquals(30, t.get());
            assertEquals(1, t.productArity());
            assertEquals(30, t.productIterator().next());
        } else {
            fail();
        }
    }
}
