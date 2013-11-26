public class Book {
    private String title;
    private String author;
    private int ISBN;

    // default constructor for the object
    public Book() {
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

    public int getISBN() {
        return ISBN;
    }

    public static void main(String args[]) {
        Book book1 = new Book("The Snows of Kilimanjaro");
        Book book2 = new Book("The Snows of Kilimanjaro");

        if (book1 == book2) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

        if (book1.getTitle() == book1.getTitle()) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

        if (book1.getTitle() == book2.getTitle()) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

        if (book1.getTitle().equals(book2.getTitle())) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

        System.out.println();

        Book b1 = new Book();
        System.out.println(b1.getTitle());
        System.out.println(b1.getAuthor());
        System.out.println(b1.getISBN());

        b1 = new Book("Program Development in Java");
        System.out.println(b1.getTitle());
        System.out.println(b1.getAuthor());
        System.out.println(b1.getISBN());

        b1 = new Book("Title", "Author");
        System.out.println(b1.getTitle());
        System.out.println(b1.getAuthor());
        System.out.println(b1.getISBN());

        System.out.println();

        b1.title = "As I Lay Dying";
        System.out.println(b1.getTitle());

        //Book b2 = (Book) b1.clone();
    }
}
