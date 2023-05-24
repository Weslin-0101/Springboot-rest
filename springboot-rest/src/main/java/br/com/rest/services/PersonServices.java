package br.com.rest.services;

import br.com.rest.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private static AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonServices.class.getName());


    private Person mockPerson(int i) {

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Person name " + i);
        person.setLastnName("Last name " + i);
        person.setAddress("Some address in brazil " + i);
        person.setGender("Male");
        return person;
    }
    public Person findById(String id) {
        logger.info("Finding one person!");
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Wesley");
        person.setLastnName("Lira");
        person.setAddress("Brasília - DF");
        person.setGender("Male");
        return person;
    }

    public List<Person> findAll() {
        logger.info("Finding all people");

        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Person person = mockPerson(i);
            persons.add(person);
        }

        return persons;
    }

    public Person createPerson(Person person) {
        logger.info("Creating a person");
        return person;
    }

    public Person updatePerson(Person person) {
        logger.info("Creating a person");
        return person;
    }

    public void deletePerson(String id) {
        logger.info("Deleting a person");
    }
}
