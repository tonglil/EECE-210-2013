/**
 * 
 * @author Sathish Gopalakrishnan
 * This interface lists some elementary methods that one would
 * expect from a queue.
 * 
 */

import java.util.*;

public interface MyQueue {
	
	public Integer getElement( ) throws EmptyContainerException;
	
	public void addElement( Integer e );
	
	public boolean isEmpty( );

}
