package bibliothek210;

import static org.junit.Assert.*;

import org.junit.Test;

public class LibraryTest {

	@Test
	public void testUserCount( ) {
		Library lib = new Library( );
		User u1 = new User("U1");
		User u2 = new User("U2");
		lib.addUser( u1 );
		lib.addUser( u2 );	
		assertEquals( lib.getUserCount(), 2 );
	}
	
	@Test
	public void testCheckedoutCount {
		Library lib = new Library( );
		User u1 = new User("U1");
		User u2 = new User("U2");
		lib.addUser( u1 );
		lib.addUser( u2 );
		
		Book book = new Book ("Test", "Test Author");
		
		lib.checkout(book, u1);
		
		assertEquals( lib.getCheckedoutCount(), 1 );
	}

}
