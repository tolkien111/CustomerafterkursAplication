package pl.sda.customersafterkurs.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.LatLng;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.sda.customersafterkurs.entity.Address;

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
        } catch (Exception ex) {
            throw new ReverseGeoCodingException("google ist failing ", ex);
        }
        return null;
    }

}

