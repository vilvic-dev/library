package com.vdev.library.rest.service;

import com.vdev.library.rest.controller.model.Book;
import com.vdev.library.rest.jpa.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Optional<Book> getBookById(final String id) {

        final var optionalBook = bookRepository.getBookEntityByIdWithAuthorAndCategory(id);
        if(optionalBook.isPresent()) {
            final var book = optionalBook.get();
            return Optional.of(Book
                    .builder()
                    .title(book.getTitle())
                    .description(book.getDescription())
                    .isbn(book.getIsbn())
                    .author(book.getAuthor().getName())
                    .category(book.getCategory().getTitle())
                    .build());
        }

        return Optional.empty();
    }

}
