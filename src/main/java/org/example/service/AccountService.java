package org.example.service;

import org.example.bean.CreateBankAccountRequest;
import org.example.bean.CreateBankAccountResponse;
import org.example.domain.BankAccount;
import org.example.util.BankUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AccountService {

    // In-memory store: userId -> list of accounts
    private final Map<String, List<CreateBankAccountResponse>> userAccounts = new HashMap<>();

    private final AtomicInteger accountCounter = new AtomicInteger(100000);

    public CreateBankAccountResponse createBankAccount(String userId, BankAccount request, String userName) {
        String accountNumber = generateAccountNumber();
        String sortCode = "10-10-10";
        BigDecimal initialBalance = BigDecimal.ZERO;
        String currency = "GBP";
        LocalDateTime now = LocalDateTime.now();

        CreateBankAccountResponse account = new CreateBankAccountResponse();
        account.setAccountNumber(accountNumber);
        account.setSortCode(sortCode);
        account.setName(userName);
        account.setAccountType(request.getAccountType());
        account.setBalance(initialBalance);
        account.setCurrency(currency);
        account.setCreatedTimestamp(now);
        account.setUpdatedTimestamp(now);

        userAccounts.computeIfAbsent(userId, k -> new ArrayList<>()).add(account);
        BankUtil.accountJsonConverstion(account);
        return account;
    }

    public List<CreateBankAccountResponse> getAccounts(String userId) {
        return userAccounts.getOrDefault(userId, Collections.emptyList());
    }

    public Optional<CreateBankAccountResponse> getAccountByNumber(String userId, String accountNumber) {
        return userAccounts.getOrDefault(userId, Collections.emptyList())
                .stream()
                .filter(acc -> acc.getAccountNumber().equals(accountNumber))
                .findFirst();
    }

    private String generateAccountNumber() {
        return "01" + accountCounter.getAndIncrement();
    }
}

