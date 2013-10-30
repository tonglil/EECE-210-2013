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
            assertTrue(reflectProperty(ts, "sourceReader") == null);
            assertTrue(reflectPropertyInt(ts, "sourceEnd") >= 0);
            assertTrue(reflectPropertyInt(ts, "sourceCursor") == 0);
            assertTrue(reflectPropertyInt(ts, "cursor") == 0);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Reflecting exceptions raised");
        }
    }

    @Test
    public void testTokenStreamNullString() {
        // Tests the constructor function
        // Input: non-null sourceReader, null sourceString
        // Expected output:
        // (this.sourceString == null ==> this.sourceReader != null)
        // (this.sourceEnd >= 0)
        // (this.sourceCursor == 0 && this.cursor == 0);

        TokenStream ts = new TokenStream(new StringReader(""), null, 0);

        try {
            assertTrue(reflectProperty(ts, "sourceString") == null);
            assertTrue(reflectPropertyObject(ts, "sourceReader") != null);
            assertTrue(reflectPropertyInt(ts, "sourceEnd") >= 0);
            assertTrue(reflectPropertyInt(ts, "sourceCursor") == 0);
            assertTrue(reflectPropertyInt(ts, "cursor") == 0);
        } catch (Exception e) {
            fail("Reflecting exceptions raised");
        }

        ts = new TokenStream(new StringReader("\\u0041 = y; x = 1 + 9; y = x++; y = this.a;"), "", 0);

        try {
            assertTrue(reflectProperty(ts, "sourceString") == null);
            assertTrue(reflectPropertyObject(ts, "sourceReader") != null);
            assertTrue(reflectPropertyInt(ts, "sourceEnd") >= 0);
            assertTrue(reflectPropertyInt(ts, "sourceCursor") == 0);
            assertTrue(reflectPropertyInt(ts, "cursor") == 0);
        } catch (Exception e) {
            fail("Reflecting exceptions raised");
        }
    }

    @Test
    public void testStringToKeyword() {
        // Tests if the stringToKeyword function correctly maps strings to their keyword values
        // Input: name = else, false, function, if, return, true, gibberish, (none)
        // Expected output: 113, 44, 109, 112, 4, 45, 0, 0

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
    public void testGetToken() {
        // Tests if getToken gives a correct result
// Input: non-null stringBuffer, null sourceString, non-null sourceBuffer and sourceReader
        // Expected output: -1 <= result <= 162
        // Blackbox

        TokenStream ts = new TokenStream(new StringReader(""), null, 0);

        try {
            assertTrue(reflectPropertyObject(ts, "stringBuffer") != null);
            assertTrue(reflectProperty(ts, "sourceString") == null);
            assertTrue(reflectPropertyObject(ts, "sourceBuffer") != null);
            assertTrue(reflectPropertyObject(ts, "sourceReader") != null);
            assertTrue(ts.getToken() <= 162);
        } catch (Exception e) {
            fail("Reflecting exceptions raised");
        }
    }

    @Test
    public void testGetTokenBadResult() {
        // Tests if getToken gives a bad result
// Input: non-null stringBuffer, null sourceString, non-null sourceBuffer and sourceReader
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

    @Test
    public void testStringToNumber() {
        // Tests
        // Input:
        // Expected output:

        try {
        } catch (Exception e) {
            fail("Reflecting exceptions raised");
        }
    }

    @Test
    public void testIsJSSpace() {
        // Tests
        // Input:
        // Expected output:

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
