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
		assertEquals( 2, lib.getUserCount() );
	}
	
	@Test
	public void testCheckedoutCount( ) {
		Library lib = new Library( );
		User u1 = new User("U1");
		User u2 = new User("U2");
		lib.addUser( u1 );
		lib.addUser( u2 );
		
		Book book = new Book ("Test", "Test Author");
		
		lib.checkout(book, u1);
		
		assertEquals( lib.getCheckedoutCount(), 1 );
	}
	
	@Test
	public void testContentTypeCount1( ) {
		
		Book b1 = new Book ( "Book 1", "Author 1" );
		Book b2 = new Book ( "Book 2", "Author 2" );
		Library lib = new Library( );
		lib.addItem( b1 );
		lib.addItem( b2 );
		
		int n = lib.getContentTypeCount( "Book" );
		
		assertEquals( 2, n );		
	}
	
	@Test
	public void testContentTypeCount2( ) {
		
		Library lib = new Library( );
		// TODO: add more here
		
		int n = lib.getContentTypeCount( "DVD" );
		// for this test, you must create a new class, DVD, that extends
		// LibraryHolding, and add objects of that class to the library.
		// See "Book.java" for a starting point
		
		assertEquals( 3, n );		
	}
	
	
	@Test
	public void testAddDuplicateUser( ) {
		User u = new User( "Test User" );
		Library lib = new Library( );
		lib.addUser( u );
		lib.addUser( u );
		lib.removeUser( u );
		
		assertEquals( false, lib.isUser( u ));
	}

	@Test
	public void testCheckoutAndReturn( ) {
		Library lib = new Library( );
		User u = new User( "Test User" );
		Book b = new Book ( "Grapes of Wrath", "John Steinbeck" );
		lib.addItem( b );
		lib.addUser( u );
		lib.checkout( b, u );
		assertEquals( true, lib.processReturn(b) );
	}
	
	@Test
	public void testCheckoutAndReturnWithID( ) {
		Library lib = new Library( );
		int b = 10, u = 10; // arbitrary ids. should be re-written
		lib.checkout(10, 10);
		assertEquals( true, lib.processReturn(b) );
	}
	
}
