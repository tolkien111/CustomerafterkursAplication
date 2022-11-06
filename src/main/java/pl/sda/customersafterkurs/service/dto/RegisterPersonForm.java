package pl.sda.customersafterkurs.service.dto;


import lombok.NonNull;

import java.util.Objects;

public class RegisterPersonForm { //bez lomboka

    @NonNull
    private final String email;
    @NonNull
    private final String firstName;
    @NonNull
    private final String lastName;
    @NonNull
    private final String pesel;

    public RegisterPersonForm(@NonNull String email,@NonNull String firstName,@NonNull String lastName,@NonNull String pesel) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPesel() {
        return pesel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterPersonForm that = (RegisterPersonForm) o;
        return email.equals(that.email) && firstName.equals(that.firstName) && lastName.equals(that.lastName) && pesel.equals(that.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, firstName, lastName, pesel);
    }

}
