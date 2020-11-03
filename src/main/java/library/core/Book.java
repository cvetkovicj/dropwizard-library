package library.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import library.core.util.ValidISBNFormat;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;


public class Book {

    private Integer id;

    @JsonProperty
    @NotNull
    @Valid
    @Column(unique=true)
    @ValidISBNFormat
    public String isbn;

    @JsonProperty
    @NotNull
    @Valid
    @Size(max = 50)
    private String title;

    @JsonProperty
    @NotNull
    @Valid
    @Size(max = 50)
    private String listOfAuthors;

    @JsonProperty
    @NotNull
    @Valid
    @Size(max = 30)
    private String genre;

    @JsonProperty
    @NotNull
    @Valid
    @Min(0)
    @Max(5000)
    private Integer numberOfPages;



    public Book() {
    }

    public Book(Integer id, String isbn, String title, String listOfAuthors, String genre, Integer numberOfPages) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.listOfAuthors = listOfAuthors;
        this.genre = genre;
        this.numberOfPages = numberOfPages;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getListOfAuthors() {
        return listOfAuthors;
    }

    public void setListOfAuthors(String listOfAuthors) {
        this.listOfAuthors = listOfAuthors;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }

        final Book that = (Book) o;

        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.isbn, that.isbn) &&
                Objects.equals(this.title, that.title) &&
                Objects.equals(this.listOfAuthors, that.listOfAuthors) &&
                Objects.equals(this.genre, that.genre) &&
                Objects.equals(this.numberOfPages, that.numberOfPages);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", listOfAuthors='" + listOfAuthors + '\'' +
                ", genre='" + genre + '\'' +
                ", numberOfPages=" + numberOfPages +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isbn, title, listOfAuthors, genre, numberOfPages);
    }
}
