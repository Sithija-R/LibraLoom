package dev.LibraLoom.Services;

import dev.LibraLoom.Exception.UserException;
import dev.LibraLoom.Models.Book;
import dev.LibraLoom.Repositories.BookRepo;
import dev.LibraLoom.Repositories.LibraryRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
// Ensures that the database state is rolled back after each test
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private LibraryRepo libraryRepo;

    private final String existingIsbn = "1234567890123"; // Assume this ISBN exists in your database
    private final String nonExistingIsbn = "0000000000000"; // ISBN that doesn't exist
    Book testBook;

    @BeforeEach
    public void setUp() {
        // Create and save a test book to the database
        if (bookRepo.findByIsbn(existingIsbn) == null) {
            testBook = new Book();
            testBook.setIsbn(existingIsbn);
            testBook.setTitle("Test Book Title");
            testBook.setAuthor("Test Author");
            testBook.setPublicationYear(2024);
            bookRepo.save(testBook);}
    }

    @Test
    public void testGetAllBooks() throws UserException {
        List<Book> books = bookService.getAll();
        assertNotNull(books, "Books list should not be null");
        assertFalse(books.isEmpty(), "Books list should not be empty");
    }

    @Test
    public void testGetBookByISBN_Valid() throws UserException {
        Book book = bookService.getByISBN(existingIsbn);
        assertNotNull(book, "Book should not be null");
        assertEquals(existingIsbn, book.getIsbn(), "Expected ISBN: " + existingIsbn + ", but got: " + book.getIsbn());
    }

    @Test
    public void testGetBookByISBN_Invalid() {
        Exception exception = assertThrows(UserException.class, () -> {
            bookService.getByISBN(nonExistingIsbn);
        });
        assertEquals("Book with isbn " + nonExistingIsbn + " not found.", exception.getMessage());
    }

    @Test
    public void testAddBook_Valid() throws UserException {
        Book newBook = new Book();
        newBook.setIsbn("1234567890124"); // Different ISBN
        newBook.setTitle("New Book Title");
        newBook.setAuthor("New Author");
        newBook.setPublicationYear(2024);
        
        Book addedBook = bookService.addBook(newBook);
        assertNotNull(addedBook, "Added book should not be null");
        assertEquals(newBook.getIsbn(), addedBook.getIsbn(),
                "Expected ISBN: " + newBook.getIsbn() + ", but got: " + addedBook.getIsbn());
    }

    @Test
    public void testAddBook_AlreadyExists() {
        Exception exception = assertThrows(UserException.class, () -> {
            bookService.addBook(testBook);
        });
        assertEquals("A book with this ISBN already exists.", exception.getMessage());
    }

    @Test
    public void testEditBook_Valid() throws UserException {
        testBook.setTitle("Updated Test Book Title");
        Book editedBook = bookService.editBook(testBook);
        assertNotNull(editedBook, "Edited book should not be null");
        assertEquals("Updated Test Book Title", editedBook.getTitle(), "Book title should be updated.");
    }

    @Test
    public void testEditBook_NonExisting() {
        testBook.setIsbn(nonExistingIsbn); // Set an ISBN that doesn't exist
        Exception exception = assertThrows(UserException.class, () -> {
            bookService.editBook(testBook);
        });
        assertEquals("No book found!", exception.getMessage());
    }

    @Test
    public void testDeleteBook_Valid() throws UserException {
        bookService.deleteBook(existingIsbn);
        // Verify that the book no longer exists
        assertThrows(UserException.class, () -> {
            bookService.getByISBN(existingIsbn);
        });
    }

    @Test
    public void testDeleteBook_NonExisting() {
        Exception exception = assertThrows(UserException.class, () -> {
            bookService.deleteBook(nonExistingIsbn);
        });
        assertEquals("No book found!", exception.getMessage());
    }
}
