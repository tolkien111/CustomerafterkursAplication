package pl.sda.customersafterkurs.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.sda.customersafterkurs.entity.Customer;
import pl.sda.customersafterkurs.entity.CustomerRepository;
import pl.sda.customersafterkurs.service.dto.CustomerDetails;
import pl.sda.customersafterkurs.service.dto.CustomerView;
import pl.sda.customersafterkurs.service.exception.CustomerNotExistsException;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class CustomerQuery {

    @NonNull
    private final CustomerRepository customerRepository;


    public List<CustomerView> listCustomers() {
        return customerRepository.findAll().stream()
                .map(Customer::toView)
                .collect(toList());
    }

    public CustomerDetails getById(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotExistsException("customer not found: " + customerId))
                .mapToDetails();

    }
}
