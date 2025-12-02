package io.teknek.dysfx.tests;

import io.teknek.dysfx.Either;
import io.teknek.dysfx.Left;
import io.teknek.dysfx.Right;
import io.teknek.dysfx.Try;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Supplier;

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
        Assertions.assertTrue(example.isRight());
        Assertions.assertTrue(example.contains(40));
        Assertions.assertFalse(example.contains(41));
    }

    @Test
    void rightContainsTry(){
        String input = "40";
        Try<Either<String,Integer>> example = Try.of( ()-> new Right<>(Integer.parseInt(input)));
        var x = example.getOrElse(new Left<>(input));
        Assertions.assertTrue(x.isRight());
        Assertions.assertTrue(x.contains(40));
        Assertions.assertFalse(x.contains(41));

        Either<String,Integer> y = Try.of( (Supplier<Either<String,Integer>>)
                        () -> new Right<>(Integer.parseInt(input)))
                .getOrElse(new Left<>(input));
        Assertions.assertTrue(y.isRight());
        Assertions.assertTrue(y.contains(40));
        Assertions.assertFalse(y.contains(41));
    }

}
