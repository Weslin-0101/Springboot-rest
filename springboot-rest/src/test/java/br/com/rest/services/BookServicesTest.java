package br.com.rest.services;

import br.com.rest.data.vo.v1.BookVO;
import br.com.rest.data.vo.v1.BookVO;
import br.com.rest.mocks.MockBook;
import br.com.rest.model.Book;
import br.com.rest.model.Book;
import br.com.rest.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BookServicesTest {

    MockBook input;

    @InjectMocks
    private BookServices services;

    @Mock
    BookRepository repository;

    @BeforeEach
    void setUp() throws Exception {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() throws Exception {
        Book mockBook = input.mockEntity(1);
        mockBook.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(mockBook));
        var result = services.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertNotNull(new Date());
        assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("Some Author1", result.getAuthor());
        assertEquals(25D, result.getPrice());
        assertEquals("Some Title1", result.getTitle());
    }

    @Test
    void findAll() {
        List<Book> mockListBook = input.mockEntityList();

        when(repository.findAll()).thenReturn(mockListBook);
        var books = services.findAll();

        assertNotNull(books);
        assertEquals(5, books.size());

        var book1 = books.get(1);
        assertNotNull(book1);
        assertNotNull(book1.getId());
        assertNotNull(book1.getLinks());
        assertNotNull(book1.getLaunchDate());
        assertTrue(book1.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("Some Author1", book1.getAuthor());
        assertEquals(25D, book1.getPrice());
        assertEquals("Some Title1", book1.getTitle());

        var book4 = books.get(4);
        assertNotNull(book4);
        assertNotNull(book4.getId());
        assertNotNull(book4.getLinks());
        assertNotNull(book4.getLaunchDate());
        assertTrue(book4.toString().contains("links: [</api/book/v1/4>;rel=\"self\"]"));
        assertEquals("Some Author4", book4.getAuthor());
        assertEquals(25D, book4.getPrice());
        assertEquals("Some Title4", book4.getTitle());
    }

    @Test
    void createBook() throws Exception {
//        Book mockBook = input.mockEntity(1);
//        Book persisted = mockBook;
//        persisted.setId(1L);
//
//        BookVO bookVO = input.mockVO(1);
//        bookVO.setId(1L);
//        when(repository.save(mockBook)).thenReturn(persisted);
//
//        var result = services.createBook(bookVO);
//        assertNotNull(result);
//        assertNotNull(result.getId());
//        assertNotNull(result.getLinks());
//        assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
//        assertEquals("Some Author4", result.getAuthor());
//        assertEquals(25D, result.getPrice());
//        assertEquals("Some Title4", result.getTitle());
    }

    @Test
    void updateBook() throws Exception {
        Book mockBook = input.mockEntity(1);
        mockBook.setId(1L);

        Book persisted = mockBook;
        persisted.setId(1L);

        BookVO bookVO = input.mockVO(1);
        bookVO.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(mockBook));
        when(repository.save(mockBook)).thenReturn(mockBook);

        var result = services.updateBook(bookVO);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("Some Author1", result.getAuthor());
        assertEquals(25D, result.getPrice());
        assertEquals("Some Title1", result.getTitle());
    }

    @Test
    void deleteBook() throws Exception {
        Book mockBook = input.mockEntity(1);
        mockBook.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(mockBook));

        services.deleteBook(1L);
    }
}