package io.teknek.dysfx;

public interface ThrowControl {
    /**
     * A method with a signature that does not declare any checked Exceptions/Throwable yet will throw them.
     * @param e a throwable instance
     * @param <E> a tag used for erasure
     * @throws E this method will throw any Throwable that is passed to it.
     */
    @SuppressWarnings("unchecked")
    static <E extends Throwable> void sneakyThrow(Throwable e) throws E {
        throw (E) e;
    }

    /**
     * If you want Try to return failure consume the exception, else re throw it using sneakyThrows
     * @param t some Throwable
     */
    void handle(Throwable t);

    @SuppressWarnings("unchecked")
    default <E extends Throwable> void sneakyThrows(Throwable e) throws E {
        throw (E) e;
    }

    /**
     *
     * @return an instance that will consume any Throwable that is an instanceof RuntimeException,
     * else it will sneakyThrow the Throwable.
     */
    static ThrowControl ofRuntime(){
        return t -> {
            if (t instanceof RuntimeException){
                // eat
            } else {
                ThrowControl.sneakyThrow(t);
            }
        };
    }

    /**
     *
     * @return an instance that will consume any Throwable that is an instanceof Exception and not an instance of RuntimeException
     */
    static ThrowControl ofStandardChecked(){
        return t -> {
            if (t instanceof Exception && !(t instanceof RuntimeException)){
                //eat
            } else {
                ThrowControl.sneakyThrow(t);
            }
        };
    }
}
