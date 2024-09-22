package dev.LibraLoom;

import static org.mockito.ArgumentMatchers.any;
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
import dev.LibraLoom.Models.Transaction;
import dev.LibraLoom.Models.Users;
import dev.LibraLoom.Repositories.BookRepo;
import dev.LibraLoom.Repositories.LibraryRepo;
import dev.LibraLoom.Repositories.TransactionRepo;
import dev.LibraLoom.Repositories.UserRepo;
import dev.LibraLoom.Services.BookService;
import dev.LibraLoom.Services.TransactionService;
import dev.LibraLoom.Services.UserService;

import java.time.LocalDate;
import java.util.Optional;

public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private BookService bookService;

    @Mock
    private TransactionRepo transactionRepo;

    @Mock
    private LibraryRepo libraryRepo;

    @Mock
    private BookRepo bookRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private UserService userService;

    private Book book;
    private Users user;
    private Library library;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        book = new Book();
        book.setBookID("book01");
        book.setIsbn("1234567890");
        book.setAvailable(true);

        user = new Users();
        user.setUserId("user01");
        
        library = new Library();
        library.setId("library01");

        transaction = new Transaction();
        transaction.setTransactionId("transaction01");
        transaction.setBookId(book.getBookID());
        transaction.setUserId(user.getUserId());
        transaction.setBorrowDate(LocalDate.now());
        transaction.setDueDate(LocalDate.now().plusWeeks(2));
    }

    @Test
    public void testBorrowBook_Success() throws UserException {
        when(bookService.getByISBN("1234567890")).thenReturn(book);
        when(libraryRepo.findById("library01")).thenReturn(Optional.of(library));
        when(userService.findUserByID("user01")).thenReturn(user);

        Transaction borrowedTransaction = transactionService.borrowBook("1234567890", "user01");

        assertNotNull(borrowedTransaction);
        verify(bookRepo, times(1)).save(book);
        verify(userRepo, times(1)).save(user);
        verify(transactionRepo, times(1)).save(any(Transaction.class));
        verify(libraryRepo, times(1)).save(library);
    }

    @Test
public void testBorrowBook_BookNotAvailable() throws UserException {
    book.setAvailable(false);
    
   
    when(libraryRepo.findById("library01")).thenReturn(Optional.of(library));
    when(userService.findUserByID("user01")).thenReturn(user);
    when(bookService.getByISBN("1234567890")).thenReturn(book);

    assertThrows(UserException.class, () -> transactionService.borrowBook("1234567890", "user01"));
 
    verify(transactionRepo, never()).save(any(Transaction.class));
}


@Test
public void testReturnBook_Success() throws UserException {
    book.setAvailable(false);

    when(transactionRepo.findById("transaction01")).thenReturn(Optional.of(transaction));
    when(bookService.getByISBN("1234567890")).thenReturn(book);
    when(libraryRepo.findById("library01")).thenReturn(Optional.of(library));
    when(userService.findUserByID("user01")).thenReturn(user);

    Transaction returnedTransaction = transactionService.returnBook("transaction01", "1234567890", "user01");

    assertNotNull(returnedTransaction);
    assertTrue(returnedTransaction.isCompleted());
    verify(bookRepo, times(1)).save(book); 
    verify(userRepo, times(1)).save(user);
    verify(transactionRepo, times(1)).save(transaction); 
    verify(libraryRepo, times(1)).save(library);
}


@Test
public void testReturnBook_AlreadyReturned() throws UserException {

    book.setAvailable(true);

    transaction.setCompleted(true); 
    when(transactionRepo.findById("transaction01")).thenReturn(Optional.of(transaction));
    when(bookService.getByISBN("1234567890")).thenReturn(book);
    when(libraryRepo.findById("library01")).thenReturn(Optional.of(library)); 

    assertThrows(UserException.class, () -> transactionService.returnBook("transaction01", "1234567890", "user01"));

    verify(transactionRepo, never()).save(any(Transaction.class));
    verify(libraryRepo, never()).save(any(Library.class));
    verify(userRepo, never()).save(any(Users.class));
    verify(bookRepo, never()).save(any(Book.class));
}


}
