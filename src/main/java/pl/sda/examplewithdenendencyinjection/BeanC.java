package pl.sda.examplewithdenendencyinjection;

import org.springframework.stereotype.Component;

@Component
public class BeanC  {

    private final BeanA beanA;

    public BeanC(BeanA beanA) {
        this.beanA = beanA;
    }

    public void beanC(BeanA beanA){
        System.out.println("Injection BeanA to BeanC: " + beanA);
    }
}
