package org.example.bean;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreateBankAccountResponse {

    @Pattern(regexp = "^01\\d{6}$", message = "Account number must start with 01 and be 8 digits long")
    private String accountNumber;

    @Pattern(regexp = "10-10-10", message = "Sort code must be '10-10-10'")
    private String sortCode;

    private String name;

    @Pattern(regexp = "personal", message = "Account type must be 'personal'")
    private String accountType;

    @DecimalMin(value = "0.00", inclusive = true)
    @DecimalMax(value = "10000.00", inclusive = true)
    private BigDecimal balance;

    @Pattern(regexp = "GBP", message = "Currency must be 'GBP'")
    private String currency;

    private LocalDateTime createdTimestamp;
    private LocalDateTime updatedTimestamp;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public LocalDateTime getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(LocalDateTime updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }
}

