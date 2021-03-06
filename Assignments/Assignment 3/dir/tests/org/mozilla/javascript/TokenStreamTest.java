package org.mozilla.javascript;

import static org.junit.Assert.*;

import java.io.StringReader;

import java.io.IOException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

public class TokenStreamTest {
    private static final Object[] EMPTY = {};

    public String reflectProperty(TokenStream ts, String member) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class tokenStreamClass = ts.getClass();

        Field fields[] = tokenStreamClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName() == member) {
                fields[i].setAccessible(true);
                if (fields[i].get(ts) == null) {
                    return null;
                }
                return (String) fields[i].get(ts);
            }
        }
        return null;
    }

    public Object reflectPropertyObject(TokenStream ts, String member) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class tokenStreamClass = ts.getClass();

        Field fields[] = tokenStreamClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName() == member) {
                fields[i].setAccessible(true);
                return fields[i].get(ts);
            }
        }
        throw new IllegalArgumentException();
    }

    public int reflectPropertyInt(TokenStream ts, String member) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class tokenStreamClass = ts.getClass();

        Field fields[] = tokenStreamClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName() == member) {
                fields[i].setAccessible(true);
                return (Integer) fields[i].get(ts);
            }
        }
        throw new IllegalArgumentException();
    }

    public void reflectAll(TokenStream ts) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class tokenStreamClass = ts.getClass();

        Method methods[] = tokenStreamClass.getDeclaredMethods();
        System.out.println("Access all the methods");
        for (int i = 0; i < methods.length; i++) {
            System.out.println("Method name: " + methods[i].getName());
            System.out.println("Return type: " + methods[i].getReturnType());
            methods[i].setAccessible(true);
            //System.out.println(methods[i].invoke(instance, EMPTY) + "\n");
        }

        Field fields[] = tokenStreamClass.getDeclaredFields();
        System.out.println("Access all the fields");
        for (int i = 0; i < fields.length; i++) {
            System.out.println("Field name: " + fields[i].getName());
            fields[i].setAccessible(true);
            System.out.println(fields[i].get(ts) + "\n");
        }
    }

    /*
     *============================================================
     *Tests Begin Here
     *============================================================
     */

    @Test
    public void testTokenStreamNullReader() {
        // Blackbox: tests the constructor function
        // Input: null sourceReader, javascript sourceString
        // Expected output: non-null sourceString, null sourceReader, sourceEnd >= 0, sourceCursor, cursor = 0

        TokenStream ts = new TokenStream(null, "\\u0041 = y; x = 1 + 9; y = x++; y = this.a;", 0);

        try {
            assertTrue(reflectProperty(ts, "sourceString") == "\\u0041 = y; x = 1 + 9; y = x++; y = this.a;");
            assertNull(reflectProperty(ts, "sourceReader"));
            assertTrue(reflectPropertyInt(ts, "sourceEnd") >= 0);
            assertTrue(reflectPropertyInt(ts, "sourceCursor") == 0);
            assertTrue(reflectPropertyInt(ts, "cursor") == 0);
        } catch (Exception e) {
            //e.printStackTrace();
            fail("Reflecting exceptions thrown");
        }
    }

    @Test
    public void testTokenStreamNullStringJS() {
        // Blackbox: tests the constructor function with .js
        // input: javascript sourcereader, null sourcestring
        // expected output: null sourceString, non-null sourceReader, sourceEnd >= 0, sourceCursor, cursor = 0

        TokenStream ts = new TokenStream(new StringReader("\\u0041 = y; x = 1 + 9; y = x++; y = this.a;"), "", 0);

        try {
            assertNull(reflectProperty(ts, "sourceString"));
            assertNotNull(reflectPropertyObject(ts, "sourceReader"));
            assertTrue(reflectPropertyInt(ts, "sourceEnd") >= 0);
            assertTrue(reflectPropertyInt(ts, "sourceCursor") == 0);
            assertTrue(reflectPropertyInt(ts, "cursor") == 0);
        } catch (Exception e) {
            fail("Reflecting exceptions thrown");
        }
    }

    @Test
    public void testTokenStreamNullStringEmpty() {
        // Blackbox: tests the constructor function without .js
        // Input: empty string sourceReader, null sourceString
        // expected output: null sourceString, non-null sourceReader, sourceEnd >= 0, sourceCursor, cursor = 0

        TokenStream ts = new TokenStream(new StringReader(""), null, 0);

        try {
            assertNull(reflectProperty(ts, "sourceString"));
            assertNotNull(reflectPropertyObject(ts, "sourceReader"));
            assertTrue(reflectPropertyInt(ts, "sourceEnd") >= 0);
            assertTrue(reflectPropertyInt(ts, "sourceCursor") == 0);
            assertTrue(reflectPropertyInt(ts, "cursor") == 0);
        } catch (Exception e) {
            fail("Reflecting exceptions thrown");
        }
    }

    @Test
    public void testStringToKeywordValid() {
        // Blackbox: tests if the stringToKeyword function correctly maps keywords to their values
        // Input: name = else, false, function, if, return, true
        // Expected output: 113, 44, 109, 112, 4, 45

        TokenStream ts = new TokenStream(null, "", 0);
        String[] keywords = {"else", "false", "function", "if", "return", "true"};
        int[] values = {Token.ELSE, Token.FALSE, Token.FUNCTION, Token.IF, Token.RETURN, Token.TRUE};

        for (int i = 0; i < keywords.length; i++) {
            try {
                assertTrue(ts.stringToKeyword(keywords[i]) == values[i]);
            } catch (Exception e) {
                fail("Reflecting exceptions thrown");
            }
        }
    }

    @Test
    public void testStringToKeywordInvalid() {
        // Blackbox: tests if the stringToKeyword function correctly maps non-keywords to Token.EOF (0)
        // Input: name = gibberish, (none)
        // Expected output: Token.EOF (0)

        TokenStream ts = new TokenStream(null, "", 0);
        String[] strings = {"garbage", ""};

        for (int i = 0; i < strings.length; i++) {
            try {
                assertTrue(ts.stringToKeyword(strings[i]) == Token.EOF);
            } catch (Exception e) {
                fail("Reflecting exceptions thrown");
            }
        }
    }

    @Test
    public void testStringToNumber10_0() {
        // Blackbox: tests if stringToNumber converts strings to base 10 numbers;
        // Input: s = 0, 1, 5, 9, 10, 11, start = 0, radix = 10
        // Expected output: result = 0, 1, 5, 9, 10, 11

        String[] string = {"0", "1", "5", "9", "10", "11"};
        int[] number = {0, 1, 5, 9, 10, 11};

        for (int i = 0; i < string.length; i++) {
            try {
                assertTrue(TokenStream.stringToNumber(string[i], 0, 10) == number[i]);
                assertFalse(Double.isNaN(TokenStream.stringToNumber(string[i], 0, 10)));
            } catch (Exception e) {
                fail("Unexpected exceptions thrown");
            }
        }
    }

    @Test
    public void testStringToNumber10Other() {
        // Blackbox: tests if stringToNumber converts strings to base 10 numbers;
        // Input: s = 10, 15, 146, start = 1, radix = 10
        // Expected output: result = 0, 5, 46

        String[] string = {"10", "15", "146"};
        int[] number = {0, 5, 46};

        for (int i = 0; i < string.length; i++) {
            try {
                assertTrue(TokenStream.stringToNumber(string[i], 1, 10) == number[i]);
                assertFalse(Double.isNaN(TokenStream.stringToNumber(string[i], 1, 10)));
            } catch (Exception e) {
                fail("Unexpected exceptions thrown");
            }
        }
    }

    @Test
    public void testStringToNumber8_0() {
        // Blackbox: tests if stringToNumber converts strings to base 8 numbers;
        // Input: s = 0, 1, 5, 7, start = 0, radix = 8
        // Expected output: result = 0, 1, 5, 7

        String[] string = {"0", "1", "5", "7"};
        int[] number = {0, 1, 5, 7};

        for (int i = 0; i < string.length; i++) {
            try {
                assertTrue(TokenStream.stringToNumber(string[i], 0, 8) == number[i]);
                assertFalse(Double.isNaN(TokenStream.stringToNumber(string[i], 0, 8)));
            } catch (Exception e) {
                fail("Unexpected exceptions thrown");
            }
        }
    }

    @Test
    public void testStringToNumber8Other() {
        // Blackbox: tests if stringToNumber converts strings to base 8 numbers;
        // Input: s = 10, 15 start = 1, radix = 8
        // Expected output: result = 0, 5

        String[] string = {"10", "15"};
        int[] number = {0, 5};

        for (int i = 0; i < string.length; i++) {
            try {
                assertTrue(TokenStream.stringToNumber(string[i], 1, 8) == number[i]);
                assertFalse(Double.isNaN(TokenStream.stringToNumber(string[i], 1, 8)));
            } catch (Exception e) {
                fail("Unexpected exceptions thrown");
            }
        }
    }

    @Test
    public void testStringToNumber8Other2() {
        // Blackbox: tests if stringToNumber converts strings to base 8 numbers;
        // Input: s = 147, start = 1, radix = 8
        // Expected output: result = 0, 5, 7

        try {
            assertTrue(TokenStream.stringToNumber("147", 2, 8) == 7);
            assertFalse(Double.isNaN(TokenStream.stringToNumber("147", 1, 8)));
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testStringToNumber16_0() {
        // Blackbox: tests if stringToNumber converts strings to base 16 numbers;
        // Input: s = 0, 1, 15, 16, E, AE, start = 0, radix = 16
        // Expected output: result = 0, 1, 5, 9, 10, 11

        String[] string = {"0", "1", "9", "10", "16", "E", "AE"};
        int[] number = {0, 1, 9, 16, 22, 14, 174};

        for (int i = 0; i < string.length; i++) {
            try {
                assertTrue(TokenStream.stringToNumber(string[i], 0, 16) == number[i]);
                assertFalse(Double.isNaN(TokenStream.stringToNumber(string[i], 0, 16)));
            } catch (Exception e) {
                fail("Unexpected exceptions thrown");
            }
        }
    }

    @Test
    public void testStringToNumber16Bug() {
        // Blackbox: tests if stringToNumber fails to convert "F" to a base 16 number;
        // Input: s = f, F, start = 0, radix = 16
        // Expected output: failure because stringToNumber isn't working

        try {
            assertTrue(TokenStream.stringToNumber("f", 0, 16) == 15);
            assertFalse(Double.isNaN(TokenStream.stringToNumber("f", 0, 16)));
            assertTrue(TokenStream.stringToNumber("F", 0, 16) == 15);
            assertFalse(Double.isNaN(TokenStream.stringToNumber("F", 0, 16)));
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testStringToNumber16Other() {
        // Blackbox: tests if stringToNumber converts strings to base 16 numbers;
        // Input: s = 10, 16, AE, start = 1, radix = 16
        // Expected output: result = 0, 7, 14

        String[] string = {"10", "207", "AE"};
        int[] number = {0, 7, 14};

        for (int i = 0; i < string.length; i++) {
            try {
                assertTrue(TokenStream.stringToNumber(string[i], 1, 16) == number[i]);
                assertFalse(Double.isNaN(TokenStream.stringToNumber(string[i], 1, 16)));
            } catch (Exception e) {
                fail("Unexpected exceptions thrown");
            }
        }
    }

    @Test
    public void testStringToNumberValid() {
        // Glassbox: tests if stringToNumber get the right inputs;
        // Input: non-null s, start = 0, 1, 3, radix = 10, 8, 16
        // Expected output: a number of type double

        String[] string = {"0", "1", "13", "a"};
        int[] radix = {10, 8, 16};
        int[] start = {0, 1, 2};

        // Basic all radix
        for (int i = 0; i < radix.length; i++) {
            try {
                assertFalse(Double.isNaN(TokenStream.stringToNumber("0", 0, radix[i])));
            } catch (Exception e) {
                fail("Giving NaN when is a number");
            }
        }

        // Long all radix
        for (int i = 0; i < radix.length; i++) {
            try {
                assertFalse(Double.isNaN(TokenStream.stringToNumber("15", 0, radix[i])));
            } catch (Exception e) {
                fail("Giving NaN when is a number");
            }
        }

        // Base 16
        for (int i = 0; i < string.length; i++) {
            try {
                assertFalse(Double.isNaN(TokenStream.stringToNumber(string[i], 0, 16)));
            } catch (Exception e) {
                fail("Giving NaN when is a number");
            }
        }

        // Different start indexes
        for (int i = 0; i < start.length; i++) {
            try {
                assertFalse(Double.isNaN(TokenStream.stringToNumber("164", start[i], 10)));
            } catch (Exception e) {
                fail("Giving NaN when is a number");
            }
        }
    }

    @Test
    public void testStringToNumberNaN() {
        // Blackbox: tests if stringToNumber handles bad inputs;
        // Input: s = a, 1, start = 0, 1, radix = 8, 10
        // Expected output: NaN

        try {
            // radix != 10, 8, 16 ==> NaN
            assertTrue(Double.isNaN(TokenStream.stringToNumber("8", 0, 8)));
            assertTrue(Double.isNaN(TokenStream.stringToNumber("9", 0, 8)));
            assertTrue(Double.isNaN(TokenStream.stringToNumber("a", 0, 8)));
            assertTrue(Double.isNaN(TokenStream.stringToNumber("1", 1, 10)));
        } catch (Exception e) {
            fail("Not getting NaN");
        }
    }

    @Test
    public void testIsJSSpace() {
        // Glassbox: tests if isJSSpace handles whitespace characters
        // Input: c = 0x20, 0x9, 0xC, 0xB, 0xA0
        // Expected output: result == true

        TokenStream ts = new TokenStream(null, "", 0);
        int[] values = {0x20, 0x9, 0xC, 0xB, 0xA0};

        for (int i = 0; i < values.length; i++) {
            assertTrue(ts.isJSSpace(values[i]));
        }
    }

    @Test
    public void testIsJSSpaceInvalid() {
        // Blackbox: tests if isJSSpace handles other characters
        // Input: c = 0, 1, 127, 128
        // Expected output: result == false

        TokenStream ts = new TokenStream(null, "", 0);
        int[] values = {-1, 0, 1, 127, 128};

        for (int i = 0; i < values.length; i++) {
            assertFalse(ts.isJSSpace(values[i]));
        }
    }

    @Test
    public void testGetString() {
        // BlackBox: Tests if a random input produces the correct output
        // Input: "int"
        // Expected Output: result = "int"

        TokenStream ts = new TokenStream(new StringReader("int"), "", 0);

        try {
            ts.getToken();
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
        assertEquals(ts.getString(), "int");
    }

    @Test
    public void testGetStringNull() {
        // BlackBox: Tests if the string member is initialized to ""
        // Input: "int"
        // Expected Output: result = ""

        TokenStream ts = new TokenStream(new StringReader("int"), "", 0);
        assertEquals(ts.getString(), "");
    }


    @Test
    public void testGetNumber() {
        // BlackBox: Tests if a random input produces the correct output
        // Input: "56"
        // Expected Output: result == 56

        TokenStream ts = new TokenStream(new StringReader("56"), "", 0);

        try {
            ts.getToken();
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
        assertTrue(ts.getNumber() == 56);
    }

    @Test
    public void testGetNumberNull() {
        // BlackBox: Tests if the number member is initialized to 0
        // Input: "56"
        // Expected Output: result == 0

        TokenStream ts = new TokenStream(new StringReader("abc"), "", 0);

        try {
            ts.getToken();
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
        assertTrue(ts.getNumber() == 0);
    }


    @Test
    public void testIsNumberOctal() {
        // BlackBox: Tests if the function works
        // Input: "05"
        // Expected Output: result == true

        TokenStream ts = new TokenStream(new StringReader("05"), "", 0);
        try {
            ts.getToken();
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
        assertTrue(ts.isNumberOctal());
    }

    @Test
    public void testIsNumberOctalNonOctal() {
        // BlackBox: Tests if the function works
        // Input: "0x"
        // Expected Output: result == false

        TokenStream ts = new TokenStream(new StringReader("0x"), "", 0);
        try {
            ts.getToken();
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
        assertFalse(ts.isNumberOctal());
    }

    @Test
    public void testEof() {
        // BlackBox: Tests if the function works
        // Input: "int"
        // Expected Output: result == true

        TokenStream ts = new TokenStream(new StringReader("int"), "", 0);

        try {
            ts.getToken();
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
        assertTrue(ts.eof());
    }

    @Test
    public void testEofNonEnd() {
        // BlackBox: Tests if the function works
        // Input: "int x = 10;"
        // Expected Output: result == false

        TokenStream ts = new TokenStream(new StringReader("int x = 10;"), "", 0);

        try {
            ts.getToken();
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
        assertFalse(ts.eof());
    }

    /*
     *============================================================
     *getToken() Tests Begin Here
     *============================================================
     */

    @Test
    public void testGetTokenJS() {
        // Blackbox: tests if getToken gives a correct result when given JS
        // Input: null sourceString, non-null sourceReader
        // Expected output: -1 <= result <= 162

        TokenStream ts = new TokenStream(new StringReader("\\u0041 = y; x = 1 + 9; y = x++; y = this.a;"), null, 0);

        try {
            assertNotNull(reflectPropertyObject(ts, "stringBuffer"));
            assertNull(reflectProperty(ts, "sourceString"));
            assertNotNull(reflectPropertyObject(ts, "sourceBuffer"));
            assertNotNull(reflectPropertyObject(ts, "sourceReader"));
            assertTrue(ts.getToken() >= -1);
            assertTrue(ts.getToken() <= 162);
        } catch (Exception e) {
            fail("Reflecting exceptions thrown");
        }
    }

    @Test
    public void testGetTokenEmpty() {
        // Blackbox: tests if getToken gives a correct result without JS
        // Input: null sourceString, non-null sourceReader
        // Expected output: -1 <= result <= 162

        TokenStream ts = new TokenStream(new StringReader(""), null, 0);

        try {
            assertNotNull(reflectPropertyObject(ts, "stringBuffer"));
            assertNull(reflectProperty(ts, "sourceString"));
            assertNotNull(reflectPropertyObject(ts, "sourceBuffer"));
            assertNotNull(reflectPropertyObject(ts, "sourceReader"));
            assertTrue(ts.getToken() >= -1);
            assertTrue(ts.getToken() <= 162);
        } catch (Exception e) {
            fail("Reflecting exceptions thrown");
        }
    }

    @Test
    public void testGetToken_EOL() {
        // GlassBox: Tests the EOL return path
        // Input: sourceString = "", sourceReader = "\n"
        // Expected Output: result == Token.EOL
        TokenStream ts = new TokenStream(new StringReader("\n"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.EOL);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_EOF() {
        // GlassBox: Tests the EOF return path
        // Input: sourceString = "", sourceReader = ""
        // Expected Output: result == Token.EOF
        TokenStream ts = new TokenStream(new StringReader(""), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.EOF);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_ERROR1() {
        // GlassBox: Tests the ERROR return path with 1 iteration
        // Input: sourceString = "\\u", sourceReader = ""
        // Expected Output: result == Token.ERROR
        TokenStream ts = new TokenStream(new StringReader("\\u"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.ERROR);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_ERROR2() {
        // GlassBox: Tests the ERROR return path with 2 iterations
        // Input: sourceString = "\\ua", sourceReader = ""
        // Expected Output: result == Token.ERROR
        TokenStream ts = new TokenStream(new StringReader("\\ua"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.ERROR);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_ERROR3() {
        // GlassBox: Tests the ERROR return path
        // Input: sourceString = "_\\", sourceReader = ""
        // Expected Output: result == Token.ERROR
        TokenStream ts = new TokenStream(new StringReader("_\\"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.ERROR);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_ELSE() {
        // GlassBox: Tests the keyword return path
        // Input: sourceString = "else", sourceReader = ""
        // Expected Output: result == Token.ELSE
        TokenStream ts = new TokenStream(new StringReader("else"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.ELSE);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_RETURN() {
        // GlassBox: Tests the keyword return path
        // Input: sourceString = "return", sourceReader = ""
        // Expected Output: result == Token.RETURN
        TokenStream ts = new TokenStream(new StringReader("return"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.RETURN);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_IF() {
        // GlassBox: Tests the keyword return path
        // Input: sourceString = "if", sourceReader = ""
        // Expected Output: result == Token.IF
        TokenStream ts = new TokenStream(new StringReader("if"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.IF);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_TRUE() {
        // GlassBox: Tests the keyword return path
        // Input: sourceString = "true", sourceReader = ""
        // Expected Output: result == Token.TRUE
        TokenStream ts = new TokenStream(new StringReader("true"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.TRUE);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_FALSE() {
        // GlassBox: Tests the keyword return path
        // Input: sourceString = "false", sourceReader = ""
        // Expected Output: result == Token.FALSE
        TokenStream ts = new TokenStream(new StringReader("false"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.FALSE);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_FUNCTION() {
        // GlassBox: Tests the keyword return path
        // Input: sourceString = "function", sourceReader = ""
        // Expected Output: result == Token.FUNCTION
        TokenStream ts = new TokenStream(new StringReader("function"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.FUNCTION);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_NAME() {
        // GlassBox: Tests the keyword return path
        // Input: sourceString = "abc", sourceReader = ""
        // Expected Output: result == Token.NAME
        TokenStream ts = new TokenStream(new StringReader("abc"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.NAME);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_NUMBER_hex() {
        // GlassBox: Tests the NUMBER return path
        // Input: sourceString = "0xf", sourceReader = ""
        // Expected Output: result == Token.NUMBER
        TokenStream ts = new TokenStream(new StringReader("0xf"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.NUMBER);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_NUMBER_octal() {
        // GlassBox: Tests the NUMBER return path
        // Input: sourceString = "05", sourceReader = ""
        // Expected Output: result == Token.NUMBER
        TokenStream ts = new TokenStream(new StringReader("05"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.NUMBER);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_SEMI() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = ";", sourceReader = ""
        // Expected Output: result == Token.SEMI
        TokenStream ts = new TokenStream(new StringReader(";"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.SEMI);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_LB() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "[", sourceReader = ""
        // Expected Output: result == Token.LB
        TokenStream ts = new TokenStream(new StringReader("["), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.LB);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_RB() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "]", sourceReader = ""
        // Expected Output: result == Token.RB
        TokenStream ts = new TokenStream(new StringReader("]"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.RB);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_RC() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "}", sourceReader = ""
        // Expected Output: result == Token.RC
        TokenStream ts = new TokenStream(new StringReader("}"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.RC);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_LC() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "{", sourceReader = ""
        // Expected Output: result == Token.LC
        TokenStream ts = new TokenStream(new StringReader("{"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.LC);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_RP() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = ")", sourceReader = ""
        // Expected Output: result == Token.RP
        TokenStream ts = new TokenStream(new StringReader(")"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.RP);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_LP() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "(", sourceReader = ""
        // Expected Output: result == Token.LP
        TokenStream ts = new TokenStream(new StringReader("("), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.LP);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_COMMA() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = ",", sourceReader = ""
        // Expected Output: result == Token.COMMA
        TokenStream ts = new TokenStream(new StringReader(","), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.COMMA);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_HOOK() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "?", sourceReader = ""
        // Expected Output: result == Token.HOOK
        TokenStream ts = new TokenStream(new StringReader("?"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.HOOK);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_COLON() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = ":", sourceReader = ""
        // Expected Output: result == Token.COLON
        TokenStream ts = new TokenStream(new StringReader(":"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.COLON);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_COLONCOLON() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "::", sourceReader = ""
        // Expected Output: result == Token.COLONCOLON
        TokenStream ts = new TokenStream(new StringReader("::"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.COLONCOLON);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_DOT() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = ".", sourceReader = ""
        // Expected Output: result == Token.DOT
        TokenStream ts = new TokenStream(new StringReader("."), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.DOT);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_DOTDOT() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "..", sourceReader = ""
        // Expected Output: result == Token.DOTDOT
        TokenStream ts = new TokenStream(new StringReader(".."), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.DOTDOT);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_DOTQUERY() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = ".(", sourceReader = ""
        // Expected Output: result == Token.DOTQUERY
        TokenStream ts = new TokenStream(new StringReader(".("), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.DOTQUERY);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_BITOR() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "|", sourceReader = ""
        // Expected Output: result == Token.BITOR
        TokenStream ts = new TokenStream(new StringReader("|"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.BITOR);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_OR() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "||", sourceReader = ""
        // Expected Output: result == Token.OR
        TokenStream ts = new TokenStream(new StringReader("||"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.OR);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_ASSIGN_BITOR() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "|=", sourceReader = ""
        // Expected Output: result == Token.ASSIGN_BITOR
        TokenStream ts = new TokenStream(new StringReader("|="), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.ASSIGN_BITOR);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_BITXOR() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "^", sourceReader = ""
        // Expected Output: result == Token.BITXOR
        TokenStream ts = new TokenStream(new StringReader("^"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.BITXOR);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_ASSIGN_BITXOR() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "^=", sourceReader = ""
        // Expected Output: result == Token.ASSIGN_BITXOR
        TokenStream ts = new TokenStream(new StringReader("^="), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.ASSIGN_BITXOR);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_AND() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "&&", sourceReader = ""
        // Expected Output: result == Token.AND
        TokenStream ts = new TokenStream(new StringReader("&&"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.AND);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_BITAND() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "&", sourceReader = ""
        // Expected Output: result == Token.BITAND
        TokenStream ts = new TokenStream(new StringReader("&"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.BITAND);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_ASSIGN_BITAND() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "&=", sourceReader = ""
        // Expected Output: result == Token.ASSIGN_BITAND
        TokenStream ts = new TokenStream(new StringReader("&="), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.ASSIGN_BITAND);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_SHEQ() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "===", sourceReader = ""
        // Expected Output: result == Token.SHEQ
        TokenStream ts = new TokenStream(new StringReader("==="), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.SHEQ);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_EQ() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "==", sourceReader = ""
        // Expected Output: result == Token.EQ
        TokenStream ts = new TokenStream(new StringReader("=="), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.EQ);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_ASSIGN() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "=", sourceReader = ""
        // Expected Output: result == Token.ASSIGN
        TokenStream ts = new TokenStream(new StringReader("="), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.ASSIGN);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_SHNE() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "!==", sourceReader = ""
        // Expected Output: result == Token.SHNE
        TokenStream ts = new TokenStream(new StringReader("!=="), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.SHNE);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_NE() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "!=", sourceReader = ""
        // Expected Output: result == Token.NE
        TokenStream ts = new TokenStream(new StringReader("!="), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.NE);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_NOT() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "!", sourceReader = ""
        // Expected Output: result == Token.NOT
        TokenStream ts = new TokenStream(new StringReader("!"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.NOT);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_ASSIGN_LSH() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "<<=", sourceReader = ""
        // Expected Output: result == Token.ASSIGN_LSH
        TokenStream ts = new TokenStream(new StringReader("<<="), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.ASSIGN_LSH);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_LSH() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "<<", sourceReader = ""
        // Expected Output: result == Token.LSH
        TokenStream ts = new TokenStream(new StringReader("<<"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.LSH);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_LT() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "<", sourceReader = ""
        // Expected Output: result == Token.LT
        TokenStream ts = new TokenStream(new StringReader("<"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.LT);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_LE() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "<=", sourceReader = ""
        // Expected Output: result == Token.LE
        TokenStream ts = new TokenStream(new StringReader("<="), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.LE);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_ASSIGN_URSH() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = ">>>=", sourceReader = ""
        // Expected Output: result == Token.ASSIGN_URSH
        TokenStream ts = new TokenStream(new StringReader(">>>="), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.ASSIGN_URSH);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_URSH() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = ">>>", sourceReader = ""
        // Expected Output: result == Token.URSH
        TokenStream ts = new TokenStream(new StringReader(">>>"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.URSH);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_ASSIGN_RSH() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = ">>=", sourceReader = ""
        // Expected Output: result == Token.ASSIGN_RSH
        TokenStream ts = new TokenStream(new StringReader(">>="), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.ASSIGN_RSH);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_RSH() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = ">>", sourceReader = ""
        // Expected Output: result == Token.RSH
        TokenStream ts = new TokenStream(new StringReader(">>"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.RSH);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_GT() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = ">", sourceReader = ""
        // Expected Output: result == Token.GT
        TokenStream ts = new TokenStream(new StringReader(">"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.GT);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_GE() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = ">=", sourceReader = ""
        // Expected Output: result == Token.GE
        TokenStream ts = new TokenStream(new StringReader(">="), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.GE);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_ASSIGN_MUL() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "*=", sourceReader = ""
        // Expected Output: result == Token.ASSIGN_MUL
        TokenStream ts = new TokenStream(new StringReader("*="), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.ASSIGN_MUL);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_MUL() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "*", sourceReader = ""
        // Expected Output: result == Token.MUL
        TokenStream ts = new TokenStream(new StringReader("*"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.MUL);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_ASSIGN_DIV() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "/=", sourceReader = ""
        // Expected Output: result == Token.ASSIGN_DIV
        TokenStream ts = new TokenStream(new StringReader("/="), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.ASSIGN_DIV);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_DIV() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "/", sourceReader = ""
        // Expected Output: result == Token.DIV
        TokenStream ts = new TokenStream(new StringReader("/"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.DIV);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_ASSIGN_MOD() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "%=", sourceReader = ""
        // Expected Output: result == Token.ASSIGN_MOD
        TokenStream ts = new TokenStream(new StringReader("%="), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.ASSIGN_MOD);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_MOD() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "%", sourceReader = ""
        // Expected Output: result == Token.MOD
        TokenStream ts = new TokenStream(new StringReader("%"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.MOD);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_BITNOT() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "~", sourceReader = ""
        // Expected Output: result == Token.BITNOT
        TokenStream ts = new TokenStream(new StringReader("~"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.BITNOT);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_ASSIGN_ADD() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "+=", sourceReader = ""
        // Expected Output: result == Token.ASSIGN_ADD
        TokenStream ts = new TokenStream(new StringReader("+="), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.ASSIGN_ADD);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_ADD() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "+", sourceReader = ""
        // Expected Output: result == Token.ADD
        TokenStream ts = new TokenStream(new StringReader("+"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.ADD);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_INC() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "++", sourceReader = ""
        // Expected Output: result == Token.INC
        TokenStream ts = new TokenStream(new StringReader("++"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.INC);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_ASSIGN_SUB() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "-=", sourceReader = ""
        // Expected Output: result == Token.ASSIGN_SUB
        TokenStream ts = new TokenStream(new StringReader("-="), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.ASSIGN_SUB);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_SUB() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "-", sourceReader = ""
        // Expected Output: result == Token.SUB
        TokenStream ts = new TokenStream(new StringReader("-"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.SUB);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_DEC() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "--", sourceReader = ""
        // Expected Output: result == Token.DEC
        TokenStream ts = new TokenStream(new StringReader("--"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.DEC);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }

    @Test
    public void testGetToken_operator_ERROR() {
        // GlassBox: Tests the operatorToken() path
        // Input: sourceString = "@", sourceReader = ""
        // Expected Output: result == Token.ERROR
        TokenStream ts = new TokenStream(new StringReader("@"), "", 0);
        try {
            int result = ts.getToken();
            assertTrue(result == Token.ERROR);
        } catch (Exception e) {
            fail("Unexpected exceptions thrown");
        }
    }
}
