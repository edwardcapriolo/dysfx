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

    static ThrowControl ofRuntime(){
        return t -> {
            if (t instanceof RuntimeException){
                // eat
            } else {
                ThrowControl.sneakyThrow(t);
            }
        };
    }
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
