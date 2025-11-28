
dysfxn
======

Dysfunction, the missing monad library for java.

Try
======

The monadic try prevents the throwing of exception to the caller.
```java
@Test
void orElse(){
    Try<Integer> notFour = Try.of(() -> Integer.parseInt("four"));
    assertEquals(3, notFour.getOrElse(3));
}
```
