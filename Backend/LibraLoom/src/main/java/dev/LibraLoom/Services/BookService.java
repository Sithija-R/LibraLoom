package dev.LibraLoom.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.LibraLoom.Exception.UserException;
import dev.LibraLoom.Models.Book;
import dev.LibraLoom.Models.Library;
import dev.LibraLoom.Repositories.BookRepo;
import dev.LibraLoom.Repositories.LibraryRepo;

@Service
public class BookService {

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private LibraryRepo libraryRepo;

    // get all books
    public List<Book> getAll() throws UserException {
        try {
            return bookRepo.findAll();
        } catch (Exception e) {
            throw new UserException("Failed to retrieve books.");
        }
    }

    // get book by isbn
    public Book getByISBN(String isbn) throws UserException {
        Book book = bookRepo.findByIsbn(isbn);
        if (book == null) {
            throw new UserException("Book with isbn " + isbn + " not found.");
        }
        return book;
    }

    // add book
    public Book addBook(Book book) throws UserException {
        // Check if a book with the same ISBN already exists
        Book existingBook = bookRepo.findByIsbn(book.getIsbn());
        if (existingBook != null) {
            throw new UserException("A book with this ISBN already exists.");
        }

        // Fetch the library
        Library library = libraryRepo.findById("library01")
                .orElseThrow(() -> new RuntimeException("Library not found"));

        // Create and set properties for the new book
        Book newBook = new Book();
        newBook.setIsbn(book.getIsbn());
        newBook.setTitle(book.getTitle());
        newBook.setAuthor(book.getAuthor());
        newBook.setPublicationYear(book.getPublicationYear());
        newBook.setAvailable(true);
        newBook.setBorrower(null);

        // Save the new book to the repository

        // Update the library's book lists
        library.getListofBooks().add(newBook);
        library.getListofAvailableBooks().add(newBook);

        // Save the updated library document
        bookRepo.insert(newBook);
        libraryRepo.save(library);

        return newBook;

    }

    // edit book
    public Book editBook(Book book) throws UserException {

        Book reqBook = bookRepo.findByIsbn(book.getIsbn());

        if (reqBook == null) {
            throw new UserException("No book found!");
        }
        if (book.getTitle() != null && !book.getTitle().isEmpty()) {
            reqBook.setTitle(book.getTitle());
        }
        if (book.getAuthor() != null && !book.getAuthor().isEmpty()) {
            reqBook.setAuthor(book.getAuthor());
        }
        if (book.getPublicationYear() > 0) {
            reqBook.setPublicationYear(book.getPublicationYear());
        }

        bookRepo.save(reqBook);

        Library library = libraryRepo.findById("library01")
                .orElseThrow(() -> new RuntimeException("Library not found"));

       
        library.getListofBooks().stream()
                .filter(libraryBook -> libraryBook.getIsbn().equals(reqBook.getIsbn())) // Match by ISBN
                .forEach(libraryBook -> {
                    libraryBook.setTitle(reqBook.getTitle());
                    libraryBook.setAuthor(reqBook.getAuthor());
                    libraryBook.setPublicationYear(reqBook.getPublicationYear());
                });

        libraryRepo.save(library);

        return reqBook;
    }

    // delete book
    public void deleteBook(String isbn) throws UserException {
        Book book = bookRepo.findByIsbn(isbn);

        Library library = libraryRepo.findById("library01")
                .orElseThrow(() -> new RuntimeException("Library not found"));

        if (book == null) {
            throw new UserException("No book found!");
        }
        library.getListofBooks().remove(book);
        library.getListofAvailableBooks().remove(book);
        bookRepo.delete(book);
        libraryRepo.save(library);

    }
}
