package pl.sda.customersafterkurs.demo;

import org.springframework.stereotype.Component;

@Component
public class OrderService {

    InvoiceService invoice;
    OrderRepository order;

    public OrderService(InvoiceService invoice, OrderRepository order) {
        this.invoice = invoice;
        this.order = order;
    }

    public void makeOrder(String customerName, String invoiceName){
        System.out.println("making order...");
        invoice.createInvoice(customerName, invoiceName);
        order.save(customerName, invoiceName);
        System.out.println("order created");

    }
}
