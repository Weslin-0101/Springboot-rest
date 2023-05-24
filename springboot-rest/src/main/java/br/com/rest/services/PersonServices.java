package br.com.rest.services;

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

    public Person findById(Long id) {
        logger.info("Finding one person!");

        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("No records found for this ID"));
    }

    public List<Person> findAll() {
        logger.info("Finding all people");
        return personRepository.findAll();
    }

    public Person createPerson(Person person) {
        logger.info("Creating a person");
        return personRepository.save(person);
    }

    public Person updatePerson(Person person) {
        logger.info("Creating a person");

        Person entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return personRepository.save(person);
    }

    public void deletePerson(Long id) {
        logger.info("Deleting a person");

        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        personRepository.delete(entity);
    }
}
