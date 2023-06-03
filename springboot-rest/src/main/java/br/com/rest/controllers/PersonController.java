package br.com.rest.controllers;

import br.com.rest.data.vo.v1.PersonVO;
import br.com.rest.data.vo.v2.PersonVOV2;
import br.com.rest.services.PersonServices;
import br.com.rest.util.MediaTypeUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "Person", description = "Endpoint for managing people")
public class PersonController {

    @Autowired
    private PersonServices service;

    @GetMapping(
            value = "/{id}",
            produces = { MediaTypeUtil.APPLICATION_JSON, MediaTypeUtil.APPLICATION_XML, MediaTypeUtil.APPLICATION_YML }
    )
    @Operation(
            summary = "Finds a person recorded",
            description = "Finds a person recorded in the database",
            tags = { "Person" },
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = { @Content(schema = @Schema(implementation = PersonVO.class)) }),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "NotFound", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            },
            method = "GET")
    public PersonVO findById (@PathVariable(value = "id") Long id) throws Exception {
        return service.findById(id);
    }

    @GetMapping(
            produces = { MediaTypeUtil.APPLICATION_JSON, MediaTypeUtil.APPLICATION_XML, MediaTypeUtil.APPLICATION_YML }
    )
    @Operation(
            summary = "Find all people recorded with pagination",
            description = "Find all people recorded in the database",
            tags = { "Person" },
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))),
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "NotFound", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            },
            method = "GET")
    public ResponseEntity<PagedModel<EntityModel<PersonVO>>> findAll (
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) throws Exception {

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @PostMapping(
            value = "/createPerson",
            consumes = { MediaTypeUtil.APPLICATION_JSON, MediaTypeUtil.APPLICATION_XML, MediaTypeUtil.APPLICATION_YML },
            produces = { MediaTypeUtil.APPLICATION_JSON, MediaTypeUtil.APPLICATION_XML, MediaTypeUtil.APPLICATION_YML}
    )
    @Operation(
            summary = "Create a person",
            description = "Create a person in the database by passing in a JSON, XML or YML person object",
            tags = { "Person" },
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = { @Content(schema = @Schema(implementation = PersonVO.class)) }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "NotFound", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            },
            method = "POST")
    public PersonVO create (@RequestBody PersonVO person) throws Exception {
        return service.createPerson(person);
    }

    @PostMapping(
            value = "/v2/createPerson",
            consumes = { MediaTypeUtil.APPLICATION_JSON, MediaTypeUtil.APPLICATION_XML, MediaTypeUtil.APPLICATION_YML },
            produces = { MediaTypeUtil.APPLICATION_JSON, MediaTypeUtil.APPLICATION_XML, MediaTypeUtil.APPLICATION_YML }
    )
    @Operation(
            summary = "Create a person",
            description = "Create a person in the database by passing in a JSON, XML or YML person object",
            tags = { "Person" },
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = { @Content(schema = @Schema(implementation = PersonVO.class)) }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "NotFound", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            },
            method = "POST")
    public PersonVOV2 createV2 (@RequestBody PersonVOV2 person) throws Exception {
        return service.createPersonV2(person);
    }

    @PutMapping(
            consumes = { MediaTypeUtil.APPLICATION_JSON, MediaTypeUtil.APPLICATION_XML, MediaTypeUtil.APPLICATION_YML },
            produces = { MediaTypeUtil.APPLICATION_JSON, MediaTypeUtil.APPLICATION_XML, MediaTypeUtil.APPLICATION_YML }
    )
    @Operation(
            summary = "Update a person",
            description = "Update a person in the database by passing in a JSON, XML or YML person object",
            tags = { "Person" },
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200", content = { @Content(schema = @Schema(implementation = PersonVO.class)) }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "NotFound", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public PersonVO update (@RequestBody PersonVO person) throws Exception {
        return service.updatePerson(person);
    }

    @PatchMapping(
            value = "/{id}",
            produces = { MediaTypeUtil.APPLICATION_JSON, MediaTypeUtil.APPLICATION_XML, MediaTypeUtil.APPLICATION_YML }
    )
    @Operation(
            summary = "Disable a specific person by ID",
            description = "Disable a specific person by ID in the database",
            tags = { "Person" },
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = { @Content(schema = @Schema(implementation = PersonVO.class)) }),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "NotFound", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            },
            method = "GET")
    public PersonVO disablePerson (@PathVariable(value = "id") Long id) throws Exception {
        return service.disablePerson(id);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "Deletes a person",
            description = "Deletes a person in the database by passing your ID",
            tags = { "Person" },
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "NotFound", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            },
            method = "DELETE")
    public ResponseEntity<?> delete (@PathVariable (value = "id") Long id) throws Exception {
        service.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}
