package br.com.rest.services;

import br.com.rest.data.vo.v1.PersonVO;
import br.com.rest.data.vo.v2.PersonVOV2;
import br.com.rest.mapper.ModelMapperAdapter;
import br.com.rest.model.Person;
import br.com.rest.repositories.PersonRepository;
import expections.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository personRepository;

    public PersonVO findById(Long id) {
        logger.info("Finding one person!");

        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("No records found for this ID"));

        return ModelMapperAdapter.parseObject(entity, PersonVO.class);
    }

    public List<PersonVO> findAll() {
        logger.info("Finding all people");
        return ModelMapperAdapter.parseListObject(personRepository.findAll(), PersonVO.class);
    }

    public PersonVO createPerson(PersonVO person) {
        logger.info("Creating a person");

        var entity = ModelMapperAdapter.parseObject(person, Person.class);

        return ModelMapperAdapter.parseObject(personRepository.save(entity), PersonVO.class);
    }

    public PersonVOV2 createPersonV2(PersonVOV2 person) {
        logger.info("Creating a person");

        var entity = ModelMapperAdapter.parseObject(person, Person.class);

        return ModelMapperAdapter.parseObject(personRepository.save(entity), PersonVOV2.class);
    }

    public PersonVO updatePerson(PersonVO person) {
        logger.info("Creating a person");

        var entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return ModelMapperAdapter.parseObject(personRepository.save(entity), PersonVO.class);
    }

    public void deletePerson(Long id) {
        logger.info("Deleting a person");

        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        personRepository.delete(entity);
    }
}
