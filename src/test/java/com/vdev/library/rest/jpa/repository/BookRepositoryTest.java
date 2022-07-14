package com.vdev.library.rest.jpa.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.vdev.library.rest.TestConstants.BOOK_ID_INVALID;
import static com.vdev.library.rest.TestConstants.STEPHEN_KING_THE_INSTITUTE_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = {"/db/schema.sql", "/db/data.sql"})
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void GetBookEntityById_BookExist_RecordReturned() {
        final var optionalBook = bookRepository.getBookEntityById(STEPHEN_KING_THE_INSTITUTE_ID);
        assertTrue(optionalBook.isPresent());
    }

    @Test
    void GetBookEntityById_BookIdNull_NoRecordReturned() {
        final var optionalBook = bookRepository.getBookEntityById(null);
        assertFalse(optionalBook.isPresent());
    }

    @Test
    void GetBookEntityById_BookIdInvalid_NoRecordReturned() {
        final var optionalBook = bookRepository.getBookEntityById(BOOK_ID_INVALID);
        assertFalse(optionalBook.isPresent());
    }

    @Test
    void GetBookEntityByIdWithAuthorAndCategory_BookExist_RecordReturned() {
        final var optionalBook = bookRepository.getBookEntityByIdWithAuthorAndCategory(STEPHEN_KING_THE_INSTITUTE_ID);
        assertTrue(optionalBook.isPresent());

        final var bookEntity = optionalBook.get();
        assertEquals("The Institute", bookEntity.getTitle());
        assertEquals("Stephen King", bookEntity.getAuthor().getName());
        assertEquals("Horror", bookEntity.getCategory().getTitle());
    }

    @Test
    void GetBookEntityByIdWithAuthorAndCategory_BookIdNull_NoRecordReturned() {
        final var optionalBook = bookRepository.getBookEntityByIdWithAuthorAndCategory(null);
        assertFalse(optionalBook.isPresent());
    }

    @Test
    void GetBookEntityByIdWithAuthorAndCategory_BookIdInvalid_NoRecordReturned() {
        final var optionalBook = bookRepository.getBookEntityByIdWithAuthorAndCategory(BOOK_ID_INVALID);
        assertFalse(optionalBook.isPresent());
    }

}
