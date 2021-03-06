package za.ac.cput.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.City;
import za.ac.cput.domain.Country;
import za.ac.cput.factory.CityFactory;
import za.ac.cput.factory.CountryFactory;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class CityControllerTest {
    @LocalServerPort
    private int port;
    @Autowired private CityController cityController;
    @Autowired private TestRestTemplate restTemplate;
    private City city;
    private Country country;
    private String baseUrl;

    @BeforeEach
    void setUp()
    {
        country = CountryFactory.build("2","2");
        this.city= CityFactory.build("id","waseem.dollie.wd@gmail.com", country);
        this.baseUrl="http://localhost:" + this.port + "/schoolmanagement/city/";
    }
    @Test
    @Order(1)
    void save() {
        String url = baseUrl + "save";
        System.out.println(url);
        ResponseEntity<City> response = this.restTemplate
                .postForEntity(url, this.city, City.class);
        System.out.println(response);
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody())

        );
    }
    @Test
    @Order(5)
    void delete()
    {
        String url=baseUrl + "delete/" + this.city.getId();
        this.restTemplate.delete(url,cityController.delete(url));

    }
    @Test
    @Order(2)
    void read() {
        String url=baseUrl + "read/" + this.city.getId();
        System.out.println(url);
        ResponseEntity<City>response=
                this.restTemplate.getForEntity(url,City.class);
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
        ResponseEntity<City[]>response=
                this.restTemplate.getForEntity(url,City[].class);
        System.out.println(Arrays.asList(response.getBody()));
        assertAll(
                ()->assertEquals(HttpStatus.OK,response.getStatusCode()),
                ()->assertTrue(response.getBody().length==1)
        );
    }
    @Test
    @Order(4)
    void findCitiesByCountryId(){
        String url = baseUrl + "readCityByCountryId/" + this.country.getCountryId();
        System.out.println(url);
        ResponseEntity<City[]>response=
                this.restTemplate.getForEntity(url,City[].class);
        System.out.println(response);
        assertAll(
                ()->assertEquals(HttpStatus.OK,response.getStatusCode()),
                ()->assertNotNull(response.getBody())
        );

    }
}
