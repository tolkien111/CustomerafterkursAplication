package pl.sda.customersafterkurs.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GoogleReverseGeocodingTest {

    @Autowired
    private GoogleReverseGeocoding reverseGeocoding;

    @Test
    void shouldFindAddressForCoordinates(){
        //Given
        //When
        final var address = reverseGeocoding.reverse(52.242770, 20.979435);

        //Then
        assertNotNull(address);
        assertEquals("Dzielna 78", address.getStreet());
        assertEquals("Warszawa", address.getCity());
        assertEquals("01-029", address.getZipCode());
        assertEquals("PL", address.getCountryCode());
    }
}