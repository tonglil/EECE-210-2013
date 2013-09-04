/**
 * @author Sathish Gopalakrishnan
 *
 */
public class MP0a {
	static public void main( String[] args ) {
		int n = 1001;
		
		if ( n < 1 ) {
			System.out.println( "Incorrect input. Terminating program." );
			return;
		}
		
		while ( n > 1 ) {
			System.out.print( n + " ");
			if ( n%2 == 0 ) {
				n = n/2;
			}
			else {
				n = 3*n + 1;
			}
		}
		
		System.out.println( n );
		
	}
}
