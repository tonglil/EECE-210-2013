package org.mozilla.javascript;

import static org.junit.Assert.*;

import java.io.StringReader;

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

    @Test
    public void testTokenStreamNullReader() {
        // Tests the constructor function
        // Input: null sourceReader, non-null sourceString
        // Expected output: TokenStream with:
        // (this.sourceString != null ==> this.sourceReader == null)
        // (this.sourceEnd >= 0)
        // (this.sourceCursor == 0 && this.cursor == 0);

        TokenStream ts = new TokenStream(null, "abc", 0);

        try {
            //reflectAll(ts);
            assertTrue(reflectProperty(ts, "sourceString") == "abc");
            assertNull(reflectProperty(ts, "sourceReader"));
            assertTrue(reflectPropertyInt(ts, "sourceEnd") >= 0);
            assertTrue(reflectPropertyInt(ts, "sourceCursor") == 0);
            assertTrue(reflectPropertyInt(ts, "cursor") == 0);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Reflecting exceptions raised");
        }
    }

    @Test
    public void testTokenStreamNullStringJS() {
        // Tests the constructor function with .js
        // Input: sourceReader with javascript, null sourceString
        // Expected output:
        // (this.sourceString == null ==> this.sourceReader != null)
        // (this.sourceEnd >= 0)
        // (this.sourceCursor == 0 && this.cursor == 0);

        TokenStream ts = new TokenStream(new StringReader("\\u0041 = y; x = 1 + 9; y = x++; y = this.a;"), "", 0);

        try {
            assertNull(reflectProperty(ts, "sourceString"));
            assertNotNull(reflectPropertyObject(ts, "sourceReader"));
            assertTrue(reflectPropertyInt(ts, "sourceEnd") >= 0);
            assertTrue(reflectPropertyInt(ts, "sourceCursor") == 0);
            assertTrue(reflectPropertyInt(ts, "cursor") == 0);
        } catch (Exception e) {
            fail("Reflecting exceptions raised");
        }
    }

    @Test
    public void testTokenStreamNullStringEmpty() {
        // Tests the constructor function without .js
        // Input: empty sourceReader, null sourceString
        // Expected output:
        // (this.sourceString == null ==> this.sourceReader != null)
        // (this.sourceEnd >= 0)
        // (this.sourceCursor == 0 && this.cursor == 0);

        TokenStream ts = new TokenStream(new StringReader(""), null, 0);

        try {
            assertNull(reflectProperty(ts, "sourceString"));
            assertNotNull(reflectPropertyObject(ts, "sourceReader"));
            assertTrue(reflectPropertyInt(ts, "sourceEnd") >= 0);
            assertTrue(reflectPropertyInt(ts, "sourceCursor") == 0);
            assertTrue(reflectPropertyInt(ts, "cursor") == 0);
        } catch (Exception e) {
            fail("Reflecting exceptions raised");
        }
    }

    @Test
    public void testStringToKeywordValid() {
        // Tests if the stringToKeyword function correctly maps strings to their keyword values
        // Input: name = else, false, function, if, return, true
        // Expected output: 113, 44, 109, 112, 4, 45

        TokenStream ts = new TokenStream(null, "", 0);
        String[] keywords = {"else", "false", "function", "if", "return", "true"};
        int[] values = {113, 44, 109, 112, 4, 45};

        for (int i = 0; i < keywords.length; i++) {
            try {
                assertTrue(ts.stringToKeyword(keywords[i]) == values[i]);
            } catch (Exception e) {
                fail("Reflecting exceptions raised");
            }
        }
    }

    @Test
    public void testStringToKeywordInvalid() {
        // Tests if the stringToKeyword function correctly maps non-keyword values to 0
        // Input: name = gibberish, (none)
        // Expected output: 0, 0

        TokenStream ts = new TokenStream(null, "", 0);
        String[] strings = {"gibberish", ""};

        for (int i = 0; i < strings.length; i++) {
            try {
                assertTrue(ts.stringToKeyword(strings[i]) == 0);
            } catch (Exception e) {
                fail("Reflecting exceptions raised");
            }
        }
    }

    @Test
    public void testGetTokenJS() {
        // Tests if getToken gives a correct result when given JS
        // Input: non-null stringBuffer, null sourceString, non-null sourceBuffer and sourceReader
        // Expected output: -1 <= result <= 162
        // Blackbox

        TokenStream ts = new TokenStream(new StringReader("\\u0041 = y; x = 1 + 9; y = x++; y = this.a;"), null, 0);

        try {
            assertNotNull(reflectPropertyObject(ts, "stringBuffer"));
            assertNull(reflectProperty(ts, "sourceString"));
            assertNotNull(reflectPropertyObject(ts, "sourceBuffer"));
            assertNotNull(reflectPropertyObject(ts, "sourceReader"));
            assertTrue(ts.getToken() >= -1);
            assertTrue(ts.getToken() <= 162);
        } catch (Exception e) {
            fail("Reflecting exceptions raised");
        }
    }

    @Test
    public void testGetTokenEmpty() {
        // Tests if getToken gives a correct result without JS
        // Input: non-null stringBuffer, null sourceString, non-null sourceBuffer and sourceReader
        // Expected output: -1 <= result <= 162
        // Blackbox

        TokenStream ts = new TokenStream(new StringReader(""), null, 0);

        try {
            assertNotNull(reflectPropertyObject(ts, "stringBuffer"));
            assertNull(reflectProperty(ts, "sourceString"));
            assertNotNull(reflectPropertyObject(ts, "sourceBuffer"));
            assertNotNull(reflectPropertyObject(ts, "sourceReader"));
            assertTrue(ts.getToken() >= -1);
            assertTrue(ts.getToken() <= 162);
        } catch (Exception e) {
            fail("Reflecting exceptions raised");
        }
    }

    @Test
    public void testGetTokenBadResult() {
        // Tests if getToken gives a bad result
// Input: null stringBuffer, null sourceString, non-null sourceBuffer and sourceReader
        // Expected output: result < -1, result > 162
        // Blackbox

        try {
        } catch (Exception e) {
            fail("Reflecting exceptions raised");
        }
    }

    @Test
    public void testGetTokenException() {
        // Tests if getToken throws an exception
// Input: non-null stringBuffer, null sourceString, non-null sourceBuffer and sourceReader
        // Expected output: RuntimeException, IOException;
        // Blackbox

        try {
        } catch (Exception e) {
            fail("Reflecting exceptions raised");
        }
    }

//todo separate....
    @Test
    public void testStringToNumberValid() {
        // Tests if stringToNumber get the right inputs;
        // Input: non-null s, start = 0, 1, 3, radix = 10, 8, 16
        // Expected output: a number of type double

        TokenStream ts = new TokenStream(null, "", 0);
        String[] string = {"0", "1", "13", "a"};
        int[] radix = {10, 8, 16};
        int[] start = {0, 1, 2};

        for (int i = 0; i < radix.length; i++) {
            try {
                assertFalse(Double.isNaN(TokenStream.stringToNumber("0", 0, radix[i])));
            } catch (Exception e) {
                fail("Giving NaN when is a number");
            }
        }

        for (int i = 0; i < radix.length; i++) {
            try {
                assertFalse(Double.isNaN(TokenStream.stringToNumber("15", 1, radix[i])));
            } catch (Exception e) {
                fail("Giving NaN when is a number");
            }
        }

        for (int i = 0; i < string.length; i++) {
            try {
                assertFalse(Double.isNaN(TokenStream.stringToNumber(string[i], 0, 16)));
            } catch (Exception e) {
                fail("Giving NaN when is a number");
            }
        }

        for (int i = 0; i < start.length; i++) {
            try {
                assertFalse(Double.isNaN(TokenStream.stringToNumber("164", start[i], 10)));
            } catch (Exception e) {
                fail("Giving NaN when is a number");
            }
        }
    }

    @Test
    public void testStringToNumberInvalid() {
        // Tests if stringToNumber handles bad inputs;
        // Input: null s, start < 0, radix != 10, 8, 16
    // Expected output: ......todo
        // Glassbox

        TokenStream ts = new TokenStream(null, "", 0);

        try {
            // null s
            TokenStream.stringToNumber(null, 1, 10);
        } catch (NullPointerException e) {
            // We want this
        }

        try {
            // start < 0
            TokenStream.stringToNumber("123", -1, 16);
        } catch (StringIndexOutOfBoundsException e) {
            // We want this
        }

        try {
            // radix != 10, 8, 16 ==> NaN
            assertTrue(Double.isNaN(TokenStream.stringToNumber("a", 1, 8)));
            assertTrue(Double.isNaN(TokenStream.stringToNumber("1", 1, 10)));
        } catch (Exception e) {
            fail("Not getting NaN");
        }
    }

    //@ ensures c <= 127 ==> \result == (c == 0x20 || c == 0x9 || c == 0xC || c == 0xB);
    //public static boolean isJSSpace(int c)
    @Test
    public void testIsJSSpace() {
        // Tests if isJSSpace handles whitespace characters (todo and non-breaking space?)
        // Input: c <= 127
        // Expected output: 0x20, 0x9, 0xC, 0xB
        // todo: another version for c > 127 ==> 0xA0

        try {
        } catch (Exception e) {
            fail("Reflecting exceptions raised");
        }
    }

    @Test
    public void testGetString() {
        // Tests
        // Input:
        // Expected output:

        try {
        } catch (Exception e) {
            fail("Reflecting exceptions raised");
        }
    }

    @Test
    public void testGetNumber() {
        // Tests
        // Input:
        // Expected output:

        try {
        } catch (Exception e) {
            fail("Reflecting exceptions raised");
        }
    }

    @Test
    public void testIsNumberOctal() {
        // Tests
        // Input:
        // Expected output:

        try {
        } catch (Exception e) {
            fail("Reflecting exceptions raised");
        }
    }

    @Test
    public void testEof() {
        // Tests
        // Input:
        // Expected output:

        try {
        } catch (Exception e) {
            fail("Reflecting exceptions raised");
        }
    }
}
