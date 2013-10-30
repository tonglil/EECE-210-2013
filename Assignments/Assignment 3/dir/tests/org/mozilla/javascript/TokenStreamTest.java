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
                return (String) fields[i].get(ts);
            }
        }
        return null;
    }
    
    public int reflectIntProperty(TokenStream ts, String member) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
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

    public boolean reflectMemberExists(TokenStream ts, String member) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class tokenStreamClass = ts.getClass();

        Method methods[] = tokenStreamClass.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName() == member) {
                return true;
            }
        }

        Field fields[] = tokenStreamClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName() == member) {
                return true;
            }
        }
        return false;
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
            assertTrue(reflectIntProperty(ts, "sourceEnd") >= 0);
            assertTrue(reflectIntProperty(ts, "sourceCursor") == 0);
            assertTrue(reflectIntProperty(ts, "cursor") == 0);
        } catch (Exception e) {
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

        TokenStream ts = new TokenStream(new StringReader("\\u0041 = y; x = 1 + 9; y = x++; y = this.a;"), "", 0);
        
        try {
            reflectAll(ts);
            assertTrue(reflectProperty(ts, "sourceString") == null);
            assertTrue(reflectMemberExists(ts, "sourceReader"));
            assertTrue(reflectIntProperty(ts, "sourceEnd") >= 0);
            assertTrue(reflectIntProperty(ts, "sourceCursor") == 0);
            assertTrue(reflectIntProperty(ts, "cursor") == 0);
        } catch (Exception e) {
            fail("Reflecting exceptions raised");
        }

        //TokenStream ts = new TokenStream(new StringReader(""), null, 1);
        //fail("Not yet implemented");
    }
}
