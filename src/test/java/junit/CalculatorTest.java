package junit;

import org.example.Calculator;
import org.example.selenium.RutubeTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalculatorTest {

    private static Calculator calculator;
    private static final Logger LOG = LoggerFactory.getLogger(RutubeTest.class);

    @BeforeAll
    public static void setUp(){
        calculator = new Calculator();
        LOG.info("Тест калькулятора сконфигурирован");
    }

    @Test
    public void divideByZeroTest(){
        Assertions.assertThrows(ArithmeticException.class, () -> {
            calculator.div(1,0);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = "невозможно поделить на ноль")
    public void upgDivideByZeroTest(String text){
        Assertions.assertEquals(calculator.uprgradedDiv(2, 0), text);
    }

    @ParameterizedTest
    @NullSource
    public void nullSumTest(Integer argument){
        Assertions.assertNull(argument);
    }

}
