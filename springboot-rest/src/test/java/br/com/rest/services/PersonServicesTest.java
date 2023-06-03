package br.com.rest.services;

import br.com.rest.data.vo.v1.PersonVO;
import br.com.rest.mocks.MockPerson;
import br.com.rest.model.Person;
import br.com.rest.repositories.PersonRepository;
import expections.RequiredObjectsIsNullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    MockPerson input;

    @InjectMocks
    private PersonServices services;

    @Mock
    PersonRepository repository;

    @BeforeEach
    void setUpMocks() throws Exception {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() throws Exception {
        Person mockPerson = input.mockEntity(1);
        mockPerson.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(mockPerson));
        var result = services.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    /*
    @Test
    void findAll() {
        List<Person> mockListPerson = input.mockEntityList();

        when(repository.findAll()).thenReturn(mockListPerson);
        var peopleList = services.findAll();

        assertNotNull(peopleList);
        assertEquals(5, peopleList.size());

        var person1 = peopleList.get(1);
        assertNotNull(person1);
        assertNotNull(person1.getId());
        assertNotNull(person1.getLinks());
        assertTrue(person1.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Address Test1", person1.getAddress());
        assertEquals("First Name Test1", person1.getFirstName());
        assertEquals("Last Name Test1", person1.getLastName());
        assertEquals("Female", person1.getGender());

        var person4 = peopleList.get(4);
        assertNotNull(person4);
        assertNotNull(person4.getId());
        assertNotNull(person4.getLinks());
        assertTrue(person4.toString().contains("links: [</api/person/v1/4>;rel=\"self\"]"));
        assertEquals("Address Test4", person4.getAddress());
        assertEquals("First Name Test4", person4.getFirstName());
        assertEquals("Last Name Test4", person4.getLastName());
        assertEquals("Male", person4.getGender());
    }
    */

    @Test
    void createPerson() throws Exception {
        Person mockPerson = input.mockEntity(1);
        Person persisted = mockPerson;
        persisted.setId(1L);

        PersonVO personVO = input.mockVO(1);
        personVO.setId(1L);
        when(repository.save(mockPerson)).thenReturn(persisted);

        var result = services.createPerson(personVO);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void createWithNullPerson() throws Exception {
        Exception exception = assertThrows(RequiredObjectsIsNullException.class, () -> {
            services.createPerson(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updatePerson() throws Exception {
        Person mockPerson = input.mockEntity(1);
        mockPerson.setId(1L);

        Person persisted = mockPerson;
        persisted.setId(1L);

        PersonVO personVO = input.mockVO(1);
        personVO.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(mockPerson));
        when(repository.save(mockPerson)).thenReturn(mockPerson);

        var result = services.updatePerson(personVO);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void updateWithNullPerson() throws Exception {
        Exception exception = assertThrows(RequiredObjectsIsNullException.class, () -> {
            services.updatePerson(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deletePerson() {
        Person mockPerson = input.mockEntity(1);
        mockPerson.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(mockPerson));

        services.deletePerson(1L);
    }
}