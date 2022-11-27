package pl.sda.customersafterkurs.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.LatLng;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.sda.customersafterkurs.entity.Address;
import pl.sda.customersafterkurs.service.exception.InvalidCoordinatesException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.google.maps.model.AddressComponentType.*;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;

@Component
@RequiredArgsConstructor
public class GoogleReverseGeocoding implements ReverseGeocoding {

    @NonNull
    private final GeoApiContext context;

    @Override
    public Address reverse(double latitude, double longitude) {

        try {
            final var results = GeocodingApi
                    .reverseGeocode(context, new LatLng(latitude, longitude)) // tworzy request
                    .await();//stworzony request zostaje wyslany

            if (results.length == 0)
                throw new InvalidCoordinatesException(format("invalid lat: %s long: %s long", latitude, longitude));

            final var addressComponents = results[0].addressComponents;

            final var streetNumber = findValue(addressComponents, List.of(STREET_NUMBER, PREMISE));
            final var street = findOptionalValue(addressComponents, ROUTE);
            final var city = findValue(addressComponents, LOCALITY);
            final var zipCode = findValue(addressComponents, POSTAL_CODE);
            final var countryCode = findValue(addressComponents, COUNTRY);

//            for (final var component : result.addressComponents) { //mniej przebiegów, bardziej wydajne, ale w naszym przypadku nieistotne ponieważ iterujemy po KRÓTKIEJ TABLICY
//                final var types = asList(component.types);
//                if (types.contains(STREET_NUMBER)) {
//                    streetNumber = component.shortName;
//                } else if (types.contains(ROUTE)) {
//                    street = component.shortName;
//                } else if (types.contains(LOCALITY)) {
//                    city = component.shortName;
//                } else if (types.contains(POSTAL_CODE)){
//                    zipCode = component.shortName;
//                } else if (types.contains(COUNTRY)){
//                    countryCode = component.shortName;
//                }
//            }


            return new Address(
                    street != null ? street + " " + streetNumber : city + " " + streetNumber, //jeżeli street różne od null to street łącz z streetNumber w przeciwnym wypadku użyj tylko streetNumber
                    city,
                    zipCode,
                    countryCode);
        } catch (ApiException | InterruptedException | IOException ex) {
            throw new ReverseGeoCodingException("google ist failing ", ex);
        }
    }

    private String findValue(AddressComponent [] components, AddressComponentType type) {
        for (final var component : components) {
            if (asList(component.types).contains(type)) {
                return component.shortName;
            }
        }
        throw new InvalidCoordinatesException("cannnot find address part: " + type);
    }

    private String findValue(AddressComponent [] components, Collection<AddressComponentType> types) { // sposób na szukanie elementu z listy w liście
        for (final var component : components) {
            if (stream(component.types).anyMatch(types::contains)) { //asList(component.types).stream().anyMatch(type ->types.contains(type))
                return component.shortName;
            }
        }
        throw new InvalidCoordinatesException("cannnot find address part: " + types);
    }

    private String findOptionalValue (AddressComponent [] components, AddressComponentType type){
        for (final var component : components) {
            if (asList(component.types).contains(type)) {
                return component.shortName;
            }
        }
        return null;
    }

}

