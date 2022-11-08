package pl.sda.customersafterkurs.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.customersafterkurs.entity.Address;
import pl.sda.customersafterkurs.entity.CustomerRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerAddressService {

    @NonNull
    private final CustomerRepository repository;

    @NonNull
    private final ReverseGeocoding reverseGeocoding;

    public CreatedAddress addAddress(AddAddressForm form) {
        if (!repository.existsById(form.getCustomerId())) {
            throw new CustomerNotExistsException("customer not exists: " + form.getCustomerId());
        }
        final var address = reverseGeocoding.reverse(form.getLatitude(), form.getLongitude());
        final var customer = repository.getReferenceById(form.getCustomerId); // u trenera getById, w razie problemów zmienić
        customer.addAddress(address);
        repository.save(customer);
        return new CreatedAddress(customer.getId(),
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getZipCode(),
                address.getCountryCode());
    }

}
