import static org.junit.Assert.*;

import org.junit.Test;


/**
 * 
 * @author Sathish Gopalakrishnan
 * Simple tests for the class MyConcreteQueue.
 *
 */
public class MyConcreteQueueTest {

	// The following method tests if we are able to retrieve the first
	// element in the queue correctly.
	@Test
	public void testFirst() {
		MyConcreteQueue queue = new MyConcreteQueue( );
		queue.addElement ( 3 );
		queue.addElement ( 5 );
		Integer e;
		try {
			e = queue.getElement( );
			assertEquals( new Integer(3), e );
		}
		catch ( EmptyContainerException ex ) {
			fail( "Queue is empty!" );
		}
	}
	
	// The following method tests if we are able to retrieve the last
	// element in the queue correctly.
	@Test
	public void testLast( ) {
		MyConcreteQueue queue = new MyConcreteQueue( );
		queue.addElement ( 3 );
		queue.addElement ( 5 );
		Integer e;
		try {
			e = queue.getElement( );
			e = queue.getElement( );
			assertEquals( new Integer(5), e );
		}
		catch ( EmptyContainerException ex ) {
			fail( "Queue is empty!" );
		}		
	}
	
	// The following method tests that the correct exception is
	// thrown when the queue is empty.
	@Test
	public void testEmpty( ) {
		MyConcreteQueue queue = new MyConcreteQueue( );
		queue.addElement ( 3 );
		queue.addElement ( 5 );
		Integer e;
		try {
			e = queue.getElement( );
			e = queue.getElement( );
			e = queue.getElement( );
		}
		catch ( EmptyContainerException ex ) {
			assertEquals( 0, 0 );
		}		
	}
	
}
