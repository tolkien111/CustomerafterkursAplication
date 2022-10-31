package pl.sda.customersafterkurs.demo;

import org.springframework.stereotype.Component;

@Component
public class InvoiceService {

    public void createInvoice(String customerName, String invoiceNumber){
        System.out.println("Preparing invoice number " + invoiceNumber +" for customer " + customerName);
    }
}
