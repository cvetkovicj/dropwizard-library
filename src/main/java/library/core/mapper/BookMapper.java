package library.core.mapper;

import library.core.Book;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements ResultSetMapper<Book> {

    @Override
    public Book map(int index, ResultSet r, StatementContext ctx) throws SQLException {

        return new Book(r.getInt("id"), r.getString("isbn"), r.getString("title"), r.getString("list_of_authors"),
                r.getString("genre"), r.getInt("number_of_pages"));
    }

}