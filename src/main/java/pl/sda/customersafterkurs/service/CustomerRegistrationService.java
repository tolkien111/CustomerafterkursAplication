package pl.sda.customersafterkurs.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import pl.sda.customersafterkurs.entity.Company;
import pl.sda.customersafterkurs.entity.CustomerRepository;
import pl.sda.customersafterkurs.entity.Person;
import pl.sda.customersafterkurs.service.dto.RegisterCompanyForm;
import pl.sda.customersafterkurs.service.dto.RegisterPersonForm;
import pl.sda.customersafterkurs.service.dto.RegisteredCustomerId;
import pl.sda.customersafterkurs.service.exception.EmailAlreadyExistsException;
import pl.sda.customersafterkurs.service.exception.PeselAlreadyExistsException;
import pl.sda.customersafterkurs.service.exception.VatAlreadyExistsException;

import javax.transaction.Transactional;


@Service
@Transactional
//@RequiredArgsConstructor
public class CustomerRegistrationService { // podczas zajęć brak możliwości wpisania final z uwagi że Spring podczes transakcji (@Transactional) odziedzicza klase i owija klasę we wrappera(klasę opakowującą), w najnowszej wersji można wpisać final
    //update, final trzeba usunąć ponieważ mimo że nie podkreśla to nie może podnieść kontekstu
    //    @NonNull
    private final CustomerRepository repository;

    public CustomerRegistrationService(@NonNull CustomerRepository repository) {
        this.repository = repository;
    }

    public RegisteredCustomerId registerCompany(@NonNull RegisterCompanyForm form) {
        if (repository.emailExists(form.getEmail()))
            throw new EmailAlreadyExistsException("email exists: " + form.getEmail());
        if (repository.vatExists(form.getVat()))
            throw new VatAlreadyExistsException("vat exists: " + form.getVat());

        final var company = Company.createWith(form);
        repository.save(company);

        return new RegisteredCustomerId(company.getId());

    }

    public RegisteredCustomerId registerPerson(@NonNull RegisterPersonForm form) {
        if (repository.emailExists(form.getEmail()))
            throw new EmailAlreadyExistsException("email exists: " + form.getEmail());
        if (repository.peselExists(form.getPesel()))
            throw new PeselAlreadyExistsException("pesel exists: " + form.getPesel());
//        if (!PeselValidation.peselIsValid(form.getPesel()))
//            throw new PeselNotValidateException("pesel " + form.getPesel() + " is not correct");
//
        final var person = Person.createWith(form);
        repository.save(person);

        return new RegisteredCustomerId(person.getId());
    }

}
