package io.teknek.dysfx.tests;

import io.teknek.dysfx.CheckedSupplier;
import io.teknek.dysfx.Failure;
import io.teknek.dysfx.ThrowControl;
import io.teknek.dysfx.Try;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TryThrowControlTest {

    CheckedSupplier<String> supplyThrowsRuntime = () -> {
        throw new RuntimeException();
    };

    CheckedSupplier<String> supplyThrowsException = () -> {
        throw new SQLException("the one");
    };

    CheckedSupplier<String> supplyThrowsThrowable = () -> {
        throw new Throwable("the two");
    };

    CheckedSupplier<String> supplyThrowsError = () -> {
        throw new Error("out of memory");
    };

    @Test
    void modifyCheckedSupplierControl(){
        assertThrows(SQLException.class, () ->
                Try.ofCheckedSupplier(supplyThrowsException, ThrowControl.ofRuntime()));
        assertInstanceOf(Failure.class,
                Try.ofCheckedSupplier(supplyThrowsException, ThrowControl.ofStandardChecked()));
        assertInstanceOf(Failure.class,
                Try.ofCheckedSupplier(supplyThrowsRuntime, ThrowControl.ofRuntime()));
        assertThrows(Error.class, () ->
                Try.ofCheckedSupplier(supplyThrowsError, ThrowControl.ofStandardChecked()));
    }
}
