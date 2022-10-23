package pl.sda.customersafterkurs.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional
abstract class EntityTest {

    @Autowired
    protected EntityManager em;

    protected void persist(Object entity){
        //KLUCZ: ID, Wartość: Encja
        em.persist(entity); // dodanie do cach
        em.flush();//wysłanie cache do db: insert into adresses ......
        em.clear();//czyszczenie cach
    }
}
