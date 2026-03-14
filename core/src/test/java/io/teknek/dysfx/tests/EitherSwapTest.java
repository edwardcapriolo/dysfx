package io.teknek.dysfx.tests;

import io.teknek.dysfx.Either;
import io.teknek.dysfx.Left;
import io.teknek.dysfx.Right;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EitherSwapTest {

    @Test
    public void makeTheWrongRight(){
        Either<Integer, String> swapped = dynamic(-30).swap();
        assertEquals(Right.class, swapped.getClass());
        assertEquals("Bad value", swapped.productElement(0));
    }

    @Test
    public void makeTheRightWrong(){
        Either<Integer, String> swapped = dynamic(10).swap();
        assertEquals(Left.class, swapped.getClass());
        assertEquals(11, swapped.productElement(0));
    }

    public Either<String, Integer> dynamic(int number) {
        if (number < 0){
            return Either.Left("Bad value");
        } else {
            return Either.Right(number + 1);
        }
    }
}
