package pl.sda.customersafterkurs.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sda.customersafterkurs.service.CustomerAddressService;
import pl.sda.customersafterkurs.service.CustomerRegistrationService;
import pl.sda.customersafterkurs.service.dto.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WriteCustomersRestController {

    @NonNull
    private final CustomerRegistrationService registrationService;

    @NonNull
    private final CustomerAddressService addressService;

    @PostMapping("/companies")
    ResponseEntity<RegisteredCustomerId> registerCompany(@RequestBody RegisterCompanyForm form){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registrationService.registerCompany(form));

    }
    @PostMapping("/people")
    ResponseEntity<RegisteredCustomerId> registerPerson(@RequestBody RegisterPersonForm form){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registrationService.registerPerson(form));
    }

    @Value
    static class LatLong{

        double latitude;
        double longitude;
    }

    @PostMapping("/customers/{customerId}/addresses")
    ResponseEntity<CreatedAddress> addAddress(@PathVariable("customerId")  UUID id,
                                              @RequestBody LatLong latLong){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(addressService.addAddress(new AddAddressForm(id,
                        latLong.getLatitude(),
                        latLong.getLongitude())));
    }

}
