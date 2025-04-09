package ru.thevalidator.timeattackracing.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class UserRegistrationRequest {

    @NotEmpty(message = "Missing parameter 'first_name'")
    private String firstName;

    @NotEmpty(message = "Missing parameter 'last_name'")
    private String lastName;

    @NotEmpty(message = "Missing parameter 'email'")
    private String email;

    @NotEmpty(message = "Missing parameter 'password'")
    private String password;

    @NotNull(message = "Missing parameter 'birth_date'")
    private LocalDate birthDate;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

}
