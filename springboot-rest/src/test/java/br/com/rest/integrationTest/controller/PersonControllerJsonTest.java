package br.com.rest.integrationTest.controller;

import br.com.rest.configs.TestConfigs;
import br.com.rest.data.vo.v1.PersonVO;
import br.com.rest.integrationTest.testcontainers.AbstractIntegrationTest;
import br.com.rest.integrationTest.vo.PersonTestVO;
import br.com.rest.integrationTest.vo.wrappers.WrapperPersonVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static PersonTestVO person;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        person = new PersonTestVO();
    }

    @Test
    @Order(1)
    public void testCreate() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, "http://localhost:8080")
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given()
                .spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                    .body(person)
                    .when()
                    .post()
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                        .asString();

        PersonTestVO createPersonTest = objectMapper.readValue(content, PersonTestVO.class);
        person = createPersonTest;

        assertNotNull(createPersonTest);
        assertNotNull(createPersonTest.getId());
        assertNotNull(createPersonTest.getFirstName());
        assertNotNull(createPersonTest.getLastName());
        assertNotNull(createPersonTest.getAddress());
        assertNotNull(createPersonTest.getGender());

        assertTrue(createPersonTest.getId() > 0);


        assertEquals("Weslin", createPersonTest.getFirstName());
        assertEquals("Lira", createPersonTest.getLastName());
        assertEquals("Brasília - DF", createPersonTest.getAddress());
        assertEquals("Male", createPersonTest.getGender());
    }

    @Test
    @Order(2)
    public void testFindAll() throws JsonMappingException, JsonProcessingException {
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .queryParams("page", 3, "size", 10, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        WrapperPersonVO wrapperPersonVO = objectMapper.readValue(content, WrapperPersonVO.class);
        var people = wrapperPersonVO.getEmbedded().getPersonTestVOList();

        PersonTestVO foundPersonOne = people.get(0);

        assertNotNull(foundPersonOne);
        assertNotNull(foundPersonOne.getId());
        assertNotNull(foundPersonOne.getFirstName());
        assertNotNull(foundPersonOne.getLastName());
        assertNotNull(foundPersonOne.getAddress());
        assertNotNull(foundPersonOne.getGender());

        assertTrue(foundPersonOne.getEnabled());
    }

    @Test
    @Order(3)
    public void testFindByName() throws JsonMappingException, JsonProcessingException {
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .accept(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("firstName", person.getFirstName())
                .queryParams("page", 0, "size", 6, "direction", "asc")
                    .when()
                    .get("/findPersonByName/{firstName}")
                .then()
                    .statusCode(200)
                        .extract()
                        .body()
                            .asString();

        WrapperPersonVO wrapperPersonVO = objectMapper.readValue(content, WrapperPersonVO.class);
        var people = wrapperPersonVO.getEmbedded().getPersonTestVOList();

        PersonTestVO foundPersonOne = people.get(0);

        assertNotNull(foundPersonOne);
        assertNotNull(foundPersonOne.getId());
        assertNotNull(foundPersonOne.getFirstName());
        assertNotNull(foundPersonOne.getLastName());
        assertNotNull(foundPersonOne.getAddress());
        assertNotNull(foundPersonOne.getGender());

        assertTrue(foundPersonOne.getEnabled());
    }

    @Test
    @Order(4)
    public void testHATEOAS() throws JsonMappingException, JsonProcessingException {
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .queryParams("page", 3, "size", 10, "direction", "asc")
                    .when()
                    .get()
                .then()
                    .statusCode(200)
                        .extract()
                        .body()
                            .asString();

        assertNotNull(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/api/person/v1/1\"}}}"));
    }

    private void mockPerson() {
        person.setFirstName("Weslin");
        person.setLastName("Lira");
        person.setAddress("Brasília - DF");
        person.setGender("Male");
    }
}
