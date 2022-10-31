package pl.sda.customersafterkurs.demo;

import org.springframework.stereotype.Component;

@Component
public class CustomerRegistration {

    private final CustomerDatabase repository;

    public CustomerRegistration(CustomerDatabase repository){
        this.repository = repository;
    }

    public void register(String email, String name){
        System.out.println("registering customer: " + email);
        repository.save(email, name);
        System.out.println("registered customer: " + email);

    }
}
