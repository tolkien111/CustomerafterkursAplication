package pl.sda.customersafterkurs.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;
import pl.sda.customersafterkurs.service.CustomerRegistrationService;
import pl.sda.customersafterkurs.service.dto.RegisterCompanyForm;
import pl.sda.customersafterkurs.service.dto.RegisterPersonForm;
import pl.sda.customersafterkurs.service.dto.RegisteredCustomerId;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WriteCustomersRestController {

    @NonNull
    private final CustomerRegistrationService service;

    @PostMapping("/companies")
    ResponseEntity<RegisteredCustomerId> registerCompany(@RequestBody RegisterCompanyForm form){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.registerCompany(form));

    }
    @PostMapping("/people")
    ResponseEntity<RegisteredCustomerId> registerPerson(@RequestBody RegisterPersonForm form){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.registerPerson(form));
    }

}
