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
		users.add( user );
	}
	
	public boolean isUser( User user ) {
		return users.contains( user );
	}
	
	public void removeUser( User user ) {
		users.remove( user );
	}
	
	public void addItem( LibraryHolding item ) {
		libraryItems.add( item );
	}
	
	public int getCheckedoutCount( ) {
		// TODO: implement this
		return 0;
	}
	
	public int getContentTypeCount( String contentType ) {
		// TODO: implement this based on holding type
		return 0;
	}
	
	public boolean checkout( LibraryHolding item, User user ) {
		if ( item.checkOut( user ) ) {
			user.addToList( item );
			return true;
		}
		else
			return false;
	}
	
	public boolean processReturn( LibraryHolding item ) {
		// TODO: implement this
		return false;
	}
}
