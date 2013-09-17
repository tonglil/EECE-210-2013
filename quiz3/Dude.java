/*
	The purpose of this example program is to illustrate several aspects of Java.
	Some of these aspects were not emphasized in other notes (e.g., nested classes).
	You may want to read more about some of these aspects on your own.
	Note the use of static fields, as well as public and private fields.
	You can experiment with the code snippets to get a better understanding of Java.
*/

public class Dude {
	private String name;
	private int hp;
	private static int numDudes = 0;
	
	Dude( String name ) {
		this.name = name;
		numDudes++;
		hp = 50;
	}
	
	public void whoAmI( ) {
		System.out.println( "My name is " + name );
	}
	
	public static int getNumDudes( ) {
		return numDudes;
	}
	
	private class Armour {
		private int protection = 10;
	}
	
	public static void main( String[ ] args ) {
		Dude d1 = new Dude( "Frodo" );
		Dude d2 = new Dude( "Bilbo" );
		Dude d3 = d2;
		
		// Armour a = new Armour( );			// line a
		Armour b = d3.new Armour( );			// line b
		System.out.println( Dude.getNumDudes( ) );	// line c
		System.out.println( d3.getNumDudes( ) );	// line d
		System.out.println( b.protection );		// line e
	}
	
}
