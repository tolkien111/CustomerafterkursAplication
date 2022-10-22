package pl.sda.examplewithdenendencyinjection;

import org.springframework.stereotype.Component;

@Component
public class BeanA {

    private final BeanB beanB;


    public BeanA(BeanB beanB) {
        this.beanB = beanB;
    }

    public void beanA(BeanB beanB){
        System.out.println("Injection BeanB to BeanA: " + beanB);
    }
}
