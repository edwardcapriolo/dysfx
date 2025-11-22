package io.teknek.dysfx.tests;

import io.teknek.dysfx.multiple.Tuple2;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class TupleTest {

    @Test
    void tupleTest(){
        Tuple2<Integer, String> tp = Tuple2.of(5, "hi");
        assertEquals(2, tp.productArity());
        assertEquals("hi", tp.getT2());
        assertEquals("hi", tp.productElement(1));
    }

    @Test
    void tupleTest2(){
        Tuple2<Integer, String> tp = Tuple2.of(5, "hi");
        assertEquals(2, tp.productArity());
        assertEquals("hi", tp.getT2());
        assertEquals("hi", tp.productElement(1));
    }

    @Test
    void tupleOutOfBounds(){
        Tuple2<Integer, String> tp = Tuple2.of(5, "hi");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> tp.productElement(7));
    }

    @Test
    void iterate(){
        Tuple2<Integer, String> tp = Tuple2.of(5, "hi");
        Iterator<Object> it = tp.productIterator();
        assertTrue(it.hasNext());
        assertEquals(5, it.next());
        assertTrue(it.hasNext());
        assertEquals("hi", it.next());
        assertFalse(it.hasNext());
    }

    @Test
    void pair(){
        Tuple2<Integer, String> tp1 = Tuple2.of(5, "hi");
        Tuple2<Integer, String> tp2 = Tuple2.of(5, "hi");
        assertEquals(tp1, tp2);
        Tuple2<Integer, String> tp3 = Tuple2.of(5, "bye");
        assertNotEquals(tp2, tp3);

        Tuple2<Integer, String> tp4 = Tuple2.of(5, null);
        assertNotEquals(tp3, tp4);

    }
}
