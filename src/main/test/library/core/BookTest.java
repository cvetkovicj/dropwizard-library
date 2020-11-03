package library.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class BookTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    private static Book book;

    @BeforeClass
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        book = new Book(1, "111-45-763", "The Godfather", "Mario Puzo", "Crime novel", 442);
    }

    @AfterClass
    public static void close() {
        validatorFactory.close();
    }

    @Test
    public void BookShouldHaveNoViolations() {

        Set<ConstraintViolation<Book>> violations
                = validator.validate(book);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void BookISBNNotValidFormat() {
        Book bookInvalidISBN = new Book(1, "1112-4-763", "The Godfather", "Mario Puzo", "Crime novel", 442);
        Set<ConstraintViolation<Book>> violations
                = validator.validate(bookInvalidISBN);
        assertEquals(violations.size(), 1);

        ConstraintViolation<Book> violation
                = violations.iterator().next();
        assertEquals("has to contain only digits and to be in format DDD-DD-DDD",
                violation.getMessage());
        assertEquals("isbn", violation.getPropertyPath().toString());
        assertEquals("1112-4-763", violation.getInvalidValue());
    }

    @Test
    public void serializesToJSON() throws Exception {
        assertThat(MAPPER.writeValueAsString(book))
                .isEqualTo(fixture("fixtures/book.json"));
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        assertThat(MAPPER.readValue(fixture("fixtures/book.json"), Book.class))
                .isEqualTo(book);
    }
}