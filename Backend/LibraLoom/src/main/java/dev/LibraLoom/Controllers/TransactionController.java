package dev.LibraLoom.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    


    //get by uniqueId
    @GetMapping("/get/{uniqueId}")
    public ResponseEntity<Transaction> getByUniqueId(@PathVariable String uniqueId) throws UserException{
        return new ResponseEntity<>(transactionService.findByUniqueId(uniqueId),HttpStatus.OK);
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
}
