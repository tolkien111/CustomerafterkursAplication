package pl.sda.customersafterkurs.service.dto;

import lombok.Value;
import pl.sda.customersafterkurs.entity.CustomerType;

import java.util.UUID;

@Value
public class CustomerView {

    UUID customerId;
    String name;
    String email;
    CustomerType type;
}
