package dev.LibraLoom;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dev.LibraLoom.Exception.UserException;
import dev.LibraLoom.Models.Book;
import dev.LibraLoom.Models.Library;
import dev.LibraLoom.Repositories.BookRepo;
import dev.LibraLoom.Repositories.LibraryRepo;
import dev.LibraLoom.Services.BookService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepo bookRepo;

    @Mock
    private LibraryRepo libraryRepo;

    private Book book;
    private Library library;

    @BeforeEach
public void setUp() {
    MockitoAnnotations.openMocks(this);
    book = new Book();
    book.setIsbn("1234567890");
    book.setTitle("Test Book");
    book.setAuthor("Author");
    book.setPublicationYear(2023);

    library = new Library();
    library.setId("library01");
    library.setListofBooks(new ArrayList<>(Arrays.asList(book)));  
    library.setListofAvailableBooks(new ArrayList<>(Arrays.asList(book))); 
}


    @Test
    public void testGetAllBooks() throws UserException {
        when(bookRepo.findAll()).thenReturn(Arrays.asList(book));

        List<Book> books = bookService.getAll();

        assertNotNull(books);
        assertEquals(1, books.size());
        verify(bookRepo, times(1)).findAll();
    }

    @Test
    public void testGetByISBN_Exists() throws UserException {
        when(bookRepo.findByIsbn("1234567890")).thenReturn(book);

        Book foundBook = bookService.getByISBN("1234567890");

        assertNotNull(foundBook);
        assertEquals("Test Book", foundBook.getTitle());
        verify(bookRepo, times(1)).findByIsbn("1234567890");
    }

    @Test
    public void testGetByISBN_NotFound() {
        when(bookRepo.findByIsbn("invalidIsbn")).thenReturn(null);

        assertThrows(UserException.class, () -> bookService.getByISBN("invalidIsbn"));
        verify(bookRepo, times(1)).findByIsbn("invalidIsbn");
    }

    @Test
    public void testAddBook_New() throws UserException {
        when(bookRepo.findByIsbn("1234567890")).thenReturn(null);
        when(libraryRepo.findById("library01")).thenReturn(Optional.of(library));

        Book newBook = bookService.addBook(book);

        assertNotNull(newBook);
        verify(bookRepo, times(1)).insert(newBook);
        verify(libraryRepo, times(1)).save(library);
    }

    @Test
    public void testAddBook_AlreadyExists() {
        when(bookRepo.findByIsbn("1234567890")).thenReturn(book);

        assertThrows(UserException.class, () -> bookService.addBook(book));
        verify(bookRepo, times(1)).findByIsbn("1234567890");
    }

    @Test
    public void testEditBook_Exists() throws UserException {
        when(bookRepo.findByIsbn("1234567890")).thenReturn(book);
        when(libraryRepo.findById("library01")).thenReturn(Optional.of(library));

        book.setTitle("Updated Title");
        Book updatedBook = bookService.editBook(book);

        assertNotNull(updatedBook);
        assertEquals("Updated Title", updatedBook.getTitle());
        verify(bookRepo, times(1)).save(book);
        verify(libraryRepo, times(1)).save(library);
    }

    @Test
    public void testEditBook_NotFound() {
        when(bookRepo.findByIsbn("invalidIsbn")).thenReturn(null);

        assertThrows(UserException.class, () -> bookService.editBook(book));
        verify(bookRepo, times(1)).findByIsbn("invalidIsbn");
    }

    @Test
    public void testDeleteBook_Exists() throws UserException {
        when(bookRepo.findByIsbn("1234567890")).thenReturn(book);
        when(libraryRepo.findById("library01")).thenReturn(Optional.of(library));

        bookService.deleteBook("1234567890");

        verify(bookRepo, times(1)).delete(book);
        verify(libraryRepo, times(1)).save(library);
    }

    @Test
    public void testDeleteBook_NotFound() {
        when(bookRepo.findByIsbn("invalidIsbn")).thenReturn(null);

        assertThrows(UserException.class, () -> bookService.deleteBook("invalidIsbn"));
        verify(bookRepo, times(1)).findByIsbn("invalidIsbn");
    }
}
