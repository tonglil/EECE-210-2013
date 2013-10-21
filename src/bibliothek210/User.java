package bibliothek210;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	private static int nextUserId = 1;
	private final String name;
	private final int userId;
	private List<LibraryHolding> itemList;
	
	public User( String name ) {
		userId = nextUserId;
		nextUserId++;
		this.name = name;
		itemList = new ArrayList<LibraryHolding>( );
	}
	
	public String getName( ) {
		return name;
	}
	
	public int getUserId( ) {
		return userId;
	}
	
	public void addToList( LibraryHolding item ) {
		itemList.add( item );
	}
	
	public boolean hasItem( LibraryHolding item ) {
		return itemList.contains( item );
	}
}
