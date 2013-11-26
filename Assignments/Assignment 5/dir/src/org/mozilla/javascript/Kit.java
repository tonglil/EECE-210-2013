/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Rhino code, released
 * May 6, 1999.
 *
 * The Initial Developer of the Original Code is
 * Netscape Communications Corporation.
 * Portions created by the Initial Developer are Copyright (C) 1997-1999
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 *   Igor Bukanov, igor@fastmail.fm
 *
 * Alternatively, the contents of this file may be used under the terms of
 * the GNU General Public License Version 2 or later (the "GPL"), in which
 * case the provisions of the GPL are applicable instead of those above. If
 * you wish to allow use of your version of this file only under the terms of
 * the GPL and not to allow others to use your version of this file under the
 * MPL, indicate your decision by deleting the provisions above and replacing
 * them with the notice and other provisions required by the GPL. If you do
 * not delete the provisions above, a recipient may use your version of this
 * file under either the MPL or the GPL.
 *
 * ***** END LICENSE BLOCK ***** */

package org.mozilla.javascript;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Collection of utilities
 */

public class Kit
{
    /**
     * Reflection of Throwable.initCause(Throwable) from JDK 1.4
     * or nul if it is not available.
     */
    private static Method Throwable_initCause = null;

    /**
     * If character <tt>c</tt> is a hexadecimal digit, return
     * <tt>accumulator</tt> * 16 plus corresponding
     * number. Otherise return -1.
     */
    public static int xDigitToInt(int c, int accumulator)
    {
        check: {
            // Use 0..9 < A..Z < a..z
            if (c <= '9') {
                c -= '0';
                if (0 <= c) { break check; }
            } else if (c <= 'F') {
                if ('A' <= c) {
                    c -= ('A' - 10);
                    break check;
                }
            } else if (c <= 'f') {
                if ('a' <= c) {
                    c -= ('a' - 10);
                    break check;
                }
            }
            return -1;
        }
        return (accumulator << 4) | c;
    }

    /**
     * Throws RuntimeException to indicate failed assertion.
     * The function never returns and its return type is RuntimeException
     * only to be able to write <tt>throw Kit.codeBug()</tt> if plain
     * <tt>Kit.codeBug()</tt> triggers unreachable code error.
     */
    //@ signals_only RuntimeException;
    public static RuntimeException codeBug()
        throws RuntimeException
    {
        RuntimeException ex = new IllegalStateException("FAILED ASSERTION");
        // Print stack trace ASAP
        ex.printStackTrace(System.err);
        throw ex;
    }

    /**
     * Throws RuntimeException to indicate failed assertion.
     * The function never returns and its return type is RuntimeException
     * only to be able to write <tt>throw Kit.codeBug()</tt> if plain
     * <tt>Kit.codeBug()</tt> triggers unreachable code error.
     */
    //@ signals_only RuntimeException;
    public static RuntimeException codeBug(String msg)
        throws RuntimeException
    {
        msg = "FAILED ASSERTION: " + msg;
        RuntimeException ex = new IllegalStateException(msg);
        // Print stack trace ASAP
        ex.printStackTrace(System.err);
        throw ex;
    }
}
