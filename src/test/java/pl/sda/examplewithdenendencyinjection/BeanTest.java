package pl.sda.examplewithdenendencyinjection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sda.examplewithdenendencyinjection.AllBeans;

@SpringBootTest
class BeanTest {

    @Autowired
    AllBeans allBeans;

    @Test
    void circuitBeanTest(){
        allBeans.print();
    }
}