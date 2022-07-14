package com.vdev.library.rest.jpa.repository;

import com.vdev.library.rest.controller.model.Book;
import com.vdev.library.rest.jpa.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, String> {

    Optional<BookEntity> getBookEntityById(String id);

    @Query("SELECT b FROM BookEntity b JOIN FETCH b.author JOIN FETCH b.category WHERE b.id = ?1")
    Optional<BookEntity> getBookEntityByIdWithAuthorAndCategory(String id);

}
