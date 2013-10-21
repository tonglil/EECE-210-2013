package bibliothek210;

import static org.junit.Assert.*;

import org.junit.Test;

public class BookTest {

	@Test
	public void test1() {
		Book book = new Book( "The Snows of Kilimanjaro", "Ernest Hemingway" );
		assertEquals( true, 
				book.getStringRepresentation().equals("<book>\n<title>The Snows of Kilimanjaro</title>\n<author>Ernest Hemingway</author>\n</book>") );
	}
	
	@Test
	public void test2() {
		Book book = new Book( "The Snows of Kilimanjaro", "Ernest Hemingway" );
		assertEquals( 14, book.getCheckoutDuration() );		
	}

}
