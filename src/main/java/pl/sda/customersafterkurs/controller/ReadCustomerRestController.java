package pl.sda.customersafterkurs.controller;

import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.customersafterkurs.service.CustomerQuery;
import pl.sda.customersafterkurs.service.dto.CustomerView;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
final class ReadCustomerRestController {

    @NotNull
    private final CustomerQuery query;

    @GetMapping
    List<CustomerView> getCustomer(){
        return query.listCustomers();
    }
}
