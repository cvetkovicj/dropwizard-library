package library.db;

import library.core.Book;
import library.core.mapper.BookMapper;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(BookMapper.class)
public interface BookDAO {

    @SqlQuery("select * from books order by id desc limit 6")
    List<Book> getSixBooks();


    @SqlQuery("select * from books where upper(title) like upper(:searchText) or upper(list_of_authors) like upper(:searchText) order by id desc")
    List<Book> getBooksByTitleOrAuthor(@Bind("searchText") String searchText);

    @SqlUpdate("insert into books (isbn, title, list_of_authors, number_of_pages, genre) "
            + "values (:isbn, :title, :listOfAuthors, :numberOfPages, :genre)")
    @GetGeneratedKeys
    int insertBook(@BindBean Book book);

    @SqlQuery("select * from books where id=:id")
    Book getBookById(@Bind("id") int id);

    @SqlUpdate("delete from books where id = :id")
    int deleteById(@Bind("id") int id);

    @SqlUpdate("update books set isbn = :isbn, title = :title, list_of_authors = :listOfAuthors, number_of_pages = :numberOfPages, genre = :genre where ID = :id")
    int update(@BindBean Book team);
}
