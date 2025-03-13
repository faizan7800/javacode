package part01;

public class Book {
    private String title;
    private String author;
    private String isbn;
    private BookType type;
    private int edition;
    private String summary;
    private double price;

    public Book(String title, String author, String isbn, BookType type, int edition, String summary, double price) {
        // Validate title (5-40 characters)
        if (title == null || title.length() < 5 || title.length() > 40) {
            throw new IllegalArgumentException("Title must be between 5 and 40 characters");
        }
        
        // Validate author (5-40 characters)
        if (author == null || author.length() < 5 || author.length() > 40) {
            throw new IllegalArgumentException("Author must be between 5 and 40 characters");
        }
        
        // Validate ISBN (exactly 10 digits)
        if (isbn == null || isbn.length() != 10 || !isbn.matches("\\d{10}")) {
            throw new IllegalArgumentException("ISBN must be exactly 10 digits");
        }
        
        // Validate edition (>= 1)
        if (edition < 1) {
            throw new IllegalArgumentException("Edition must be greater than or equal to 1");
        }
        
        // Validate summary (20-150 characters)
        if (summary == null || summary.length() < 20 || summary.length() > 150) {
            throw new IllegalArgumentException("Summary must be between 20 and 150 characters");
        }
        
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.type = type;
        this.edition = edition;
        this.summary = summary;
        this.price = price;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public BookType getType() {
        return type;
    }

    public int getEdition() {
        return edition;
    }

    public String getSummary() {
        return summary;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", ISBN: " + isbn + 
               ", Type: " + type + ", Edition: " + edition + 
               ", Summary: " + summary + ", Price: £" + String.format("%.2f", price);
    }
}