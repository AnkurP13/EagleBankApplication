package org.example.service;

import org.example.bean.ErrorList;
import org.example.bean.ErrorResponse;
import org.example.domain.*;
import org.example.util.BankUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final ConcurrentHashMap<Long, Account> accountMap = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Account createAccount(Account account) {
        long id = idCounter.getAndIncrement();
        account.setId(id);
        accountMap.put(id, account);
        return account;
    }

    public void createAccounts(List<Account> accounts) {
        List<Account> createdList = new ArrayList<>();
        for (Account account : accounts) {
            createdList.add(createAccount(account));
        }
        BankUtil.userJsonConverstion(createdList);

    }

    public List<Account> retrieveAccountDetails(Long id) throws IOException {
        List<Account> createdList = BankUtil.loadAccountsFromFile();
        return createdList;
    }

    public void errorsToFile(List<ErrorList> errorList){
        ErrorResponse errorResponse = new ErrorResponse(errorList);
        BankUtil.writeErrorsToFile(errorResponse);
    }
}
