package br.com.rest.controllers;

import br.com.rest.data.vo.v1.BookVO;
import br.com.rest.services.BookServices;
import br.com.rest.util.MediaTypeUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book/v1")
@Tag(name = "Book", description = "Endpoint for managing books")
public class BookController {

    @Autowired
    private BookServices service;

    @GetMapping(
            value = "/{id}",
            produces = { MediaTypeUtil.APPLICATION_JSON, MediaTypeUtil.APPLICATION_XML, MediaTypeUtil.APPLICATION_YML }
    )
    @Operation(
            summary = "Finds a book recorded",
            description = "Finds a book recorded in the database",
            tags = { "Book" },
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = { @Content(schema = @Schema(implementation = BookVO.class)) }),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "NotFound", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            },
            method = "GET")
    public BookVO findById (@PathVariable(value = "id") Long id) throws Exception {
        return service.findById(id);
    }

    @GetMapping(
            produces = { MediaTypeUtil.APPLICATION_JSON, MediaTypeUtil.APPLICATION_XML, MediaTypeUtil.APPLICATION_YML }
    )
    @Operation(
            summary = "Find all people recorded",
            description = "Find all people recorded in the database",
            tags = { "Book" },
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BookVO.class))),
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "NotFound", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            },
            method = "GET")
    public List<BookVO> findAll () throws Exception {
        return service.findAll();
    }

    @PostMapping(
            value = "/createBook",
            consumes = { MediaTypeUtil.APPLICATION_JSON, MediaTypeUtil.APPLICATION_XML, MediaTypeUtil.APPLICATION_YML },
            produces = { MediaTypeUtil.APPLICATION_JSON, MediaTypeUtil.APPLICATION_XML, MediaTypeUtil.APPLICATION_YML}
    )
    @Operation(
            summary = "Create a book",
            description = "Create a book in the database by passing in a JSON, XML or YML book object",
            tags = { "Book" },
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = { @Content(schema = @Schema(implementation = BookVO.class)) }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "NotFound", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            },
            method = "POST")
    public BookVO create (@RequestBody BookVO book) throws Exception {
        return service.createBook(book);
    }

    @PutMapping(
            consumes = { MediaTypeUtil.APPLICATION_JSON, MediaTypeUtil.APPLICATION_XML, MediaTypeUtil.APPLICATION_YML },
            produces = { MediaTypeUtil.APPLICATION_JSON, MediaTypeUtil.APPLICATION_XML, MediaTypeUtil.APPLICATION_YML }
    )
    @Operation(
            summary = "Update a book",
            description = "Update a book in the database by passing in a JSON, XML or YML book object",
            tags = { "Book" },
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200", content = { @Content(schema = @Schema(implementation = BookVO.class)) }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "NotFound", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public BookVO update (@RequestBody BookVO book) throws Exception {
        return service.updateBook(book);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "Deletes a book",
            description = "Deletes a book in the database by passing your ID",
            tags = { "Book" },
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "NotFound", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            },
            method = "DELETE")
    public ResponseEntity<?> delete (@PathVariable (value = "id") Long id) throws Exception {
        service.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
