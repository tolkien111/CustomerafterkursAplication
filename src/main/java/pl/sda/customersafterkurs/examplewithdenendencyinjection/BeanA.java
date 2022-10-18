package pl.sda.customersafterkurs.examplewithdenendencyinjection;

import org.springframework.stereotype.Component;

@Component
public class BeanA {

    private final BeanB beanB;


    public BeanA(BeanB beanB) {
        this.beanB = beanB;
    }

    void beanA(BeanB beanB){
        System.out.println("Injection BeanB to BeanA: " + beanB);
    }
}
