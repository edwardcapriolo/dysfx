package io.teknek.dysfx;

@FunctionalInterface
public interface CheckedSupplier<T>  {
    T get() throws Throwable;
}
