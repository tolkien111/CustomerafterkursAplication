package pl.sda.customersafterkurs.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.customersafterkurs.entity.Company;
import pl.sda.customersafterkurs.entity.CustomerRepository;
import pl.sda.customersafterkurs.service.dto.RegisterCompanyForm;
import pl.sda.customersafterkurs.service.dto.RegisteredCustomerId;
import pl.sda.customersafterkurs.service.exception.EmailAlreadyExistsException;
import pl.sda.customersafterkurs.service.exception.VatAlreadyExistsException;

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

    public RegisteredCustomerId registerCompany(@NonNull RegisterCompanyForm form) {
        if (repository.emailExists(form.getEmail()))
            throw new EmailAlreadyExistsException("email exists: " + form.getEmail());
        if (repository.vatExists(form.getVat()))
            throw new VatAlreadyExistsException("vat exists: " + form.getVat());

        final var company = new Company(form.getEmail(), form.name(), form.vat());
        repository.save(company);

        return new RegisteredCustomerId(company.getId());

    }

}
