package bibliothek210;

/**
 * 
 * @author Sathish Gopalakrishnan
 *
 */

public class Book extends LibraryHolding {
	
	private String title;
	private String author;
	private static final int checkoutDuration = 14;
	
	public Book( String title, String author ) {
		super( );
		this.title = title;
		this.author = author;
	}
	
	@Override
	public int getCheckoutDuration( ) {
		return checkoutDuration;
	}
	
	@Override
	public String getStringRepresentation( ) {
		return "<book>\n<title>"+title+"</title>\n<author>"+author+"</author>\n</book>";
	}
	
	@Override
	public String holdingType( ) {
		return "Book";
	}
	
}
