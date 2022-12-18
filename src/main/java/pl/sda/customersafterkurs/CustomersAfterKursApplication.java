package pl.sda.customersafterkurs;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Component;
import pl.sda.customersafterkurs.entity.Address;
import pl.sda.customersafterkurs.entity.Company;
import pl.sda.customersafterkurs.entity.CustomerRepository;
import pl.sda.customersafterkurs.entity.Person;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootApplication
public class CustomersAfterKursApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomersAfterKursApplication.class, args);
	}

	@Component
	@RequiredArgsConstructor
	@Profile("dev")
	static class InitOnStartUp{

		private final CustomerRepository repository;

		@EventListener
		@Transactional
		public void setup(ApplicationReadyEvent event){

			final var customer01 = new Person("adv@wP.pl", "Janek", "Marciniak", "0907987658758");
			final var customer02 = new Person("aaaav@Wp.pl", "Roman", "Nowak", "090000000758");
			final var customer03 = new Person("aaasasav@gmail.com", "Romano", "Nowakiewicz", "0900000758");
			final var customer04 = new Company("agrw@WP.PL", "Nowa Firma", "PL32439555");
			final var customer05 = new Company("agrw@WP.PL", "Firma", "PL3243000009555");

			customer01.addAddress(new Address("str", "Wawa", "04-222", "PL"));
			customer01.addAddress(new Address("stra", "Wyszków", "66-222", "PL"));
			customer01.addAddress(new Address("Nowa ulica", "Wawa", "67-222", "PL"));
			customer02.addAddress(new Address("sds", "Wawa", "14-222", "PL"));
			customer03.addAddress(new Address("Jerozolimskie", "Elbląg", "55-222", "PL"));
			customer04.addAddress(new Address("str", "Wawa", "04-222", "PL"));
			customer05.addAddress(new Address("aaa", "Elbląg", "82-100", "PL"));

			repository.saveAllAndFlush(List.of(customer01, customer02, customer03, customer04, customer05));



		}
	}
}
