
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

The beauty of Try is that you can use *match style* statements to handle cases.
This switch of sealed types requires java 21 even though the sealed types are 
supported in java17.

```java
void javaPatterns(){
    Try<Integer> integerTry = Try.of(() -> 4 );
    assertInstanceOf(Success.class, integerTry);
    switch (integerTry) {
        case Failure f -> fail("It should not fail " + f);
        case Success<Integer> i -> assertEquals(4, i.get());
    }
}
```
