public class Dude {
  private String name;
  private int hp;
  private int mp;

  Dude( ) {
    name = "Son of a Gun";
    hp = 30;
    mp = 0;
  }

  Dude( String name ) {
    this( );
    this.name = name;
  }

  public void sayAhh ( ) {
    System.out.println( name + ": Ahh!" );
  }

  public void punchDude( Dude d ) {
    d.hp = d.hp - 3;
    d.sayAhh( );
  }

  public int getHP( ) {
    return hp;
  } 

  public int getMP( ) {
    return mp;
  }

  public void setMP( int mp ) {
    this.mp = mp;
  }

  public static void main( String[ ] args ) {
    Dude dude1 = new Dude( "Neo" );
    Dude dude2 = new Dude( );

    dude1.punchDude( dude2 );
    System.out.println( dude2.getHP( ) );
    System.out.println( dude1.getHP( ) );
  }
}
