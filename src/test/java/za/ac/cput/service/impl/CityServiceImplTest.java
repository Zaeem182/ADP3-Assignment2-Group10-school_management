package za.ac.cput.service.impl;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.City;
import za.ac.cput.domain.Country;
import za.ac.cput.domain.Student;
import za.ac.cput.factory.CityFactory;
import za.ac.cput.factory.CountryFactory;
import za.ac.cput.service.service.CityService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CityServiceImplTest {

    private final Country country = CountryFactory.build("1", "SA");
    private final City city = CityFactory.build("1", "cape town", country);

    @Autowired
    private CityService cityService;


    @Order(1)
    @Test
    void save() {
        City create = this.cityService.save(this.city);
        assertEquals(this.city,create);
        System.out.println(create);
    }

    @Order(2)
    @Test
    void read() {
        Optional<City> view = this.cityService.read(this.city.getId());
        assertAll(
                ()-> assertTrue(view.isPresent()),
                ()-> assertEquals(this.city,view.get())
        );
    }

/*    @Test
    void delete() {
    }*/

    @Order(3)
    @Test
    void readAll() {
        List<City> cityList = this.cityService.readAll();
        assertEquals(1,cityList.size());
    }

    @Order(5)
    @Test
    void deleteById() {
        this.cityService.deleteById("1");
        List<City> cityList = this.cityService.readAll();
        System.out.println(cityList);

    }
    @Order(4)
    @Test
    void findCitiesByCountry(){
    List<City> cityList = this.cityService.findCitiesByCountry_CountryId(country.getCountryId());
        System.out.println(cityList);
    }
}