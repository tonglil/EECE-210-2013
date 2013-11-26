public class MP0b {

/**
* Find the first occurrence of x in an array a.
*
* @param x
* value to find
* @param a
* array with values to search from
* @return lowest i such that a[i]==x, or -1 if x not found in a.
*/
    public static int find(int x, int[] a) {
        return linearSearch(x, a);
    }

 
    private static int linearSearch(int x, int[] a) {
        // notice the use of the length field wrt the array a
        for (int i = 0; i < a.length; ++i) {
            if (x == a[i]) {
                return i;
            }
        }
        return -1;
    }

    public static void main ( String[] args ) {
        // create an array to search from
        // note the declaration of the array & the initialization
        int[] a = new int[] { 1, 5, 6, 2, 3, 9, 99, 72, 11, 12 };

        // call the search function
        System.out.println( find( 3, a ) );
    }
}
