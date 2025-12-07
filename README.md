
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

Either
======
Either allows the user to return one of type types. In this implementation
the left side is the "error" and the right side is the "result". Unlike the Try
the left does not not always an Exception. Take the case below:

```java
String input = "40";
Try<Either<String,Integer>> example = Try.of(()-> new Right<>(Integer.parseInt(input)));
var x = example.getOrElse(new Left<>(input));
assertTrue(x.isRight());
assertTrue(x.contains(40));
assertFalse(x.contains(41));

```
In the failure case we are returned the actual input that caused the failure, maybe because it can
be recovered.

```java
Either<String,Integer> eitherMonad = Try.of( (Supplier<Either<String,Integer>>)
                () -> Right(Integer.parseInt(input)))
        .getOrElse(Left(input));
assertTrue(eitherMonad.isRight());
assertTrue(eitherMonad.contains(40));
```