package pl.sda.customersafterkurs.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.LatLng;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.sda.customersafterkurs.entity.Address;
import static com.google.maps.model.AddressComponentType.*;
import static java.lang.String.format;
import static java.util.Arrays.asList;

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
                throw new ReverseGeoCodingException(format("invalid lat: %s long: %s long", latitude, longitude));

            final var result = results[0];

            String streetNumber = null;
            String street = null;
            String city = null;
            String zipCode = null;
            String countryCode = null;

            for (final var component : result.addressComponents) {
                final var types = asList(component.types);
                if (types.contains(STREET_NUMBER)) {
                    streetNumber = component.shortName;
                } else if (types.contains(ROUTE)) {
                    street = component.shortName;
                } else if (types.contains(LOCALITY)) {
                    city = component.shortName;
                } else if (types.contains(POSTAL_CODE)){
                    zipCode = component.shortName;
                } else if (types.contains(COUNTRY)){
                    countryCode = component.shortName;
                }
            }


            return new Address(street + " " + streetNumber, city, zipCode, countryCode);

        } catch (Exception ex) {
            throw new ReverseGeoCodingException("google ist failing ", ex);
        }
    }

}

