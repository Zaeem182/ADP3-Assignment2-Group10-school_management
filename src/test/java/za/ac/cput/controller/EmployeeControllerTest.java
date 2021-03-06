package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Employee;
import za.ac.cput.domain.Name;
import za.ac.cput.factory.EmployeeFactory;
import za.ac.cput.factory.NameFactory;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class EmployeeControllerTest {
    @LocalServerPort
    private int port;
    @Autowired private EmployeeController controller;
    @Autowired private TestRestTemplate restTemplate;
    private Employee employee;
    private Name name;
    private String baseUrl;

    @BeforeEach
    void setUp()
    {
        this.name = NameFactory.build("max", "", "jacobs");
        this.employee= EmployeeFactory.build("7","adp3@gmail.com", name);
        this.baseUrl="http://localhost:" + this.port + "/schoolmanagement/employee/";
    }
    @Test
    @Order(1)
    void save() {
        String url = baseUrl + "save";
        System.out.println(url);
        ResponseEntity<Employee> response = this.restTemplate
                .postForEntity(url, this.employee, Employee.class);
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
        String url=baseUrl + "delete/" + this.employee.getStaffId();
        System.out.println(url);
        this.restTemplate.delete(url,controller.delete(url));


    }
    @Test
    @Order(2)
    void read() {
        String url=baseUrl + "read/" + this.employee.getStaffId();
        System.out.println(url);
        ResponseEntity<Employee>response=
                this.restTemplate.getForEntity(url,Employee.class);
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
        ResponseEntity<Employee[]>response=
                this.restTemplate.getForEntity(url,Employee[].class);
        System.out.println(Arrays.asList(response.getBody()));
        assertAll(
                ()->assertEquals(HttpStatus.OK,response.getStatusCode()),
                ()->assertTrue(response.getBody().length==1)
        );
    }
    @Test
    @Order(4)
    void findEmployeeByEmail(){
        String url = baseUrl + "findByEmail/" + this.employee.getEmail();
        ResponseEntity<Employee> response = this.restTemplate.getForEntity(url, Employee.class);
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody())
        );
        System.out.println(url);

    }
}
