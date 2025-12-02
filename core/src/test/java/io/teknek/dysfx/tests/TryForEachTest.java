package io.teknek.dysfx.tests;

import io.teknek.dysfx.Try;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.function.Consumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TryForEachTest {
    @Test
    void aSuccessForeach() {
        Consumer<Object> cons = mock(Consumer.class);
        Try<Object> aTry = Try.of(() -> Integer.parseInt("30"));
        aTry.forEach(cons);
        verify(cons, Mockito.times(1)).accept(Mockito.any());
    }

    @Test
    void aFailedForeach() {
        Consumer<Object> cons = mock(Consumer.class);
        Try<Object> aTry = Try.of(() -> Integer.parseInt("thirty"));
        aTry.forEach(cons);
        verify(cons, Mockito.never()).accept(Mockito.any());
    }
}
