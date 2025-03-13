package part01;

public class LibraryBook extends Book implements Lendable {
    private int id;
    private static int nextId = 1;
    private BookStatus status;
    private String image;
    private int loanCount;

    public LibraryBook(String title, String author, String isbn, BookType type, int edition, 
                      String summary, double price, String coverImage) {
        super(title, author, isbn, type, edition, summary, price);
        
        // Validate price
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than £0.00");
        }
        
        this.id = nextId++;
        this.status = BookStatus.AVAILABLE;
        this.image = coverImage;
        this.loanCount = 0;
    }

    public int getId() {
        return id;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public int getLoanCount() {
        return loanCount;
    }

    @Override
    public boolean checkout() {
        if (status == BookStatus.AVAILABLE) {
            status = BookStatus.ON_LOAN;
            loanCount++;
            return true;
        }
        return false;
    }

    @Override
    public boolean checkIn() {
        if (status == BookStatus.ON_LOAN) {
            status = BookStatus.AVAILABLE;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", " + super.toString() + 
               ", Status: " + status + ", Image: " + image + 
               ", Times Borrowed: " + loanCount;
    }
}