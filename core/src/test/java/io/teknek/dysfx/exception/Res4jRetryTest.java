package io.teknek.dysfx.exception;

import io.github.resilience4j.core.functions.CheckedSupplier;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Res4jRetryTest {

    RetryConfig config = RetryConfig.custom()
            .maxAttempts(3)
            .retryOnException(e -> e instanceof Recoverable)
            .build();

    static class MyRecoverableException extends RuntimeException implements Recoverable {

    }

    @Test
    void retryExample(){
        Supplier<Integer> data = Mockito.mock(Supplier.class);
        Mockito.when(data.get()).thenThrow(new MyRecoverableException())
                .thenThrow(new MyRecoverableException()).thenReturn(4);
        Retry retry = Retry.of("this", config);
        Supplier<Integer> z = retry.decorateSupplier(data);
        assertEquals(4, z.get());
        assertEquals(3, retry.getMetrics().getNumberOfTotalCalls());
    }

    @Test
    void willNotRetry(){
        Supplier<Integer> data = () -> Integer.parseInt("fotyfivedolla");
        Retry retry = Retry.of("that", config);
        Supplier<Integer> z = retry.decorateSupplier(data);
        assertThrows(NumberFormatException.class, z::get);
    }

    @Test
    void checkedSuppplie(){
        CheckedSupplier<Integer> a = new CheckedSupplier<>(){

            @Override
            public Integer get() throws Throwable {
                return null;
            }
        };
    }
}
