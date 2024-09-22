package dev.LibraLoom;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.LibraLoom.Exception.UserException;
import dev.LibraLoom.Models.Book;

import dev.LibraLoom.Services.BookService;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    // Positive: Test get all books
    @Test
    public void testGetAllBooks() throws Exception {
        Book book1 = new Book();
        book1.setIsbn("123");
        book1.setTitle("Spring in Action");
        book1.setAuthor("Craig Walls");
        book1.setPublicationYear(2018);

        Book book2 = new Book();
        book2.setIsbn("456");
        book2.setTitle("Java in Action");
        book2.setAuthor("Brian Goetz");
        book2.setPublicationYear(2006);

        List<Book> books = Arrays.asList(book1, book2);

        when(bookService.getAll()).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/book/get/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].isbn").value("123"))
                .andExpect(jsonPath("$[0].title").value("Spring in Action"))
                .andExpect(jsonPath("$[0].author").value("Craig Walls"))
                .andExpect(jsonPath("$[0].publicationYear").value(2018))
                .andExpect(jsonPath("$[1].isbn").value("456"))
                .andExpect(jsonPath("$[1].title").value("Java in Action"));
    }

    // Negative: Test get all books when service throws an exception
    @Test
    public void testGetAllBooksThrowsException() throws Exception {
        when(bookService.getAll()).thenThrow(new UserException("Failed to fetch books"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/book/get/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Failed to fetch books"));
    }

    // Positive: Test get book by valid ISBN
    @Test
    public void testGetBookByISBN() throws Exception {
        Book book = new Book();
        book.setIsbn("123");
        book.setTitle("Spring in Action");
        book.setAuthor("Craig Walls");
        book.setPublicationYear(2018);

        when(bookService.getByISBN("123")).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/book/get/123")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("123"))
                .andExpect(jsonPath("$.title").value("Spring in Action"))
                .andExpect(jsonPath("$.author").value("Craig Walls"))
                .andExpect(jsonPath("$.publicationYear").value(2018));
    }

    // Negative: Test get book by invalid ISBN
    @Test
    public void testGetBookByInvalidISBN() throws Exception {
        when(bookService.getByISBN("999")).thenThrow(new UserException("Book not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/book/get/999")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Book not found"));
    }

    // Positive: Test add book
    @Test
    public void testAddBook() throws Exception {
        Book book = new Book();
        book.setIsbn("123");
        book.setTitle("Spring in Action");
        book.setAuthor("Craig Walls");
        book.setPublicationYear(2018);

        when(bookService.addBook(Mockito.any(Book.class))).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/book/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isbn").value("123"))
                .andExpect(jsonPath("$.title").value("Spring in Action"))
                .andExpect(jsonPath("$.author").value("Craig Walls"))
                .andExpect(jsonPath("$.publicationYear").value(2018));
    }

    // Negative: Test add invalid book (missing fields)
    @Test
    public void testAddInvalidBook() throws Exception {
        Book invalidBook = new Book(); // Missing ISBN, title, author, and publicationYear

        mockMvc.perform(MockMvcRequestBuilders.post("/api/book/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidBook)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid book data"));
    }

    // Positive: Test edit book
    @Test
    public void testEditBook() throws Exception {
        Book updatedBook = new Book();
        updatedBook.setIsbn("123");
        updatedBook.setTitle("Updated Spring in Action");
        updatedBook.setAuthor("Craig Walls");
        updatedBook.setPublicationYear(2019);

        when(bookService.editBook(Mockito.any(Book.class))).thenReturn(updatedBook);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/book/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.isbn").value("123"))
                .andExpect(jsonPath("$.title").value("Updated Spring in Action"))
                .andExpect(jsonPath("$.author").value("Craig Walls"))
                .andExpect(jsonPath("$.publicationYear").value(2019));
    }

    // Negative: Test edit non-existent book
    @Test
    public void testEditNonExistentBook() throws Exception {
        Book nonExistentBook = new Book();
        nonExistentBook.setIsbn("999"); // This book does not exist

        when(bookService.editBook(Mockito.any(Book.class))).thenThrow(new UserException("Book not found"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/book/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nonExistentBook)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Book not found"));
    }

    // Positive: Test delete book
    @Test
    public void testDeleteBook() throws Exception {
        doNothing().when(bookService).deleteBook("123");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/book/delete/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Successfully Deleted !"))
                .andExpect(jsonPath("$.success").value(true));
    }

    // Negative: Test delete non-existent book
    @Test
    public void testDeleteNonExistentBook() throws Exception {
        doThrow(new UserException("Book not found")).when(bookService).deleteBook("999");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/book/delete/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Book not found"));
    }
}
