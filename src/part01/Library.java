package part01;

import java.util.ArrayList;

public class Library {
    private ArrayList<LibraryBook> books;

    public Library() {
        books = new ArrayList<>();
    }

    public boolean borrowBook(int id) {
        LibraryBook book = search(id);
        if (book != null) {
            return book.checkout();
        }
        return false;
    }

    public boolean returnBook(int id) {
        LibraryBook book = search(id);
        if (book != null) {
            return book.checkIn();
        }
        return false;
    }

    public LibraryBook[] list() {
        LibraryBook[] result = new LibraryBook[books.size()];
        for (int i = 0; i < books.size(); i++) {
            result[i] = books.get(i);
        }
        return result;
    }

    public LibraryBook[] list(BookStatus status) {
        // First, count books with matching status
        int count = 0;
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getStatus() == status) {
                count++;
            }
        }
        
        // Create array of correct size and populate
        LibraryBook[] result = new LibraryBook[count];
        int index = 0;
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getStatus() == status) {
                result[index++] = books.get(i);
            }
        }
        return result;
    }

    public LibraryBook[] mostPopular() {
        // Create a copy of the books array
        LibraryBook[] result = list();
        
        // Sort by loan count (bubble sort)
        for (int i = 0; i < result.length - 1; i++) {
            for (int j = 0; j < result.length - i - 1; j++) {
                if (result[j].getLoanCount() < result[j + 1].getLoanCount()) {
                    // Swap
                    LibraryBook temp = result[j];
                    result[j] = result[j + 1];
                    result[j + 1] = temp;
                }
            }
        }
        
        return result;
    }

    public LibraryBook search(int id) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == id) {
                return books.get(i);
            }
        }
        return null;
    }

    public boolean remove(int id) {
        LibraryBook book = search(id);
        if (book != null && book.getStatus() != BookStatus.ON_LOAN) {
            book.setStatus(BookStatus.WITHDRAWN);
            return true;
        }
        return false;
    }

    public boolean add(String title, String author, String isbn, BookType type, 
                      int edition, String summary, double price, String coverImage) {
        try {
            // All validations are handled in the LibraryBook constructor
            LibraryBook newBook = new LibraryBook(title, author, isbn, type, edition, summary, price, coverImage);
            books.add(newBook);
            return true;
        } catch (IllegalArgumentException e) {
            // Return false if any validation fails
            return false;
        }
    }
}