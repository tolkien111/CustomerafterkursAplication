package pl.sda.customersafterkurs.demo;

import org.springframework.stereotype.Component;

@Component
public class CustomerDatabase {

    public void save (String email, String name){
        System.out.println("saving to database: " + email);
    }
}
