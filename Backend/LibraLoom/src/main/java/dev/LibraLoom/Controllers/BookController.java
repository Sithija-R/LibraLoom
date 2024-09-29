package dev.LibraLoom.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;

import dev.LibraLoom.Exception.UserException;
import dev.LibraLoom.Models.Book;
import dev.LibraLoom.Response.ApiResponse;
import dev.LibraLoom.Services.BookService;



@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Map<String, Object>> handleUserException(UserException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("status", false);

        HttpStatus status = ex.getMessage().equals("Book not found") ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Invalid book data");
        response.put("status", false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Book>> getAllBooks() throws UserException {
        try {
            List<Book> books = bookService.getAll();
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (UserException ex) {
            throw new UserException("Failed to fetch books");
        }
    }

    @GetMapping("/get/available")
    public ResponseEntity<List<Book>> getAvailableBook() {
        return new ResponseEntity<>(bookService.findAvailableBooks(), HttpStatus.OK);
    }

    @GetMapping("/get/{isbn}")
    public ResponseEntity<Book> getBookByISBN(@PathVariable String isbn) throws UserException {
        try {
            Book book = bookService.getByISBN(isbn);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (UserException ex) {
            throw new UserException("Book not found");
        }
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Book>> searchBook(@PathVariable String keyword) {
        return new ResponseEntity<>(bookService.searchBooks(keyword), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody Book book) throws UserException {
        Book newBook = bookService.addBook(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @PostMapping("/edit")
    public ResponseEntity<Book> editBook(@RequestBody Book book) throws UserException {
        Book reqBook = bookService.editBook(book);
        return new ResponseEntity<>(reqBook, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{isbn}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable String isbn) throws UserException {
        bookService.deleteBook(isbn);
        ApiResponse response = new ApiResponse("Successfully Deleted!", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
