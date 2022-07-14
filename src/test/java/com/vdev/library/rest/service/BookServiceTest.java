package com.vdev.library.rest.service;

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
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    void GetBookEntityByIdWithAuthorAndCategory_BookExist_RecordReturned() {
        final var optionalBook = bookService.getBookById(STEPHEN_KING_THE_INSTITUTE_ID);
        assertTrue(optionalBook.isPresent());

        final var book = optionalBook.get();
        assertEquals("The Institute", book.getTitle());
        assertEquals("Stephen King", book.getAuthor());
        assertEquals("Horror", book.getCategory());
    }

    @Test
    void GetBookEntityByIdWithAuthorAndCategory_BookIdInvalid_NoRecordReturned() {
        final var optionalBook = bookService.getBookById(BOOK_ID_INVALID);
        assertFalse(optionalBook.isPresent());
    }

}
