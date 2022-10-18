package pl.sda.customersafterkurs.examplewithdenendencyinjection;

import org.springframework.stereotype.Component;

@Component
public class AllBeans {

    BeanA beanA;
    BeanB beanB;
    BeanC beanC;

    public AllBeans(BeanA beanA, BeanB beanB, BeanC beanC) {
        this.beanA = beanA;
        this.beanB = beanB;
        this.beanC = beanC;
    }

    public void print(){
        beanA.beanA(beanB);
        beanB.beanB(beanC);
        beanC.beanC(beanA);
    }
}
