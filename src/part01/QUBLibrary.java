package part01;

import java.util.Scanner;

public class QUBLibrary {
    private Library library;
    private Menu mainMenu;
    private Scanner scanner;

    public QUBLibrary() {
        library = new Library();
        scanner = new Scanner(System.in);
        
        // Initialize with 5 sample books
        initializeLibrary();
        
        // Create main menu
        String[] menuOptions = {
            "List All Books", 
            "List Books by Status", 
            "Add a Book", 
            "Remove a Book", 
            "Borrow a Book", 
            "Return a Book", 
            "Display Ranked List", 
            "Exit"
        };
        mainMenu = new Menu("QUB Library System", menuOptions);
    }

    private void initializeLibrary() {
        // Add 5 sample books
        library.add("Introduction to Java Programming", "John Smith", "1234567890", 
                   BookType.TEXTBOOK, 1, "A comprehensive guide to Java programming for beginners.", 
                   29.99, "java_intro.jpg");
                   
        library.add("The Great Gatsby", "F. Scott Fitzgerald", "0987654321", 
                   BookType.FICTION, 2, "A novel about the mysteriously wealthy Jay Gatsby and his love for Daisy Buchanan.", 
                   12.99, "gatsby.jpg");
                   
        library.add("A Brief History of Time", "Stephen Hawking", "5678901234", 
                   BookType.NON_FICTION, 3, "A book on cosmology that explores the origin and structure of the universe.", 
                   15.99, "brief_history.jpg");
                   
        library.add("Oxford English Dictionary", "Oxford Press", "6789012345", 
                   BookType.REFERENCE, 5, "The definitive record of the English language featuring 600,000 words.", 
                   45.99, "dictionary.jpg");
                   
        library.add("To Kill a Mockingbird", "Harper Lee", "9876543210", 
                   BookType.FICTION, 1, "A novel about racial inequality and moral growth in the American South.", 
                   14.99, "mockingbird.jpg");
    }

    public void run() {
        boolean running = true;
        
        while (running) {
            int choice = mainMenu.getChoice();
            
            switch (choice) {
                case 1:
                    listAllBooks();
                    break;
                case 2:
                    listBooksByStatus();
                    break;
                case 3:
                    addBook();
                    break;
                case 4:
                    removeBook();
                    break;
                case 5:
                    borrowBook();
                    break;
                case 6:
                    returnBook();
                    break;
                case 7:
                    displayRankedList();
                    break;
                case 8:
                    running = false;
                    System.out.println("Thank you for using QUB Library System. Goodbye!");
                    break;
            }
        }
    }

    private void listAllBooks() {
        System.out.println("\n=== All Books ===");
        LibraryBook[] books = library.list();
        
        if (books.length == 0) {
            System.out.println("No books in the library.");
        } else {
            for (LibraryBook book : books) {
                System.out.println(book);
            }
        }
    }

    private void listBooksByStatus() {
        System.out.println("\n=== List Books by Status ===");
        System.out.println("1. Available");
        System.out.println("2. On Loan");
        System.out.println("3. Withdrawn");
        
        System.out.print("Enter choice (1-3): ");
        int statusChoice = Integer.parseInt(scanner.nextLine());
        
        BookStatus status;
        switch (statusChoice) {
            case 1:
                status = BookStatus.AVAILABLE;
                break;
            case 2:
                status = BookStatus.ON_LOAN;
                break;
            case 3:
                status = BookStatus.WITHDRAWN;
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }
        
        LibraryBook[] books = library.list(status);
        
        if (books.length == 0) {
            System.out.println("No books with status: " + status);
        } else {
            System.out.println("\n=== Books with status: " + status + " ===");
            for (LibraryBook book : books) {
                System.out.println(book);
            }
        }
    }

    private void addBook() {
        System.out.println("\n=== Add a New Book ===");
        
        try {
            String title = mainMenu.getInput("Enter title (5-40 characters)");
            String author = mainMenu.getInput("Enter author (5-40 characters)");
            String isbn = mainMenu.getInput("Enter ISBN (exactly 10 digits)");
            
            System.out.println("Book Types:");
            System.out.println("1. FICTION");
            System.out.println("2. NON_FICTION");
            System.out.println("3. REFERENCE");
            System.out.println("4. TEXTBOOK");
            System.out.print("Enter book type (1-4): ");
            int typeChoice = Integer.parseInt(scanner.nextLine());
            
            BookType type;
            switch (typeChoice) {
                case 1: type = BookType.FICTION; break;
                case 2: type = BookType.NON_FICTION; break;
                case 3: type = BookType.REFERENCE; break;
                case 4: type = BookType.TEXTBOOK; break;
                default:
                    System.out.println("Invalid book type. Using FICTION as default.");
                    type = BookType.FICTION;
            }
            
            System.out.print("Enter edition (must be >= 1): ");
            int edition = Integer.parseInt(scanner.nextLine());
            
            String summary = mainMenu.getInput("Enter summary (20-150 characters)");
            
            System.out.print("Enter price (must be > £0.00): ");
            double price = Double.parseDouble(scanner.nextLine());
            
            String coverImage = mainMenu.getInput("Enter cover image filename");
            
            boolean success = library.add(title, author, isbn, type, edition, summary, price, coverImage);
            
            if (success) {
                System.out.println("Book added successfully!");
            } else {
                System.out.println("Failed to add book. Please check your input.");
            }
            
        } catch (Exception e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    private void removeBook() {
        System.out.println("\n=== Remove a Book ===");
        
        LibraryBook[] books = library.list();
        if (books.length == 0) {
            System.out.println("No books in the library to remove.");
            return;
        }
        
        System.out.println("Available books:");
        for (LibraryBook book : books) {
            if (book.getStatus() != BookStatus.ON_LOAN) {
                System.out.println("ID: " + book.getId() + " - " + book.getTitle() + " by " + book.getAuthor());
            }
        }
        
        System.out.print("Enter the ID of the book to remove: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            boolean success = library.remove(id);
            
            if (success) {
                System.out.println("Book marked as withdrawn successfully.");
            } else {
                System.out.println("Failed to remove book. It may be on loan or not exist.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }

    private void borrowBook() {
        System.out.println("\n=== Borrow a Book ===");
        
        LibraryBook[] availableBooks = library.list(BookStatus.AVAILABLE);
        if (availableBooks.length == 0) {
            System.out.println("No books available to borrow.");
            return;
        }
        
        System.out.println("Available books:");
        for (LibraryBook book : availableBooks) {
            System.out.println("ID: " + book.getId() + " - " + book.getTitle() + " by " + book.getAuthor());
        }
        
        System.out.print("Enter the ID of the book to borrow: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            boolean success = library.borrowBook(id);
            
            if (success) {
                System.out.println("Book borrowed successfully.");
            } else {
                System.out.println("Failed to borrow book. It may not be available or not exist.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }

    private void returnBook() {
        System.out.println("\n=== Return a Book ===");
        
        LibraryBook[] onLoanBooks = library.list(BookStatus.ON_LOAN);
        if (onLoanBooks.length == 0) {
            System.out.println("No books currently on loan.");
            return;
        }
        
        System.out.println("Books on loan:");
        for (LibraryBook book : onLoanBooks) {
            System.out.println("ID: " + book.getId() + " - " + book.getTitle() + " by " + book.getAuthor());
        }
        
        System.out.print("Enter the ID of the book to return: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            boolean success = library.returnBook(id);
            
            if (success) {
                System.out.println("Book returned successfully.");
            } else {
                System.out.println("Failed to return book. It may not be on loan or not exist.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }

    private void displayRankedList() {
        System.out.println("\n=== Most Popular Books ===");
        
        LibraryBook[] rankedBooks = library.mostPopular();
        
        if (rankedBooks.length == 0) {
            System.out.println("No books in the library.");
        } else {
            System.out.println("Books ranked by popularity (times borrowed):");
            for (int i = 0; i < rankedBooks.length; i++) {
                LibraryBook book = rankedBooks[i];
                System.out.println((i + 1) + ". " + book.getTitle() + " by " + book.getAuthor() + 
                                  " - Times borrowed: " + book.getLoanCount());
            }
        }
    }

    public static void main(String[] args) {
        QUBLibrary app = new QUBLibrary();
        app.run();
    }
}