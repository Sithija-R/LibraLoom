package dev.LibraLoom.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.LibraLoom.Exception.UserException;
import dev.LibraLoom.Models.Book;
import dev.LibraLoom.Models.Library;
import dev.LibraLoom.Models.Transaction;
import dev.LibraLoom.Models.Users;
import dev.LibraLoom.Repositories.BookRepo;
import dev.LibraLoom.Repositories.LibraryRepo;
import dev.LibraLoom.Repositories.TransactionRepo;
import dev.LibraLoom.Repositories.UserRepo;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private BookService bookService;

    @Autowired
    private LibraryRepo libraryRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private UserRepo userRepo;

public List<Transaction> getAll(){
   return transactionRepo.findAll();
}
//find by uniqueId
public Transaction findByUniqueId(String key) throws UserException {
    Transaction transaction = transactionRepo.findByUniqueId(key);

    if (transaction == null) {
        throw new UserException("Transaction with ID " + key + " not found.");
    }

    return transaction;
}


    // borrow book
   public Transaction borrowBook(String isbn, String userId) throws UserException {
    Book book = bookService.getByISBN(isbn);
    Library library = libraryRepo.findById("library01")
            .orElseThrow(() -> new RuntimeException("Library not found"));
    Users user = userService.findUserByID(userId);

    if (!book.isAvailable()) {
        throw new UserException("The book is not available for borrowing.");
    }

    Transaction transaction = new Transaction();
    
    // Generate unique ID and check if it's already in the database
    String uniqueId = generateUniqueTransactionId();
    while (transactionRepo.existsById(uniqueId)) { // Check if ID already exists
        uniqueId = generateUniqueTransactionId(); // Regenerate if necessary
    }
    transaction.setUniqueId(uniqueId); // Set the unique ID to the transaction
    
    transaction.setBook(book);
    transaction.setUserId(userId);
    transaction.setBorrowDate(LocalDate.now());
    transaction.setDueDate(LocalDate.now().plusWeeks(2));
    transaction.setReturnDate(null);
    transaction.setLateDates(0);
    transaction.setCompleted(false);
    transactionRepo.save(transaction);

    library.getListofAvailableBooks().remove(book);
    library.getListofTransactions().add(transaction);

    libraryRepo.save(library);
    book.setAvailable(false);
    bookRepo.save(book);

    user.setIncompleteTransaction(transaction);
    user.setBorroweBook(book);
    userRepo.save(user);

    return transaction;
}

// Method to generate a unique transaction ID (TR + 4 random digits)
private String generateUniqueTransactionId() {
    Random random = new Random();
    int number = random.nextInt(9000) + 1000; // Generate a 4-digit number between 1000 and 9999
    return "TR" + number; // Concatenate with "TR"
}

    // Return a book
    public Transaction returnBook(String transactionId, String isbn, String userId) throws UserException {

        Transaction transaction = transactionRepo.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        Book book = bookService.getByISBN(isbn);
        Library library = libraryRepo.findById("library01")
                .orElseThrow(() -> new RuntimeException("Library not found"));

        Users user = userService.findUserByID(userId);

        if (book.isAvailable()) {
            throw new UserException("This book has already been returned.");
        }

        user.setBorroweBook(null);
        user.setIncompleteTransaction(null);
        userRepo.save(user);
        transaction.setCompleted(true);
        transaction.setReturnDate(LocalDate.now());

        long lateDays = transaction.getDueDate().until(LocalDate.now()).getDays();
        if (lateDays > 0) {
            transaction.setLateDates((int) lateDays);
        } else {
            transaction.setLateDates(0);
        }
        transactionRepo.save(transaction);

        book.setAvailable(true);
        bookRepo.save(book);
        library.getListofAvailableBooks().add(book);

        libraryRepo.save(library);

        return transaction;
    }

}
