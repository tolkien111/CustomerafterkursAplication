package pl.sda.customersafterkurs.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class AddressTest extends EntityTest{

    @Test
    void shouldSaveAddress() {
        //Given
        final var address = new Address("srgfdsgh", "a", "a", "a");

        //When
        persist(address);

        //Then
        final var readAddress = em.find(Address.class, address.getId());
        assertEquals(address, readAddress);
    }

}