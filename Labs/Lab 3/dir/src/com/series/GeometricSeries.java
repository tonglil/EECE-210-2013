/*
* Looks for the sum of the first n terms of a geometric sequence with first term a and common ratio r
*/
package com.series;

public class GeometricSeries {
    public static boolean hasResult = false;
    public static int result;

    /*@ assignable result, hasResult;
      @ ensures (args.length > 3 && args[3] != 1 && args[2] >= 0) ==> (hasResult == true && result == args[1] * (1 - args[3]^args[2])/(1 - args[3])
      @ (args.length <= 3 || args[3] == 1 || args[2] < 0) ==> (hasResult == false && result == -1);
      @ signals NumberFormatException if args[1], args[2], and/or args[3] are not parsable integers
      @*/
    public static void main(String[] args) throws NumberFormatException {
        if (args.length > 3) {
            //Note: args[0] is the program name

            //Get the first term
            int a = Integer.parseInt(args[1]);

            //Get the number of terms
            int n = Integer.parseInt(args[2]);

            //Get the common ratio
            int r = Integer.parseInt(args[3]);

            if (r == 1) { //common ratio can't be 1
                hasResult = false;
                result = -1;
                return;
            }

            try {
                result = (a * (1 - powPositive(r,n))) / (1 - r);
                hasResult = true;
            } catch (Exception e) {
                     hasResult = false;
                     result = -1;
                     return;
            }
        } else {
            hasResult = false;
            result = -1;
            return;
        }
    }

    /*@ assignable \nothing
      @ ensures !((base == 0 && exp == 0) || exp < 0) ==> \result = base^exp;
      @ signals exception if (base == 0 && exp == 0) || exp < 0
      @*/
    public static int powPositive(int base, int exp) throws Exception {
        if ((base == 0 && exp == 0) || exp < 0) {
            throw new Exception();
        }

        int result = 1;
        for (int i = 1; i <= exp; i++) {
            result = result*base;
        }
        return result;
    }
}





















