package br.com.rest;

import br.com.rest.data.vo.v1.PersonVO;
import br.com.rest.data.vo.v2.PersonVOV2;
import br.com.rest.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServices service;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonVO findById (@PathVariable(value = "id") Long id) throws Exception {
        return service.findById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonVO> findAll () throws Exception {
        return service.findAll();
    }

    @PostMapping(
            value = "/v1/createPerson",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public PersonVO create (@RequestBody PersonVO person) throws Exception {
        return service.createPerson(person);
    }

    @PostMapping(
            value = "/v2/createPerson",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public PersonVOV2 createV2 (@RequestBody PersonVOV2 person) throws Exception {
        return service.createPersonV2(person);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public PersonVO update (@RequestBody PersonVO person) throws Exception {
        return service.updatePerson(person);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete (@PathVariable (value = "id") Long id) throws Exception {
        service.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}
