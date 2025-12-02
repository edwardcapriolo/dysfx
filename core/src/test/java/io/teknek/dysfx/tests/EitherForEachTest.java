package io.teknek.dysfx.tests;

import io.teknek.dysfx.Left;
import io.teknek.dysfx.Right;
import io.teknek.dysfx.Try;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.function.Consumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EitherForEachTest {

    @Test
    void aSuccessForeach() {
        Consumer<Integer> cons = mock(Consumer.class);
        Right<String,Integer> right = new Right<>(5);
        right.forEach(cons);
        verify(cons, Mockito.times(1)).accept(Mockito.any());
    }

    @Test
    void aFailedForeach() {
        Consumer<Integer> cons = mock(Consumer.class);
        Left<String,Integer> left = new Left<>("");
        left.forEach(cons);
        verify(cons, Mockito.never()).accept(Mockito.any());
    }
}
