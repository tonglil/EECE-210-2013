public class Book {
    private String title;
    private String author;
    private int ISBN;

    // default constructor for the object
    public Book( ) {
        ISBN = 0;
    }

    public Book( String title ) {
        // set the title of this object (the one that is being created) to the argument being passed
        this.title = title;
    }

    public Book( int ISBN ) {
        this.ISBN = ISBN;
    }

    public Book( String title, String author ) {
        this.title = title;
        this.author = author;
    }

    public String getTitle( ) {
        return title;
    }

    public String getAuthor( ) {
        return author;
    }

    public static void main(String args[]) {
        Book b1 = new Book();
        System.out.println(b1.getTitle());
        System.out.println(b1.getAuthor());

        b1 = new Book("Title");
        System.out.println(b1.getTitle());
        System.out.println(b1.getAuthor());

        b1 = new Book("Title", "Author");
        System.out.println(b1.getTitle());
        System.out.println(b1.getAuthor());
    }
}
