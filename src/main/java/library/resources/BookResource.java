package library.resources;

import library.core.Book;
import library.db.BookDAO;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("books")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class BookResource {
    BookDAO bookDAO;
    private final static Logger logger = LoggerFactory.getLogger(BookResource.class);

    public BookResource(BookDAO bookDao) {
        bookDAO = bookDao;
    }

    /**
     * This method fetch six records from table book without any condition
     * @return list of six books descending order by id
     */
    @GET
    public List<Book> getSixBooks() {
        return bookDAO.getSixBooks();
    }


    /**
     * This method returns all books that title or list of authors match the criteria
     * @param searchText criteria that should be matched to the author or title of the books
     * @return list of all matching books
     */
    @GET
    @Path("/search")
    public List<Book> getBoosByTitleOrAuthor(@QueryParam("searchText") String searchText) {
        List<Book> books = bookDAO.getBooksByTitleOrAuthor("%" + searchText + "%");
        return books;
    }


    /**
     * This method creates the new database record in table books
     * @param book object that should be inserted in database
     * @return the previously inserted object from the database
     */
    @POST
    @Path("/add")
    public Book addBook(@Valid Book book) {

        try {
            int bookId = bookDAO.insertBook(book);
            return bookDAO.getBookById(bookId);
        } catch (Exception e) {
            if (e.getCause() instanceof PSQLException) {
                if (((PSQLException)e.getCause()).getSQLState().equals("23505")) {
                    throw new WebApplicationException("Duplicate constraint error.", Response.Status.CONFLICT);
                }
            }
            throw new WebApplicationException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * This method deletes record in table books by passed id
     * @param id identifier of book that should be deleted
     */
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Integer id) {
        Book bookToUpdate = bookDAO.getBookById(id);
        if(bookToUpdate == null)
        {
            logger.error("Book with id " + id + " does not exist");
            throw new WebApplicationException("Book does not exist", Response.Status.NOT_FOUND);
        }
        else {
            bookDAO.deleteById(id);
        }
    }


    /**
     * This method updates all properties of
     * @param id identifier of book that should be updated
     * @param book book object with new values to be used for update
     * @return the new value of book object, after update
     */
    @PUT
    @Path("/{id}")
    public Book update(@PathParam("id") Integer id, @Valid Book book) {
        Book bookToUpdate = bookDAO.getBookById(id);
        if(bookToUpdate == null)
        {
            logger.error("Book with id " + id + " does not exist");
            throw new WebApplicationException("Book does not exist", Response.Status.NOT_FOUND);
        }
        else {
            logger.info("Book id " + id + " update to new values: " + bookToUpdate.toString());
            Book updateBook = new Book(id, book.getIsbn(), book.getTitle(), book.getListOfAuthors(), book.getGenre(), book.getNumberOfPages());
            try
            {
                bookDAO.update(updateBook);
                return updateBook;
            } catch (Exception e) {
                if (e.getCause() instanceof PSQLException) {
                    if (((PSQLException)e.getCause()).getSQLState().equals("23505")) {
                        throw new WebApplicationException("Duplicate constraint error.", Response.Status.CONFLICT);
                    }
                }
                throw new WebApplicationException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
