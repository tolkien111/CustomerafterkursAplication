package pl.sda.customersafterkurs.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    //SELECT
    List<Person> findByLastName(String lastname);

    List<Person> findByFirstNameStartingWithIgnoreCaseAndLastNameStartingWithIgnoreCase(String firstName, String lastName);

    List<Customer> findByEmailEndingWithIgnoreCase(String email);

    @Query("FROM Person p WHERE UPPER(p.firstName) LIKE UPPER(?1) AND UPPER(p.lastName) LIKE UPPER(?2)")
    List<Person> searchPeople(String firstName, String lastName);

    @Query("FROM Customer c INNER JOIN c.addresses a  WHERE LOWER(a.city) = LOWER(?1)")
    List<Customer> findCustomerInCity(String city);

    @Query("FROM Company c INNER JOIN c.addresses a WHERE LOWER(a.countryCode) = LOWER(:countryCode) ORDER BY c.name")
    List<Company> findCompaniesInCountrySortedByName(@Param("countryCode") String code);

    @Query("SELECT p.addresses FROM Person p WHERE LOWER (p.lastName) = LOWER(:searchLastName)")
    List<Address> findAllAddressesForLastName(String searchLastName);
    //SELECT a.* FROM addresses a INNER JOIN customers c on c.id = a.customer_id WHERE lastName LIKE searchLastName

    @Query("SELECT a.city, count(DISTINCT c) FROM Customer c INNER JOIN c.addresses a GROUP BY a.city ORDER BY a.city asc")
        //błąd, przy kilku adresach w tym samym mieście zlicza tego samego klienta wielokrotnie
    List<Object[]> countCustomersByCity();
    //rozwiązanie, użycie DISTINCT czyli jeżeli dany rekord Customer już się w zliczaniu powtórzył nie zliczy go ponownie


    @Query("SELECT a.countryCode as countryCode, count(DISTINCT c) as count "
            + "FROM Customer c "
            + "INNER JOIN c.addresses a "
            + "GROUP BY a.countryCode "
            + "ORDER BY a.countryCode ASC")
    List<CountCustomerByCountryCode> countCustomersByCountryCode();

    interface CountCustomerByCountryCode {
        String getCountryCode();

        int getCount();
    }

    @Query("SELECT new pl.sda.customersafterkurs.entity.CompanyZipCodeView(c.name, c.vat, a.zipCode) "
            + "FROM Company c "
            + "INNER JOIN c.addresses a "
            + "WHERE a.zipCode "
            + "LIKE ?1")
    List<CompanyZipCodeView> findCompaniesWithZipCode(String zipCode);


    @Query("FROM PersonView v WHERE LOWER(v.email) LIKE LOWER(?1)")
    List<PersonView> findPersonViewByEmail(String email);

    //UPDATE

    @Modifying
    @Query("UPDATE Address SET countryCode = :countryCode WHERE city = :city")
    int updateCountryCodeForCity(String city, String countryCode);

    //dodatkowe sprawdzenie dla powyższego Update

    @Query("SELECT count(a) FROM Address a WHERE a.city = :city "
            + "AND a.countryCode = :countryCode")
    int countCityWithCountryCode(String city, String countryCode);

    @Query("FROM Address a WHERE a.city = :city")
    List<Address> findByCity(String city);


    //DELETE

    @Modifying
    @Query("DELETE Address a WHERE a.zipCode LIKE ?1")
    int deleteAllAddressesWithZipCode(String zipCode);

    @Query("SELECT count(a) FROM Address a")
    int countAllAddresses();


    //To SERVICE

    @Query("SELECT (count(c) > 0) FROM Customer c WHERE LOWER(c.email) = LOWER(?1)") //() przy count dla pewności
    boolean emailExists(String email);

    @Query("SELECT (count(c) > 0) FROM Company c WHERE LOWER(c.vat) = LOWER(?1)")
    boolean vatExists(String vat);

    @Query("SELECT count(p) > 0 FROM Person p WHERE p.pesel = :pesel")
    boolean peselExists(String pesel);

}
