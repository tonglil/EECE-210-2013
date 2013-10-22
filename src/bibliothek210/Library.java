package bibliothek210;

import java.util.List;
import java.util.ArrayList;

/**
 * 
 * @author Sathish Gopalakrishnan
 * 
 * The Library class represents a library,
 * with a collection of items and users.
 * 
 * The class includes methods for processing checkouts and returns
 * and other basic operations.
 *
 */

public class Library {
	
	// the list of items in the library
	private List<LibraryHolding> libraryItems;
	
	// the list of users
	private List<User> users;
	
	/**
	 * Default constructor that creates empty item and user lists.
	 */
	public Library( ) {
		libraryItems = new ArrayList<LibraryHolding>();
		users = new ArrayList<User>();
	}
	
	/**
	 * Obtain the number of users.
	 * 
	 * @return the number of users in the library system.
	 */
	public int getUserCount() {
		return users.size();
	}
	
	/**
	 * Obtain the number of items in the library.
	 * 
	 * @return the number of items in the library system.
	 */
	public int getItemCount() {
		return libraryItems.size();
	}

	/**
	 * Add a new user to the list of users.
	 * 
	 * @param user to add to the library user list
	 */
	public void addUser( User user ) {
		// TODO: More needed?
		users.add( user );
	}
	
	/**
	 * Checks if a user is part of this library system
	 * @param user to be tested for
	 * @return true of the user is part of the library's user list,
	 * otherwise return false
	 */
	public boolean isUser( User user ) {
		// TODO: More needed?
		return users.contains( user );
	}
	
	/**
	 * Remove a user from the library's user list
	 * 
	 * @param user to be removed
	 */
	public void removeUser( User user ) {
		// TODO: More needed?
		users.remove( user );
	}
	
	/**
	 * Add an item to the library's collection
	 * 
	 * @param item to be added
	 */
	public void addItem( LibraryHolding item ) {
		// TODO: More needed?
		libraryItems.add( item );
	}
	
	/**
	 * Return the number of items that have been checked out
	 * @return number of checked out items
	 */
	public int getCheckedoutCount( ) {
		// TODO: implement this
		return 0;
	}
	
	/**
	 * 
	 * @param contentType is a string that represents the item type
	 * @return the number of library items that match the content type
	 */
	public int getContentTypeCount( String contentType ) {
		// TODO: implement this based on holding type
		return 0;
	}
	
	/**
	 * Process an item checkout
	 * 
	 * @param item being checked out
	 * @param user who is checking the item out
	 * @return true if the checkout succeeded, false otherwise.
	 * If the checkout is successful then the state of the item 
	 * changes to CheckedOut and the user is added to the item
	 * as its current holder. Similarly the item is added to the 
	 * list of items in the user's possession.
	 */
	public boolean checkout( LibraryHolding item, User user ) {
		// should we do more here?
		// Is the item part of the library's collection at all?
		if ( item.checkOut( user ) ) {
			user.addToList( item );
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Process an item checkout given the item's ID and the user's ID
	 * TODO: write a more detailed spec
	 * @param holdingID
	 * @param userID
	 * @return
	 */
	public boolean checkout( int holdingID, int userID ) {
		// TODO: implement this method
		// This is to illustrate overloading.
		// Is it better to use actual object references or use IDs for
		// such transactions?
		return false;
	}
	
	/**
	 * TODO: write a good spec for this method
	 * @param item
	 * @return
	 */
	public boolean processReturn( LibraryHolding item ) {
		// TODO: implement this
		return false;
	}
	
	/**
	 * Process an item's return given the item's ID
	 * TODO: write a more detailed spec
	 * @param holdingID
	 * @param userID
	 * @return
	 */
	public boolean processReturn( int holdingID ) {
		// TODO: implement this method
		return false;
	}

}
