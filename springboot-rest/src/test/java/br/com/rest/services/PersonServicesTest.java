package br.com.rest.services;

import br.com.rest.data.vo.v1.PersonVO;
import br.com.rest.mocks.MockPerson;
import br.com.rest.model.Person;
import br.com.rest.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Test
    void findAll() {
    }

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
    void deletePerson() {
        Person mockPerson = input.mockEntity(1);
        mockPerson.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(mockPerson));

        services.deletePerson(1L);
    }
}