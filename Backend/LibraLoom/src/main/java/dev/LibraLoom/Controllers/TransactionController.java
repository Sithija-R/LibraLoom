package dev.LibraLoom.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.LibraLoom.DTO.TransactionDTO;
import dev.LibraLoom.Exception.UserException;

import dev.LibraLoom.Models.Transaction;
import dev.LibraLoom.Repositories.LibraryRepo;
import dev.LibraLoom.Response.ErrorResponse;
import dev.LibraLoom.Services.BookService;
import dev.LibraLoom.Services.TransactionService;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {


    @Autowired
    BookService bookService;
    
    @Autowired
    LibraryRepo libraryRepo;

    @Autowired
    TransactionService transactionService;
    
    @GetMapping("/get/all")
    public ResponseEntity<List<Transaction>> getAll() throws UserException{
        
        System.out.println("hit ");
        return new ResponseEntity<>(transactionService.getAll(),HttpStatus.OK);
    }
    
    //get by uniqueId
    @GetMapping("/search/{uniqueId}")
    public ResponseEntity<List<Transaction>> getByUniqueId(@PathVariable String uniqueId) throws UserException{
        System.out.println(uniqueId);
       Transaction transaction =transactionService.findByUniqueId(uniqueId);
        List<Transaction> transactions =new ArrayList<>();
        transactions.add(transaction);
       
        return new ResponseEntity<>(transactions,HttpStatus.OK);
    }
    
    // borrow book
    @PostMapping("/borrow")
    public ResponseEntity<Transaction> borrowBook(@RequestBody TransactionDTO payload) throws UserException{

        String isbn = payload.getIsbn();
        String userId = payload.getUserId();
       Transaction transaction = transactionService.borrowBook(isbn,userId);
        
        return new ResponseEntity<>(transaction,HttpStatus.OK);
    }

    @PostMapping("/return")
    public ResponseEntity<Transaction> returnBook(@RequestBody TransactionDTO payload) throws UserException{

        String transactionId = payload.getTransactionId();
        String isbn = payload.getIsbn();
        String userId = payload.getUserId();
       Transaction transaction = transactionService.returnBook(transactionId, isbn, userId);
        
        return new ResponseEntity<>(transaction,HttpStatus.OK);
    }

     @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserException(UserException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), false);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
