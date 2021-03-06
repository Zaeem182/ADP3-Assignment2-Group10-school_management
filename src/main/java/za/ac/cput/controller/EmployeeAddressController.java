package za.ac.cput.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import za.ac.cput.domain.EmployeeAddress;
import za.ac.cput.service.service.EmployeeAddressService;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("schoolmanagement/employeeAddress/")
@Slf4j
public class EmployeeAddressController {
    public final EmployeeAddressService employeeAddressService;

    @Autowired public EmployeeAddressController(EmployeeAddressService employeeAddressService)
    {
        this.employeeAddressService=employeeAddressService;
    }
    @PostMapping("save")
    public ResponseEntity<EmployeeAddress> save(@Valid @RequestBody EmployeeAddress employeeAddress)
    {
        log.info("save request:{}",employeeAddress);
        EmployeeAddress save=employeeAddressService.save(employeeAddress);
        return ResponseEntity.ok(save);
    }

    @GetMapping("read/{employeeAddressId}")
    public ResponseEntity<EmployeeAddress> read(@PathVariable String employeeAddressId)
    {
        log.info("Read request:{}",employeeAddressId);
        EmployeeAddress employeeAddress=this.employeeAddressService.read(employeeAddressId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(employeeAddress);
    }
    @DeleteMapping("delete/{employeeAddressId}")
    public ResponseEntity<Void>delete(@PathVariable String employeeAddressId)
    {
        log.info("Read request:{}",employeeAddressId);
        this.employeeAddressService.deleteById(employeeAddressId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("all")
    public ResponseEntity<List<EmployeeAddress>>findAll()
    {
        List<EmployeeAddress>employeeAddressList=this.employeeAddressService.readAll();
        return ResponseEntity.ok(employeeAddressList);
    }
}
