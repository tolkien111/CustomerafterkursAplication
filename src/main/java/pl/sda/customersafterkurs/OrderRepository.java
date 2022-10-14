package pl.sda.customersafterkurs;

import org.springframework.stereotype.Component;

@Component
public class OrderRepository {

    public void save(String customerName, String invoiceNumber){
        System.out.println("Saving invoice number " + invoiceNumber +" for customer " + customerName);
    }
}
