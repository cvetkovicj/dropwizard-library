package library.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class BookTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        final Book book = new Book(1, "111-45-763", "The Godfather", "Mario Puzo", "Crime novel", 442);
        assertThat(MAPPER.writeValueAsString(book))
                .isEqualTo(fixture("fixtures/book.json"));
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        final Book book = new Book(1, "111-45-763", "The Godfather", "Mario Puzo", "Crime novel", 442);
        assertThat(MAPPER.readValue(fixture("fixtures/book.json"), Book.class))
                .isEqualTo(book);
    }
}