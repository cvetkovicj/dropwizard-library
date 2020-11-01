package library.resources;

import library.core.Book;
import library.db.BookDAO;
import org.junit.*;
import org.junit.rules.ExpectedException;

import io.dropwizard.testing.junit.ResourceTestRule;
import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class BookResourceTest {
    private static final BookDAO bookDao = mock(BookDAO.class);

    private Book book = new Book(1, "111-45-763", "The Godfather", "Mario Puzo", "Crime novel", 442);
    private Book bookToInsert = new Book();
    private List<Book> books;

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(new BookResource(bookDao))
            .build();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        books = getBooksList();
    }

    @After
    public void tearDown() {
        reset(bookDao);
    }


    @Test
    public void getSixBooks() {
        when(bookDao.getSixBooks()).thenReturn(books);
        List<Book> response = resources.target("/books").request().get(new GenericType<List<Book>>() {
        });
        assertThat(response).containsAll(books);
    }

    @Test
    public void addBook() {
        when(bookDao.insertBook(book)).thenReturn(1);
        when(bookDao.getBookById(1)).thenReturn(book);

        Response response = resources.target("/books/add").request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(book, MediaType.APPLICATION_JSON_TYPE));

        Book responseBook = response.readEntity(Book.class);
        Assert.assertTrue(response.getStatusInfo() == Response.Status.OK);
        Assert.assertTrue(responseBook.getId() == 1);
        Assert.assertTrue(responseBook.getIsbn().equals("111-45-763"));
        Assert.assertTrue(responseBook.getTitle().equals("The Godfather"));
        Assert.assertTrue(responseBook.getListOfAuthors().equals("Mario Puzo"));
        Assert.assertTrue(responseBook.getGenre().equals("Crime novel"));
        Assert.assertTrue(responseBook.getNumberOfPages() == 442);
    }

    @Test
    public void addBookDuplicateIsbn() {
        given(bookDao.insertBook(book)).willAnswer( invocation -> { throw new SQLException(new PSQLException("Error", PSQLState.UNIQUE_VIOLATION)); });
        Response responseBook = resources.target("/books/add").request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(book, MediaType.APPLICATION_JSON_TYPE));

        Assert.assertTrue(responseBook.getStatusInfo()==Response.Status.CONFLICT);
    }

    @Test
    public void addBookServerError() {
        given(bookDao.insertBook(book)).willAnswer( invocation -> { throw new SQLException(new PSQLException("Error", PSQLState.UNKNOWN_STATE)); });
        Response responseBook = resources.target("/books/add").request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(book, MediaType.APPLICATION_JSON_TYPE));

        Assert.assertTrue(responseBook.getStatusInfo()==Response.Status.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void deleteBook() {
        when(bookDao.getBookById(1)).thenReturn(book);
        when(bookDao.deleteById(book.getId())).thenReturn(1);
        Response response = resources.target("/books/1").request(MediaType.APPLICATION_JSON_TYPE).delete();
        Assert.assertTrue(response.getStatusInfo() == Response.Status.NO_CONTENT);
    }

    @Test
    public void deleteBookNotFound() {
        when(bookDao.getBookById(1)).thenReturn(null);
        Response response = resources.target("/books/1").request(MediaType.APPLICATION_JSON_TYPE).delete();
        Assert.assertTrue(response.getStatusInfo() == Response.Status.NOT_FOUND);
    }

    @Test
    public void updateBook() {
        when(bookDao.getBookById(1)).thenReturn(book);
        Book bookWithNewValues = new Book(1, "111-45-763", "The Godfather", "Mario Puzo", "Crime novel", 500);
        Response response = resources.target("/books/1").request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.entity(bookWithNewValues, MediaType.APPLICATION_JSON_TYPE));

        Book responseBook = response.readEntity(Book.class);
        Assert.assertTrue(response.getStatusInfo() == Response.Status.OK);
        Assert.assertTrue(responseBook.getId() == 1);
        Assert.assertTrue(responseBook.getIsbn().equals("111-45-763"));
        Assert.assertTrue(responseBook.getTitle().equals("The Godfather"));
        Assert.assertTrue(responseBook.getListOfAuthors().equals("Mario Puzo"));
        Assert.assertTrue(responseBook.getGenre().equals("Crime novel"));
        Assert.assertTrue(responseBook.getNumberOfPages() == 500);
    }

    @Test
    public void updateBookNotExist() {
        when(bookDao.getBookById(1)).thenReturn(null);
        Book bookWithNewValues = new Book(1, "111-45-763", "The Godfather", "Mario Puzo", "Crime novel", 500);
        Response response = resources.target("/books/1").request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.entity(bookWithNewValues, MediaType.APPLICATION_JSON_TYPE));

        Assert.assertTrue(response.getStatusInfo() == Response.Status.NOT_FOUND);
    }

    @Test
    public void updateBookDuplicateIsbn() {
        when(bookDao.getBookById(1)).thenReturn(book);
        Book bookWithNewValues = new Book(1, "111-45-763", "The Godfather", "Mario Puzo", "Crime novel", 500);

        given(bookDao.update(bookWithNewValues)).willAnswer( invocation -> { throw new SQLException(new PSQLException("Error", PSQLState.UNIQUE_VIOLATION)); });

        Response response = resources.target("/books/1").request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.entity(bookWithNewValues, MediaType.APPLICATION_JSON_TYPE));

        Assert.assertTrue(response.getStatusInfo()==Response.Status.CONFLICT);
    }

    @Test
    public void getBoosByTitleOrAuthor() {
        when(bookDao.getBooksByTitleOrAuthor("%%")).thenReturn(books);
        List<Book> response = resources.target("/books/search")
                .queryParam("searchText", "")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<Book>>() {
                });
        Assert.assertTrue(response.size() == 5);
    }

    @Test
    public void getBoosByTitleOrAuthorEmpty() {
        when(bookDao.getBooksByTitleOrAuthor("%sometext%")).thenReturn(Collections.emptyList());
        List<Book> response = resources.target("/books/search")
                .queryParam("searchText", "sometext")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<List<Book>>() {
                });
        Assert.assertTrue(response.size() == 0);
    }

    public List<Book> getBooksList() {
        List<Book> books = new ArrayList<Book>();
        books.add(new Book(1, "111-45-763", "The Godfather", "Mario Puzo", "Crime novel", 442));
        books.add(new Book(2, "654-25-569", "The Sicilian", "Mario Puzo", "Novel", 350));
        books.add(new Book(3, "497-14-487", "The Fourth K", "Mario Puzo", "Novel", 414));
        books.add(new Book(4, "496-78-356", "Blindness", "Jose Saramago", "Psychological Fiction", 324));
        books.add(new Book(5, "343-88-489", "A bad Girl", "Mario Vargas Llosa", "Novel", 328));

        return books;
    }
}