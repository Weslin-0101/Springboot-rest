package br.com.rest.integrationTest.repositories;

import br.com.rest.configs.TestConfigs;
import br.com.rest.integrationTest.vo.PersonTestVO;
import br.com.rest.integrationTest.vo.wrappers.WrapperPersonVO;
import br.com.rest.model.Person;
import br.com.rest.repositories.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;

    private static Person person;

    @BeforeAll
    public static void setup() {
        person = new Person();
    }

    @Test
    @Order(1)
    public void testFindByName() throws JsonMappingException, JsonProcessingException {
        Pageable pageable = PageRequest.of(0, 6, Sort.by(Sort.Direction.ASC, "fistName"));
        person = personRepository.findPersonByName("wes", pageable).getContent().get(0);

        assertNotNull(person);
        assertNotNull(person.getId());
        assertNotNull(person.getFirstName());
        assertNotNull(person.getLastName());
        assertNotNull(person.getAddress());
        assertNotNull(person.getGender());

        assertTrue(person.getEnabled());

        assertEquals("Weslin", person.getFirstName());
        assertEquals("Cria", person.getLastName());
        assertEquals("Brasília - DF", person.getAddress());
        assertEquals("Male", person.getGender());
    }
}
