package bibliothek210;

import java.util.List;
import java.util.ArrayList;

public class Library {
	
	private List<LibraryHolding> libraryItems;
	private List<User> users;
	
	public Library( ) {
		libraryItems = new ArrayList<LibraryHolding>();
		users = new ArrayList<User>();
	}
	
	public int getUserCount() {
		return users.size();
	}
	
	public int getItemCount() {
		return libraryItems.size();
	}

	public void addUser( User user ) {
		users.add( user );
	}
	
	public void removeUser( User user ) {
		users.remove( user );
	}
	
	public void addItem( LibraryHolding item ) {
		libraryItems.add( item );
	}
	
	public int getCheckedoutCount( ) {
		// TODO
		return 0;
	}
	
	public void checkout( LibraryHolding item, User user ) {
		if ( item.checkOut( user ) )
			user.addToList( item );
	}
	
}
