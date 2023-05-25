package br.com.rest;

import br.com.rest.model.Person;
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
    public Person findById (@PathVariable(value = "id") Long id) throws Exception {
        return service.findById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> findAll () throws Exception {
        return service.findAll();
    }

    @PostMapping(
            value = "/createPerson",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Person create (@RequestBody Person person) throws Exception {
        return service.createPerson(person);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Person update (@RequestBody Person person) throws Exception {
        return service.updatePerson(person);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete (@PathVariable (value = "id") Long id) throws Exception {
        service.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}
