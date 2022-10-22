package pl.sda.examplewithdenendencyinjection;

import org.springframework.stereotype.Component;

@Component
public class BeanB {

    private final BeanC beanC;

    public BeanB(BeanC beanC) {
        this.beanC = beanC;
    }

    public void beanB (BeanC beanC){
        System.out.println("Injection BeanC to BeanB: " + beanC);
    }
}
