package pl.sda.customersafterkurs.service;

import pl.sda.customersafterkurs.entity.Address;

public interface ReverseGeocoding {

    Address reverse(double latitude, double longitude);
}
