import java.util.ArrayList;

/**
 * 
 * @author Sathish Gopalakrishnan
 * 
 * MyConcreteQueue is a class that implements the MyQueue interface.
 * It provides an elementary queue.
 *
 */
public class MyConcreteQueue implements MyQueue {
	
	// This internal field is an ArrayList that maintains
	// the elements in the queue.
	ArrayList<Integer> internal_queue;
	
	// Default constructor that initializes internal_queue
	// with an empty list.
	public MyConcreteQueue( ) {
		internal_queue = new ArrayList<Integer>( );
	}
	
	/**
	 * The isEmpty( ) method tests if the queue is empty or not.
	 * @param none.
	 * @return true if the list is empty and false otherwise. 
	 */
	public boolean isEmpty( ) {
		if ( internal_queue.size( ) == 0 ) {
			return true;
		}
		return false;
	}
	
	/**
	 * The getElement( ) method returns the element at the head
	 * of the queue. If the queue is empty then the method throws an 
	 * exception
	 * @param none
	 * @return element at the head of the queue
	 * @throws EmptyContainerException
	 */
	public Integer getElement( ) throws EmptyContainerException {
		if ( this.isEmpty( ) )
			throw new EmptyContainerException( );
		
		Integer e = internal_queue.get( 0 );
		internal_queue.remove( 0 );
		return e;
	}
	
	/**
	 * The addElement( ) method adds a new element to the queue.
	 * @param e - the new element to be added.
	 * @return nothing
	 */
	public void addElement( Integer e ) {
		internal_queue.add( e );
	}
}
