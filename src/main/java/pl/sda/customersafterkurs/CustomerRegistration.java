package pl.sda.customersafterkurs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerRegistration {

    private final CustomerRepository repository;

    public CustomerRegistration(CustomerRepository repository){
        this.repository = repository;
    }

    public void register(String email, String name){
        System.out.println("registering customer: " + email);
        repository.save(email, name);
        System.out.println("registered customer: " + email);

    }
}
