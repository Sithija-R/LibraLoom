package dev.LibraLoom;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import dev.LibraLoom.Controllers.TransactionController;
import dev.LibraLoom.DTO.TransactionDTO;
import dev.LibraLoom.Exception.UserException;
import dev.LibraLoom.Models.Transaction;
import dev.LibraLoom.Services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    // Positive test case: Successful book borrowing
    @Test
    public void testBorrowBook_Success() throws Exception {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setIsbn("1234567890");
        transactionDTO.setUserId("user01");

        Transaction transaction = new Transaction();
        transaction.setTransactionId("trans01");

        when(transactionService.borrowBook(any(String.class), any(String.class))).thenReturn(transaction);

        mockMvc.perform(post("/api/transaction/borrow")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"isbn\": \"1234567890\", \"userId\": \"user01\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"transactionId\": \"trans01\"}"));
    }

    // Negative test case: UserException thrown when borrowing a book
    @Test
    public void testBorrowBook_UserException() throws Exception {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setIsbn("1234567890");
        transactionDTO.setUserId("user01");

        when(transactionService.borrowBook(any(String.class), any(String.class)))
                .thenThrow(new UserException("User not found"));

        mockMvc.perform(post("/api/transaction/borrow")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"isbn\": \"1234567890\", \"userId\": \"user01\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User not found"));
    }

    // Positive test case: Successful book return
    @Test
    public void testReturnBook_Success() throws Exception {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionId("trans01");
        transactionDTO.setIsbn("1234567890");
        transactionDTO.setUserId("user01");

        Transaction transaction = new Transaction();
        transaction.setTransactionId("trans01");

        when(transactionService.returnBook(any(String.class), any(String.class), any(String.class)))
                .thenReturn(transaction);

        mockMvc.perform(post("/api/transaction/return")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"transactionId\": \"trans01\", \"isbn\": \"1234567890\", \"userId\": \"user01\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"transactionId\": \"trans01\"}"));
    }

    // Negative test case: UserException thrown when returning a book
    @Test
    public void testReturnBook_UserException() throws Exception {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionId("trans01");
        transactionDTO.setIsbn("1234567890");
        transactionDTO.setUserId("user01");

        when(transactionService.returnBook(any(String.class), any(String.class), any(String.class)))
                .thenThrow(new UserException("Transaction not found"));

        mockMvc.perform(post("/api/transaction/return")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"transactionId\": \"trans01\", \"isbn\": \"1234567890\", \"userId\": \"user01\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Transaction not found"));
    }
}
