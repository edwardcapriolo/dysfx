package io.teknek.dysfx.tests;

import io.teknek.dysfx.Either;
import io.teknek.dysfx.Left;
import io.teknek.dysfx.Right;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EitherAsReturnTest {

    @Test
    public void testEitherAsReturn(){
        Either<String, Integer> badAndGood = dynamic(-40);
        if (badAndGood instanceof Left<String, Integer> bad){
            String innerValue = bad.productElement(0);
            assertEquals("Bad value", innerValue);
        } else {
            Assertions.fail("can not reach here");
        }
    }

    @Test
    public void theGoodSide(){
        Either<String, Integer> badAndGood = dynamic(40);
        if (badAndGood instanceof Left<String, Integer> bad){
            Assertions.fail("can not reach here");
            String innerValue = bad.productElement(0);
            assertEquals("Bad value", innerValue);
        } else if (badAndGood instanceof Right<String, Integer> good){
            Integer innerValue = good.productElement(0);
            assertEquals(41, innerValue);
        }
    }

    public Either<String, Integer> dynamic(int number) {
        if (number < 0){
            return Either.Left("Bad value");
        } else {
            return Either.Right(number +1 );
        }
    }
}
