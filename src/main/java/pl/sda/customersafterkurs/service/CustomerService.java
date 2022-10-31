package pl.sda.customersafterkurs.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.customersafterkurs.entity.CustomerRepository;
import pl.sda.customersafterkurs.service.dto.RegisterCompanyForm;
import pl.sda.customersafterkurs.service.dto.RegisteredCustomerId;

import javax.transaction.Transactional;


@Service
@Transactional
//@RequiredArgsConstructor
public final class CustomerService { // podczas zajęć brak możliwości wpisania final z uwagi że Spring podczes transakcji (@Transactional) odziedzicza klase i owija klasę we wrappera(klasę opakowującą), w najnowszej wersji można wpisać final

//    @NonNull
    private final CustomerRepository repository;

    // inne podejście do kostruktora
    public CustomerService(@NonNull CustomerRepository repository) {
        this.repository = repository;
    }

    public RegisteredCustomerId registerCompany(@NonNull RegisterCompanyForm form){
        return null;

    }

}
