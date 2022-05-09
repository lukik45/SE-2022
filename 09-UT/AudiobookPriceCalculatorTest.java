package put.io.testing.audiobooks;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class AudiobookPriceCalculatorTest {

    Customer customer = null;
    Audiobook audiobook = null;
    AudiobookPriceCalculator calculator = null;

    @BeforeEach
    void setUp(){
        calculator = new AudiobookPriceCalculator();
    }


    @Test
    void calculate1() {
        customer = new Customer("John", Customer.LoyaltyLevel.GOLD, true);
        audiobook = new Audiobook("LOtR", 23.99);
        Assertions.assertTrue(calculator.calculate(customer, audiobook) ==  0.0);
    }

    @Test
    void calculate2() {
        customer = new Customer("John", Customer.LoyaltyLevel.SILVER, false);
        audiobook = new Audiobook("LOtR", 23.99);
        Assertions.assertTrue(calculator.calculate(customer, audiobook) ==  23.99 * 0.9);
    }

    @Test
    void calculate3() {
        customer = new Customer("John", Customer.LoyaltyLevel.GOLD, false);
        audiobook = new Audiobook("LOtR", 23.99);
        Assertions.assertTrue(calculator.calculate(customer, audiobook) ==  23.99 * 0.8);
    }
}