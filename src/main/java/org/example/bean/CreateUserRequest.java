package org.example.bean;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotNull;

public class CreateUserRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Valid
    @NotNull(message = "Address is required")
    private AddressRequest address;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^\\+[1-9]\\d{1,14}$",
            message = "Phone number must be in E.164 format (e.g., +1234567890)"
    )
    private String phoneNumber;

    @Email(message = "Must be a valid email")
    @NotBlank(message = "Email is required")
    private String email;

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressRequest getAddress() {
        return address;
    }

    public void setAddress(AddressRequest address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

