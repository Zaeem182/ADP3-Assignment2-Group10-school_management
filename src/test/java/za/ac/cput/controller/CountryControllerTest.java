package za.ac.cput.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Country;
import za.ac.cput.factory.CountryFactory;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class CountryControllerTest {
    @LocalServerPort
    private int port;
    @Autowired private CountryController countryController;
    @Autowired private TestRestTemplate restTemplate;
    private Country country;
    private String baseUrl;

    @BeforeEach
    void setUp()
    {
        this.country= CountryFactory.build("Name","email123@gmail.com");
        this.baseUrl="http://localhost:" + this.port + "/schoolmanagement/country/";
    }
    @Test
    @Order(1)
    void save() {
        String url = baseUrl + "save";
        System.out.println(url);
        ResponseEntity<Country> response = this.restTemplate
                .postForEntity(url, this.country, Country.class);
        System.out.println(response);
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody())

        );
    }
    @Test
    @Order(4)
    void delete()
    {
        String url=baseUrl + "delete/" + this.country.getCountryId();
        System.out.println(url);
        this.restTemplate.delete(url);

    }
    @Test
    @Order(2)
    void read() {
        String url=baseUrl + "read/" + this.country.getCountryId();
        System.out.println(url);
        ResponseEntity<Country>response=
                this.restTemplate.getForEntity(url,Country.class);
        System.out.println(response);
        assertAll(
                ()->assertEquals(HttpStatus.OK,response.getStatusCode()),
                ()->assertNotNull(response.getBody())
        );

    }


    @Test
    @Order(3)
    void findAll()
    {
        String url=baseUrl +"all";
        System.out.println(url);
        ResponseEntity<Country[]>response=
                this.restTemplate.getForEntity(url,Country[].class);
        System.out.println(Arrays.asList(response.getBody()));
        assertAll(
                ()->assertEquals(HttpStatus.OK,response.getStatusCode()),
                ()->assertTrue(response.getBody().length==1)
        );
    }
}
