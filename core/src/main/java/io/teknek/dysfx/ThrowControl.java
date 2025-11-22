package io.teknek.dysfx;

public interface ThrowControl {
    @SuppressWarnings("unchecked")
    static <E extends Throwable> void sneakyThrow(Throwable e) throws E {
        throw (E) e;
    }

    /**
     * If you want Try to return failure consume the exception, else throw it
     * @param t some Throwable
     */
    void handle(Throwable t);

    @SuppressWarnings("unchecked")
    default <E extends Throwable> void sneakyThrows(Throwable e) throws E {
        throw (E) e;
    }
}
