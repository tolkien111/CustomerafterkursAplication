package pl.sda.customersafterkurs.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.sda.customersafterkurs.entity.CustomerRepository;
import pl.sda.customersafterkurs.service.dto.CustomerView;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class CustomerQuery {

    @NonNull
    private final CustomerRepository customerRepository;


    public List<CustomerView> listCustomers(){
       return customerRepository.findAll().stream()
                .map(customer -> new CustomerView(customer.getId(),
                        customer.getName(),
                        customer.getEmail(),
                        customer.getCustomerType()))
                .collect(toList());
    }

}
