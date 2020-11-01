
DROP TABLE IF EXISTS books;

CREATE TABLE books(
id SERIAL PRIMARY KEY,
isbn VARCHAR (255) NOT NULL,
title VARCHAR (255) NOT NULL,
list_of_authors VARCHAR (255) NOT NULL,
genre VARCHAR (255) NOT NULL,
number_of_pages INT NOT NULL,
UNIQUE(isbn)
);

INSERT INTO
    BOOKS (isbn, title, list_of_authors, genre, number_of_pages)
VALUES
    ('111-45-763', 'The Godfather', 'Mario Puzo', 'Crime novel', 442),
    ('496-78-498', 'The Double', 'Jose Saramago', 'Fiction', 320),
    ('654-25-569', 'The Sicilian', 'Mario Puzo', 'Novel', 350),
    ('591-78-846', '1001 albums you must hear before you die', 'Robert Dimery', 'Reference work', 960),
    ('497-14-487', 'The Fourth K', 'Mario Puzo', 'Novel', 414),
    ('496-65-356', 'Blindness', 'Jose Saramago', 'Psychological Fiction', 324),
    ('343-88-489', 'A bad Girl', 'Mario Vargas Llosa', 'Novel', 328),
    ('564-78-567', 'Seeing', 'Jose Saramago', 'Fiction', 328),
    ('275-46-236', 'Fierce times', 'Mario Vargas Llosa', 'Drama', 304),
    ('256-66-565', 'Sabers and Utopias', 'Mario Vargas Llosa', 'Essays', 464);