package br.com.rest.services;

import br.com.rest.controllers.PersonController;
import br.com.rest.data.vo.v1.PersonVO;
import br.com.rest.data.vo.v2.PersonVOV2;
import br.com.rest.mapper.ModelMapperAdapter;
import br.com.rest.mapper.custom.PersonMapper;
import br.com.rest.model.Person;
import br.com.rest.repositories.PersonRepository;
import expections.RequiredObjectsIsNullException;
import expections.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonMapper mapper;

    public PersonVO findById(Long id) throws Exception {
        logger.info("Finding one person!");

        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("No records found for this ID"));

        PersonVO vo = ModelMapperAdapter.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());

        return vo;
    }

    public List<PersonVO> findAll() {
        logger.info("Finding all people");
        var persons = ModelMapperAdapter.parseListObject(personRepository.findAll(), PersonVO.class);
        persons
                .forEach(p -> {
                    try {
                        p.add(linkTo(methodOn(PersonController.class).findById(p.getId())).withSelfRel());
                    } catch (Exception e) {
                        throw new ResourceNotFoundException("No records found");
                    }
                });
        return persons;
    }

    public PersonVO createPerson(PersonVO person) throws Exception {
        if (person == null) throw new RequiredObjectsIsNullException();

        logger.info("Creating a person");
        var entity = ModelMapperAdapter.parseObject(person, Person.class);
        var vo = ModelMapperAdapter.parseObject(personRepository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getId())).withSelfRel());

        return vo;
    }

    public PersonVOV2 createPersonV2(PersonVOV2 person) {
        logger.info("Creating a person");
        var entity = mapper.convertVOToEntity(person);

        return mapper.convertEntityToVO(personRepository.save(entity));
    }

    public PersonVO updatePerson(PersonVO person) throws Exception {
        if (person == null) throw new RequiredObjectsIsNullException();

        logger.info("Creating a person");
        var entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo = ModelMapperAdapter.parseObject(personRepository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getId())).withSelfRel());

        return vo;
    }

    @Transactional
    public PersonVO disablePerson(Long id) throws Exception {
        logger.info("Disabling one person!");

        personRepository.disablePerson(id);
        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("No records found for this ID"));

        PersonVO vo = ModelMapperAdapter.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());

        return vo;
    }

    public void deletePerson(Long id) {
        logger.info("Deleting a person");
        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        personRepository.delete(entity);
    }
}
