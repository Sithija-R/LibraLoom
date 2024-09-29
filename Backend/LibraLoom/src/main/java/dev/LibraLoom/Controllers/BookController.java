package dev.LibraLoom.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.LibraLoom.Exception.UserException;
import dev.LibraLoom.Models.Book;
import dev.LibraLoom.Response.ApiResponse;
import dev.LibraLoom.Services.BookService;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    // get all book
    @GetMapping("/get/all")
    public ResponseEntity<List<Book>> getAllBooks() throws UserException {
      
        return new ResponseEntity<>(bookService.getAll(), HttpStatus.OK);
    }

    //
    @GetMapping("/get/available")
    public ResponseEntity<List<Book>> getAvailableBook(){
      return new ResponseEntity<>(bookService.findAvailableBooks(),HttpStatus.OK);
    }

    // get book by isbn
    @GetMapping("/get/{isbn}")
    public ResponseEntity<Book> getBookISBN(@PathVariable String isbn) throws UserException {

        return new ResponseEntity<>(bookService.getByISBN(isbn), HttpStatus.OK);
    }

    //search book
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<Book>> searchBook(@PathVariable String keyword){
        
  
        return new ResponseEntity<>(bookService.searchBooks(keyword),HttpStatus.OK);
    }
    
    // add book
    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody Book book) throws UserException {

       
        Book newBook = bookService.addBook(book);

        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    // edit book
    @PostMapping("/edit")
    public ResponseEntity<Book> editBook(@RequestBody Book book) throws UserException{

        Book reqbook = bookService.editBook(book);

        return new ResponseEntity<>(reqbook, HttpStatus.ACCEPTED);
    }

    //delete book
    @DeleteMapping("delete/{isbn}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable String isbn) throws UserException{
        
        bookService.deleteBook(isbn);
        ApiResponse response = new ApiResponse("Successfully Deleted !", true);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
