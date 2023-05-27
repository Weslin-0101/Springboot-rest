package br.com.rest;

import br.com.rest.data.vo.v1.PersonVO;
import br.com.rest.data.vo.v2.PersonVOV2;
import br.com.rest.services.PersonServices;
import br.com.rest.util.MediaTypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

    @Autowired
    private PersonServices service;

    @GetMapping(
            value = "/{id}",
            produces = { MediaTypeUtil.APPLICATION_JSON, MediaTypeUtil.APPLICATION_XML, MediaTypeUtil.APPLICATION_YML }
    )
    public PersonVO findById (@PathVariable(value = "id") Long id) throws Exception {
        return service.findById(id);
    }

    @GetMapping(
            produces = { MediaTypeUtil.APPLICATION_JSON, MediaTypeUtil.APPLICATION_XML, MediaTypeUtil.APPLICATION_YML }
    )
    public List<PersonVO> findAll () throws Exception {
        return service.findAll();
    }

    @PostMapping(
            value = "/createPerson",
            consumes = { MediaTypeUtil.APPLICATION_JSON, MediaTypeUtil.APPLICATION_XML, MediaTypeUtil.APPLICATION_YML },
            produces = { MediaTypeUtil.APPLICATION_JSON, MediaTypeUtil.APPLICATION_XML, MediaTypeUtil.APPLICATION_YML}
    )
    public PersonVO create (@RequestBody PersonVO person) throws Exception {
        return service.createPerson(person);
    }

    @PostMapping(
            value = "/v2/createPerson",
            consumes = { MediaTypeUtil.APPLICATION_JSON, MediaTypeUtil.APPLICATION_XML, MediaTypeUtil.APPLICATION_YML },
            produces = { MediaTypeUtil.APPLICATION_JSON, MediaTypeUtil.APPLICATION_XML, MediaTypeUtil.APPLICATION_YML }
    )
    public PersonVOV2 createV2 (@RequestBody PersonVOV2 person) throws Exception {
        return service.createPersonV2(person);
    }

    @PostMapping(
            consumes = { MediaTypeUtil.APPLICATION_JSON, MediaTypeUtil.APPLICATION_XML, MediaTypeUtil.APPLICATION_YML },
            produces = { MediaTypeUtil.APPLICATION_JSON, MediaTypeUtil.APPLICATION_XML, MediaTypeUtil.APPLICATION_YML }
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
