package part02;

import part01.BookStatus;
import part01.BookType;
import part01.Library;
import part01.LibraryBook;

import console.Console;
import java.awt.Color;
import java.awt.Font;
import java.util.Scanner;

public class QUBLibraryUpdated {
    private Console con;
    private Library library;
    private boolean running = true;
    private Scanner scanner; // Added Scanner for input
    
    // Constants for UI layout
    private static final int CONSOLE_WIDTH = 1024;
    private static final int CONSOLE_HEIGHT = 768;
    private static final int HEADER_Y = 50;
    private static final int MENU_START_Y = 150;
    private static final int MENU_ITEM_HEIGHT = 40;
    private static final int CONTENT_START_Y = 250;
    private static final int CONTENT_START_X = 50;
    
    // Colors
    private static final Color HEADER_COLOR = new Color(25, 25, 112);
    private static final Color TEXT_COLOR = new Color(50, 50, 50);
    private static final Color HIGHLIGHT_COLOR = new Color(70, 130, 180);
    private static final Color SUCCESS_COLOR = new Color(46, 139, 87);
    private static final Color ERROR_COLOR = new Color(178, 34, 34);
    
    // Fonts
    private static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 28);
    private static final Font MENU_FONT = new Font("Arial", Font.BOLD, 18);
    private static final Font CONTENT_FONT = new Font("Arial", Font.PLAIN, 16);
    private static final Font INPUT_FONT = new Font("Arial", Font.BOLD, 16);
    
    public QUBLibraryUpdated() {
        // Initialize console
        con = new Console(true);
        con.setSize(CONSOLE_WIDTH, CONSOLE_HEIGHT);
        con.setVisible(true);
        
        // Initialize scanner for input
        scanner = new Scanner(System.in);
        
        // Initialize library
        library = new Library();
        initializeLibrary();
        
        // Start the application
        run();
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
        while (running) {
            displayMainMenu();
            processMainMenuChoice();
        }
    }
    
    private void displayMainMenu() {
        // Clear console
        con.clear();
        
        // Set header font and color
        con.setFont(HEADER_FONT);
        con.setColour(HEADER_COLOR);
        
        // Draw header
        con.println("\n\n QUB Library Management System");
        con.println(" ============================");
        
        // Display menu options
        con.setFont(MENU_FONT);
        con.setColour(TEXT_COLOR);
        con.println("\n Main Menu:");
        con.println(" 1. List All Books");
        con.println(" 2. List Books by Status");
        con.println(" 3. Add a Book");
        con.println(" 4. Remove a Book");
        con.println(" 5. Borrow a Book");
        con.println(" 6. Return a Book");
        con.println(" 7. Display Ranked List");
        con.println(" 8. Exit");
        
        // Display text-based decorative elements instead of images
        con.setFont(CONTENT_FONT);
        con.setColour(HIGHLIGHT_COLOR);
        con.println("\n" + "=".repeat(50));
        con.println(" QUB LIBRARY - Your Gateway to Knowledge");
        con.println(" " + "=".repeat(50));
        
        con.setFont(INPUT_FONT);
        con.setColour(HIGHLIGHT_COLOR);
        con.print("\n Enter your choice (1-8): ");
    }
    
    private void processMainMenuChoice() {
        try {
            // Read user choice using our custom method
            int choice = readIntFromConsole();
            
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
                    con.setFont(CONTENT_FONT);
                    con.setColour(SUCCESS_COLOR);
                    con.println("\n Thank you for using QUB Library System. Goodbye!");
                    break;
                default:
                    con.setFont(CONTENT_FONT);
                    con.setColour(ERROR_COLOR);
                    con.println("\n Invalid choice. Please enter a number between 1 and 8.");
                    waitForKeyPress();
            }
        } catch (Exception e) {
            // Handle input errors
            con.setFont(CONTENT_FONT);
            con.setColour(ERROR_COLOR);
            con.println("\n Invalid input. Please enter a number.");
            waitForKeyPress();
        }
    }
    
    private void waitForKeyPress() {
        con.setFont(CONTENT_FONT);
        con.setColour(TEXT_COLOR);
        con.println("\n Press Enter in the terminal/console to continue...");
        
        try {
            // Use scanner to wait for Enter key
            scanner.nextLine();
        } catch (Exception e) {
            // If scanner fails, use a simple pause
            try {
                Thread.sleep(2000); // Wait 2 seconds
            } catch (InterruptedException ie) {
                // Ignore interruption
            }
        }
    }
    
    private void listAllBooks() {
        con.clear();
        
        // Set header
        con.setFont(HEADER_FONT);
        con.setColour(HEADER_COLOR);
        con.println("\n All Books");
        con.println(" =========");
        
        LibraryBook[] books = library.list();
        
        if (books.length == 0) {
            con.setFont(CONTENT_FONT);
            con.setColour(ERROR_COLOR);
            con.println("\n No books in the library.");
        } else {
            // Display table header
            con.setFont(MENU_FONT);
            con.setColour(HIGHLIGHT_COLOR);
            con.println("\n ID | Title                          | Author                  | Status    | Price  | Loans");
            con.println(" " + "-".repeat(90));
            
            // Display book rows
            con.setFont(CONTENT_FONT);
            for (LibraryBook book : books) {
                // Set color based on status
                switch (book.getStatus()) {
                    case AVAILABLE:
                        con.setColour(SUCCESS_COLOR);
                        break;
                    case ON_LOAN:
                        con.setColour(Color.ORANGE);
                        break;
                    case WITHDRAWN:
                        con.setColour(ERROR_COLOR);
                        break;
                    default:
                        con.setColour(TEXT_COLOR);
                }
                
                // Format and display book details
                String id = String.format(" %-3d", book.getId());
                String title = String.format("| %-30s", truncateString(book.getTitle(), 28));
                String author = String.format("| %-24s", truncateString(book.getAuthor(), 22));
                String status = String.format("| %-9s", book.getStatus());
                String price = String.format("| £%-6.2f", book.getPrice());
                String loans = String.format("| %-5d", book.getLoanCount());
                
                con.println(id + title + author + status + price + loans);
                
                // Display text representation of book cover instead of image
                con.setColour(HIGHLIGHT_COLOR);
                con.println("   [Book Cover: " + book.getImage() + "]");
            }
        }
        
        waitForKeyPress();
    }
    
    private void listBooksByStatus() {
        con.clear();
        
        // Set header
        con.setFont(HEADER_FONT);
        con.setColour(HEADER_COLOR);
        con.println("\n List Books by Status");
        con.println(" ===================");
        
        // Display status options
        con.setFont(MENU_FONT);
        con.setColour(TEXT_COLOR);
        con.println("\n Select a status:");
        con.println(" 1. Available");
        con.println(" 2. On Loan");
        con.println(" 3. Withdrawn");
        con.println(" 4. Return to Main Menu");
        
        con.setFont(INPUT_FONT);
        con.setColour(HIGHLIGHT_COLOR);
        con.print("\n Enter your choice (1-4): ");
        
        try {
            int statusChoice = readIntFromConsole();
            
            if (statusChoice >= 1 && statusChoice <= 3) {
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
                        status = BookStatus.AVAILABLE;
                }
                
                displayBooksWithStatus(status);
            } else if (statusChoice != 4) {
                con.setFont(CONTENT_FONT);
                con.setColour(ERROR_COLOR);
                con.println("\n Invalid choice. Please enter a number between 1 and 4.");
                waitForKeyPress();
            }
        } catch (Exception e) {
            con.setFont(CONTENT_FONT);
            con.setColour(ERROR_COLOR);
            con.println("\n Invalid input. Please enter a number.");
            waitForKeyPress();
        }
    }
    
    private void displayBooksWithStatus(BookStatus status) {
        con.clear();
        
        // Set header
        con.setFont(HEADER_FONT);
        con.setColour(HEADER_COLOR);
        con.println("\n Books with Status: " + status);
        con.println(" " + "=".repeat(20 + status.toString().length()));
        
        LibraryBook[] books = library.list(status);
        
        if (books.length == 0) {
            con.setFont(CONTENT_FONT);
            con.setColour(ERROR_COLOR);
            con.println("\n No books with status: " + status);
        } else {
            // Display table header
            con.setFont(MENU_FONT);
            con.setColour(HIGHLIGHT_COLOR);
            con.println("\n ID | Title                          | Author                  | Price  | Loans");
            con.println(" " + "-".repeat(80));
            
            // Display book rows
            con.setFont(CONTENT_FONT);
            con.setColour(TEXT_COLOR);
            for (LibraryBook book : books) {
                // Format and display book details
                String id = String.format(" %-3d", book.getId());
                String title = String.format("| %-30s", truncateString(book.getTitle(), 28));
                String author = String.format("| %-24s", truncateString(book.getAuthor(), 22));
                String price = String.format("| £%-6.2f", book.getPrice());
                String loans = String.format("| %-5d", book.getLoanCount());
                
                con.println(id + title + author + price + loans);
                
                // Display text representation of book cover instead of image
                con.setColour(HIGHLIGHT_COLOR);
                con.println("   [Book Cover: " + book.getImage() + "]");
            }
        }
        
        waitForKeyPress();
    }
    
    private void addBook() {
        con.clear();
        
        // Set header
        con.setFont(HEADER_FONT);
        con.setColour(HEADER_COLOR);
        con.println("\n Add a New Book");
        con.println(" ==============");
        
        try {
            // Get book details
            con.setFont(CONTENT_FONT);
            con.setColour(TEXT_COLOR);
            
            con.print("\n Enter title (5-40 characters): ");
            con.setColour(HIGHLIGHT_COLOR);
            String title = readLineFromConsole();
            
            con.setColour(TEXT_COLOR);
            con.print(" Enter author (5-40 characters): ");
            con.setColour(HIGHLIGHT_COLOR);
            String author = readLineFromConsole();
            
            con.setColour(TEXT_COLOR);
            con.print(" Enter ISBN (exactly 10 digits): ");
            con.setColour(HIGHLIGHT_COLOR);
            String isbn = readLineFromConsole();
            
            // Book type selection
            con.setColour(TEXT_COLOR);
            con.println("\n Book Types:");
            con.println(" 1. FICTION");
            con.println(" 2. NON_FICTION");
            con.println(" 3. REFERENCE");
            con.println(" 4. TEXTBOOK");
            con.print(" Enter book type (1-4): ");
            con.setColour(HIGHLIGHT_COLOR);
            int typeChoice = readIntFromConsole();
            
            BookType type;
            switch (typeChoice) {
                case 1: type = BookType.FICTION; break;
                case 2: type = BookType.NON_FICTION; break;
                case 3: type = BookType.REFERENCE; break;
                case 4: type = BookType.TEXTBOOK; break;
                default:
                    con.setColour(ERROR_COLOR);
                    con.println(" Invalid book type. Using FICTION as default.");
                    type = BookType.FICTION;
            }
            
            con.setColour(TEXT_COLOR);
            con.print(" Enter edition (must be >= 1): ");
            con.setColour(HIGHLIGHT_COLOR);
            int edition = readIntFromConsole();
            
            con.setColour(TEXT_COLOR);
            con.print(" Enter summary (20-150 characters): ");
            con.setColour(HIGHLIGHT_COLOR);
            String summary = readLineFromConsole();
            
            con.setColour(TEXT_COLOR);
            con.print(" Enter price (must be > £0.00): ");
            con.setColour(HIGHLIGHT_COLOR);
            double price = readDoubleFromConsole();
            
            con.setColour(TEXT_COLOR);
            con.print(" Enter cover image filename: ");
            con.setColour(HIGHLIGHT_COLOR);
            String coverImage = readLineFromConsole();
            
            // Display text representation of book cover instead of image
            con.setColour(HIGHLIGHT_COLOR);
            con.println("\n [Book Cover: " + coverImage + "]");
            
            // Add the book
            boolean success = library.add(title, author, isbn, type, edition, summary, price, coverImage);
            
            if (success) {
                con.setColour(SUCCESS_COLOR);
                con.println("\n Book added successfully!");
            } else {
                con.setColour(ERROR_COLOR);
                con.println("\n Failed to add book. Please check your input.");
            }
            
        } catch (Exception e) {
            con.setColour(ERROR_COLOR);
            con.println("\n Error adding book: " + e.getMessage());
        }
        
        waitForKeyPress();
    }
    
    private void removeBook() {
        con.clear();
        
        // Set header
        con.setFont(HEADER_FONT);
        con.setColour(HEADER_COLOR);
        con.println("\n Remove a Book");
        con.println(" =============");
        
        LibraryBook[] books = library.list();
        
        // Filter out books that are on loan
        int availableCount = 0;
        for (LibraryBook book : books) {
            if (book.getStatus() != BookStatus.ON_LOAN) {
                availableCount++;
            }
        }
        
        if (availableCount == 0) {
            con.setFont(CONTENT_FONT);
            con.setColour(ERROR_COLOR);
            con.println("\n No books available to remove.");
        } else {
            con.setFont(CONTENT_FONT);
            con.setColour(TEXT_COLOR);
            con.println("\n Available books:");
            
            // Display table header
            con.setFont(MENU_FONT);
            con.setColour(HIGHLIGHT_COLOR);
            con.println("\n ID | Title                          | Author                  | Status");
            con.println(" " + "-".repeat(70));
            
            // Display book rows
            con.setFont(CONTENT_FONT);
            for (LibraryBook book : books) {
                if (book.getStatus() != BookStatus.ON_LOAN) {
                    // Format and display book details
                    String id = String.format(" %-3d", book.getId());
                    String title = String.format("| %-30s", truncateString(book.getTitle(), 28));
                    String author = String.format("| %-24s", truncateString(book.getAuthor(), 22));
                    String status = String.format("| %-9s", book.getStatus());
                    
                    con.setColour(TEXT_COLOR);
                    con.println(id + title + author + status);
                    
                    // Display text representation of book cover instead of image
                    con.setColour(HIGHLIGHT_COLOR);
                    con.println("   [Book Cover: " + book.getImage() + "]");
                }
            }
            
            // Get book ID to remove
            con.setFont(INPUT_FONT);
            con.setColour(TEXT_COLOR);
            con.print("\n Enter the ID of the book to remove: ");
            con.setColour(HIGHLIGHT_COLOR);
            
            try {
                int id = readIntFromConsole();
                boolean success = library.remove(id);
                
                if (success) {
                    con.setColour(SUCCESS_COLOR);
                    con.println("\n Book marked as withdrawn successfully.");
                } else {
                    con.setColour(ERROR_COLOR);
                    con.println("\n Failed to remove book. It may be on loan or not exist.");
                }
            } catch (Exception e) {
                con.setColour(ERROR_COLOR);
                con.println("\n Invalid ID format.");
            }
        }
        
        waitForKeyPress();
    }
    
    private void borrowBook() {
        con.clear();
        
        // Set header
        con.setFont(HEADER_FONT);
        con.setColour(HEADER_COLOR);
        con.println("\n Borrow a Book");
        con.println(" =============");
        
        LibraryBook[] availableBooks = library.list(BookStatus.AVAILABLE);
        
        if (availableBooks.length == 0) {
            con.setFont(CONTENT_FONT);
            con.setColour(ERROR_COLOR);
            con.println("\n No books available to borrow.");
        } else {
            con.setFont(CONTENT_FONT);
            con.setColour(TEXT_COLOR);
            con.println("\n Available books:");
            
            // Display table header
            con.setFont(MENU_FONT);
            con.setColour(HIGHLIGHT_COLOR);
            con.println("\n ID | Title                          | Author                  | Price");
            con.println(" " + "-".repeat(70));
            
            // Display book rows
            con.setFont(CONTENT_FONT);
            con.setColour(TEXT_COLOR);
            for (LibraryBook book : availableBooks) {
                // Format and display book details
                String id = String.format(" %-3d", book.getId());
                String title = String.format("| %-30s", truncateString(book.getTitle(), 28));
                String author = String.format("| %-24s", truncateString(book.getAuthor(), 22));
                String price = String.format("| £%-6.2f", book.getPrice());
                
                con.println(id + title + author + price);
                
                // Display text representation of book cover instead of image
                con.setColour(HIGHLIGHT_COLOR);
                con.println("   [Book Cover: " + book.getImage() + "]");
            }
            
            // Get book ID to borrow
            con.setFont(INPUT_FONT);
            con.setColour(TEXT_COLOR);
            con.print("\n Enter the ID of the book to borrow: ");
            con.setColour(HIGHLIGHT_COLOR);
            
            try {
                int id = readIntFromConsole();
                boolean success = library.borrowBook(id);
                
                if (success) {
                    con.setColour(SUCCESS_COLOR);
                    con.println("\n Book borrowed successfully.");
                } else {
                    con.setColour(ERROR_COLOR);
                    con.println("\n Failed to borrow book. It may not be available or not exist.");
                }
            } catch (Exception e) {
                con.setColour(ERROR_COLOR);
                con.println("\n Invalid ID format.");
            }
        }
        
        waitForKeyPress();
    }
    
    private void returnBook() {
        con.clear();
        
        // Set header
        con.setFont(HEADER_FONT);
        con.setColour(HEADER_COLOR);
        con.println("\n Return a Book");
        con.println(" =============");
        
        LibraryBook[] onLoanBooks = library.list(BookStatus.ON_LOAN);
        
        if (onLoanBooks.length == 0) {
            con.setFont(CONTENT_FONT);
            con.setColour(ERROR_COLOR);
            con.println("\n No books currently on loan.");
        } else {
            con.setFont(CONTENT_FONT);
            con.setColour(TEXT_COLOR);
            con.println("\n Books on loan:");
            
            // Display table header
            con.setFont(MENU_FONT);
            con.setColour(HIGHLIGHT_COLOR);
            con.println("\n ID | Title                          | Author                  | Times Borrowed");
            con.println(" " + "-".repeat(75));
            
            // Display book rows
            con.setFont(CONTENT_FONT);
            con.setColour(TEXT_COLOR);
            for (LibraryBook book : onLoanBooks) {
                // Format and display book details
                String id = String.format(" %-3d", book.getId());
                String title = String.format("| %-30s", truncateString(book.getTitle(), 28));
                String author = String.format("| %-24s", truncateString(book.getAuthor(), 22));
                String loans = String.format("| %-14d", book.getLoanCount());
                
                con.println(id + title + author + loans);
                
                // Display text representation of book cover instead of image
                con.setColour(HIGHLIGHT_COLOR);
                con.println("   [Book Cover: " + book.getImage() + "]");
            }
            
            // Get book ID to return
            con.setFont(INPUT_FONT);
            con.setColour(TEXT_COLOR);
            con.print("\n Enter the ID of the book to return: ");
            con.setColour(HIGHLIGHT_COLOR);
            
            try {
                int id = readIntFromConsole();
                boolean success = library.returnBook(id);
                
                if (success) {
                    con.setColour(SUCCESS_COLOR);
                    con.println("\n Book returned successfully.");
                } else {
                    con.setColour(ERROR_COLOR);
                    con.println("\n Failed to return book. It may not be on loan or not exist.");
                }
            } catch (Exception e) {
                con.setColour(ERROR_COLOR);
                con.println("\n Invalid ID format.");
            }
        }
        
        waitForKeyPress();
    }
    
    private void displayRankedList() {
        con.clear();
        
        // Set header
        con.setFont(HEADER_FONT);
        con.setColour(HEADER_COLOR);
        con.println("\n Most Popular Books");
        con.println(" ==================");
        
        LibraryBook[] rankedBooks = library.mostPopular();
        
        if (rankedBooks.length == 0) {
            con.setFont(CONTENT_FONT);
            con.setColour(ERROR_COLOR);
            con.println("\n No books in the library.");
        } else {
            con.setFont(CONTENT_FONT);
            con.setColour(TEXT_COLOR);
            con.println("\n Books ranked by popularity (times borrowed):");
            
            // Display table header
            con.setFont(MENU_FONT);
            con.setColour(HIGHLIGHT_COLOR);
            con.println("\n Rank | Title                          | Author                  | Times Borrowed");
            con.println(" " + "-".repeat(80));
            
            // Display book rows
            con.setFont(CONTENT_FONT);
            for (int i = 0; i < rankedBooks.length; i++) {
                LibraryBook book = rankedBooks[i];
                
                // Set color based on rank
                if (i == 0) {
                    con.setColour(Color.ORANGE); // Gold for 1st place
                } else if (i == 1) {
                    con.setColour(Color.LIGHT_GRAY); // Silver for 2nd place
                } else if (i == 2) {
                    con.setColour(new Color(205, 127, 50)); // Bronze for 3rd place
                } else {
                    con.setColour(TEXT_COLOR);
                }
                
                // Format and display book details
                String rank = String.format(" %-4d", i + 1);
                String title = String.format("| %-30s", truncateString(book.getTitle(), 28));
                String author = String.format("| %-24s", truncateString(book.getAuthor(), 22));
                String loans = String.format("| %-14d", book.getLoanCount());
                
                con.println(rank + title + author + loans);
                
                // Display text representation of medals and book covers instead of images
                if (i < 3) {
                    con.println("   [Medal: " + (i+1) + "]");
                }
                
                con.setColour(HIGHLIGHT_COLOR);
                con.println("   [Book Cover: " + book.getImage() + "]");
            }
            
            // Display a simple text-based bar chart for the top 5 books
            if (rankedBooks.length > 0) {
                con.setFont(MENU_FONT);
                con.setColour(HIGHLIGHT_COLOR);
                con.println("\n\n Popularity Chart (Top 5 Books):");
                con.println(" " + "-".repeat(30));
                
                // Find the maximum loan count for scaling
                int maxLoanCount = 0;
                for (int i = 0; i < Math.min(rankedBooks.length, 5); i++) {
                    if (rankedBooks[i].getLoanCount() > maxLoanCount) {
                        maxLoanCount = rankedBooks[i].getLoanCount();
                    }
                }
                
                // Draw simple text-based bar chart
                con.setFont(CONTENT_FONT);
                for (int i = 0; i < Math.min(rankedBooks.length, 5); i++) {
                    LibraryBook book = rankedBooks[i];
                    int barLength = maxLoanCount > 0 ? (book.getLoanCount() * 20) / maxLoanCount : 0;
                    
                    // Display book title (shortened) and bar
                    String shortTitle = truncateString(book.getTitle(), 15);
                    con.setColour(TEXT_COLOR);
                    con.print(" " + shortTitle + " " + "." + " ".repeat(20 - shortTitle.length()));
                    
                    // Draw the bar with appropriate color
                    if (i == 0) {
                        con.setColour(Color.ORANGE); // Gold for 1st place
                    } else if (i == 1) {
                        con.setColour(Color.LIGHT_GRAY); // Silver for 2nd place
                    } else if (i == 2) {
                        con.setColour(new Color(205, 127, 50)); // Bronze for 3rd place
                    } else {
                        con.setColour(HIGHLIGHT_COLOR);
                    }
                    
                    con.print("|" + "=".repeat(barLength) + " " + book.getLoanCount());
                    con.println();
                }
            }
        }
        
        waitForKeyPress();
    }
    
    // New methods for input handling using Scanner
    private String readLineFromConsole() {
        con.println(" (Enter your input in the terminal/console)");
        try {
            return scanner.nextLine();
        } catch (Exception e) {
            // If scanner fails, return a default value
            return "Default value";
        }
    }
    
    private int readIntFromConsole() {
        con.println(" (Enter a number in the terminal/console)");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            // If parsing fails, return a default value
            return 1;
        }
    }
    
    private double readDoubleFromConsole() {
        con.println(" (Enter a decimal number in the terminal/console)");
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (Exception e) {
            // If parsing fails, return a default value
            return 1.0;
        }
    }
    
      {
            // If parsing fails, return a default value
            return 1.0;
        }
    }
    
    // Helper method to truncate strings
    private String truncateString(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }
    
    public static void main(String[] args) {
        new QUBLibraryUpdated();
    }
}