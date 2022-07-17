package com.vdev.library.rest.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Book {

    @Schema(description = "Book title", example = "The institute", required = true)
    private String title;

    @Schema(description = "Description of book", example = "NO ONE HAS EVER ESCAPED FROM THE INSTITUTE.")
    private String description;

    @Schema(description = "ISBN number", example = "9781529355390", required = true)
    private String isbn;

    @Schema(description = "Author", example = "Stephen King")
    private String author;

    @Schema(description = "Category", example = "Horror")
    private String category;

}
