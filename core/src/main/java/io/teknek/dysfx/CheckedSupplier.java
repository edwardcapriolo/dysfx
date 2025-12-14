package io.teknek.dysfx;

import javax.annotation.Nullable;

@FunctionalInterface
public interface CheckedSupplier<T>  {
    @Nullable T get() throws Throwable;
}
