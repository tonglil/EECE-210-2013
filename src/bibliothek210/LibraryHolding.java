package bibliothek210;

import java.util.*;
import java.util.Calendar;

/**
 * 
 * @author Sathish Gopalakrishnan
 * 
 * This class represents a generic item in the library.
 * A LibraryHolding has an identifier, a current status, a due date (if checked out),
 * and a list of users that have requested the book. A separate instance variable
 * tracks the library user that has checked out the item.
 *
 */
public abstract class LibraryHolding {
	
	private static int NextHoldingId = 1;
	private final int HoldingId;
	private HoldingStatus status;
	private User currentUser;
	private Calendar dueDate;
	private Queue<User> requestQueue;
	
	/**
	 * Constructor to add a new LibraryHolding.
	 * The constructor sets the HoldingId to the next appropriate id,
	 * sets the currentUsers and dueDates to empty sets, and
	 * initializes an empty request queue, and sets numCompies to 1.
	 */
	public LibraryHolding( ) {
		HoldingId = NextHoldingId;
		NextHoldingId++;
		currentUser = null;
		dueDate = null;
		requestQueue = new LinkedList<User>( );
		status = HoldingStatus.Available;
	}
	
	/**
	 * Obtain the status of the item
	 * @return the status of the item (available, checked out, lost, on hold)
	 */
	public HoldingStatus getStatus( ) {
		return status;
	}
	
	/**
	 * Gets the due date for this LibraryHolding.
	 * Requires that the book is checked out.
	 * @return due date.
	 */
	public Calendar getDueDate( ) {
		// return a clone to preserve immutability of dueDate from 
		// outside the class
		return ((Calendar) dueDate.clone());
	}
	
	/**
	 * Gets the current holder of this item.
	 * Requires that the item is checked out.
	 * @return the user to whom the item has been checked out.
	 */
	public User getCurrentUser( ) {
		return currentUser;
	}
	
	/**
	 * Obtain the checkout duration in days.
	 * This method should be implemented by concrete sub-classes
	 * because the checkout duration may vary based on the item type.
	 */
	public abstract int getCheckoutDuration( );
	
	/**
	 * Create a string representation of the item for
	 * display and other purposes.
	 * @return a String that represents the LibraryHolding item.
	 */
	public abstract String getStringRepresentation( );
	
	/**
	 * Return the type of holding.
	 * Abstract method that must be implemented in a concrete sub-class.
	 * @return a string that represents the type of item.
	 */
	public abstract String holdingType( );
	
	/**
	 * Check an item out to a user.
	 * @param user
	 * @return true if successful, false otherwise.
	 */
	public boolean checkOut( User user ) {
		
		if ( status == HoldingStatus.Available ) {
			status = HoldingStatus.CheckedOut;
			currentUser = user;
			dueDate = Calendar.getInstance( );
			dueDate.add(Calendar.DAY_OF_MONTH, getCheckoutDuration( ));
			return true;
		}
		else {
			if ( ( status == HoldingStatus.OnHold ) 
					&& ( requestQueue.peek( ).getUserId( ) == user.getUserId( ) ) ) {
				status = HoldingStatus.CheckedOut;
				currentUser = user;
				dueDate = Calendar.getInstance( );
				dueDate.add(Calendar.DAY_OF_MONTH, getCheckoutDuration( ));
				requestQueue.remove( );
				return true;
			}
			else {
				return false;
			}
		}
		
	}
	
	/**
	 * Method to process an item's return.
	 * @return true if the item was correctly returned, false otherwise.
	 * This method only changes the status of the item and not of the user
	 * that had checked out the item.
	 */
	public boolean processReturn( ) {
		
		if ( status != HoldingStatus.CheckedOut )
			return false;
		
		currentUser = null;
		dueDate = null;
		
		if ( !requestQueue.isEmpty() ) {
			status = HoldingStatus.OnHold;
		}
		else {
			status = HoldingStatus.Available;
		}
		return true;
	}
	
}
