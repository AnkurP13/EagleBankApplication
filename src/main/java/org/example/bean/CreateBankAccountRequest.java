package org.example.bean;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CreateBankAccountRequest {

    @NotBlank(message = "Account name is required")
    private String name;

    @NotBlank(message = "Account type is required")
    @Pattern(regexp = "personal", message = "Account type must be 'personal'")
    private String accountType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
