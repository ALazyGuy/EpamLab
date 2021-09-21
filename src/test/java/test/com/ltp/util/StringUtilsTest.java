package test.com.ltp.util;

import com.ltp.util.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringUtilsTest {

    @Test
    public void isPositiveNumberTest(){
        assertTrue(StringUtils.isPositiveNumber("120397219073"));
        assertTrue(StringUtils.isPositiveNumber("120.397219073"));
        assertFalse(StringUtils.isPositiveNumber("-120.397219073"));

        Exception exception = assertThrows(NumberFormatException.class, () -> {
            StringUtils.isPositiveNumber("qweqwe");
        });

        assertEquals(exception.getMessage(), "qweqwe");

        exception = assertThrows(NumberFormatException.class, () -> {
            StringUtils.isPositiveNumber("123.123.123");
        });

        assertEquals(exception.getMessage(), "123.123.123");

    }

}
