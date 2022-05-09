package put.io.testing.junit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    Calculator calculator = null;

    @BeforeEach
    private void setUp() {
        calculator = new Calculator();
    }

    @Test
     public void testAdd() {
        assertSame(calculator.add(2,5), 7);
        assertSame(calculator.add(-23,0), -23);
        assertSame(calculator.add(0,0), 0);
    }

    @Test
    public void testMultiply() {
        assertSame(calculator.multiply(2,5), 10);
        assertSame(calculator.multiply(-23,0), 0);
        assertSame(calculator.multiply(0,0), 0);
    }

    @Test
    public void testAddPositiveNumbers() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            calculator.addPositiveNumbers(-2, 5);
        });
    }
}