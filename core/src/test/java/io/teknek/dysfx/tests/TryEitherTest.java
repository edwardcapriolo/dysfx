package io.teknek.dysfx.tests;

import io.teknek.dysfx.Either;
import io.teknek.dysfx.Left;
import io.teknek.dysfx.Try;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TryEitherTest {
    @Test
    void aFailedEither(){
        Try<Integer> aTry = Try.of(() -> Integer.parseInt("stuff"));
        assertFalse(aTry.isSuccess());
        Either<Throwable, Integer> results = aTry.toEither();
        assertTrue(results.isLeft());
        assertInstanceOf(Left.class, results);
    }
}
