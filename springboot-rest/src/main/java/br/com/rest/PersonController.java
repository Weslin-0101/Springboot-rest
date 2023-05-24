package br.com.rest;

import br.com.rest.model.Person;
import br.com.rest.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServices service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Person findById (@PathVariable(value = "id") Long id) throws Exception {
        return service.findById(id);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> findAll () throws Exception {
        return service.findAll();
    }

    @RequestMapping(
            value = "/createPerson",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Person create (@RequestBody Person person) throws Exception {
        return service.createPerson(person);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Person update (@RequestBody Person person) throws Exception {
        return service.updatePerson(person);
    }

    @RequestMapping(
            method = RequestMethod.DELETE
    )
    public void delete (@PathVariable (value = "id") Long id) throws Exception {
        service.deletePerson(id);
    }
}
