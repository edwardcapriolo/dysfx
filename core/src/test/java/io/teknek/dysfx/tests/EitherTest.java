package io.teknek.dysfx.tests;

import io.teknek.dysfx.Either;
import io.teknek.dysfx.Left;
import io.teknek.dysfx.Right;
import io.teknek.dysfx.Try;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Supplier;
import static io.teknek.dysfx.Either.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EitherTest {

    @Test
    void rightContains(){
        String input = "40";
        Either<String,Integer> example = null;
        try {
            example = Either.right(Integer.parseInt(input));
        } catch (NumberFormatException ex){
            example = Either.left(input);
        }
        assertTrue(example.isRight());
        assertTrue(example.contains(40));
        assertFalse(example.contains(41));
    }

    @Test
    void rightContainsTry(){
        String input = "40";
        Try<Either<String,Integer>> example = Try.of(()-> new Right<>(Integer.parseInt(input)));
        var x = example.getOrElse(new Left<>(input));
        assertTrue(x.isRight());
        assertTrue(x.contains(40));
        assertFalse(x.contains(41));

        Either<String,Integer> eitherMonad = Try.of( (Supplier<Either<String,Integer>>)
                        () -> Right(Integer.parseInt(input)))
                .getOrElse(Left(input));
        assertTrue(eitherMonad.isRight());
        assertTrue(eitherMonad.contains(40));
        assertFalse(eitherMonad.contains(41));
    }

    @Test
    void exists(){
        assertTrue(Right(40).exists((p) -> p == 40));
        assertFalse(Right(40).exists((p) -> p == 41));
        assertFalse(Left(new RuntimeException("")).exists(Objects::isNull));
    }
}
