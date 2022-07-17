package com.vdev.library.rest.controller;

import com.vdev.library.rest.controller.model.Book;
import com.vdev.library.rest.controller.model.RestResponse;
import com.vdev.library.rest.controller.reponses.RestResponseBook;
import com.vdev.library.rest.exception.NotFoundException;
import com.vdev.library.rest.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/books")
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Get a book by id", description = "Get a book by id", tags = {"Book"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RestResponseBook.class))
            }),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)
    })
    @GetMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<Book>> getBookById(
            @PathVariable(name = "bookId") final String bookId) {

        final var optionalBook = bookService.getBookById(bookId);
        if (optionalBook.isPresent()) {
            final var restResponse = new RestResponse<Book>();
            restResponse.setPayload(optionalBook.get());
            return ResponseEntity.ok().body(restResponse);
        } else {
            throw new NotFoundException();
        }

    }

}
