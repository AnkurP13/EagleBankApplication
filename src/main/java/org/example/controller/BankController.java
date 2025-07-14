package org.example.controller;

import org.example.bean.CreateBankAccountRequest;
import org.example.bean.CreateBankAccountResponse;
import org.example.bean.CreateUserRequest;
import org.example.bean.CreateUserResponse.AccountCreationResponse;
import org.example.bean.ErrorList;
import org.example.bean.ErrorResponse;
import org.example.converter.RequestToCdm;
import org.example.domain.Account;
import org.example.domain.BankAccount;
import org.example.service.AccountService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class BankController {

    private final UserService userService;
    private final RequestToCdm converter;
    private final AccountService accountService;
    private List<Account> accounts = new ArrayList<>();
    CreateBankAccountResponse bankAccountResponse = new CreateBankAccountResponse();
    private Account fetchedUserId = new Account();
    private CreateBankAccountResponse fetchedAccountId = new CreateBankAccountResponse();

    @Autowired
    private Validator validator;


    public BankController(UserService userService, RequestToCdm userConverter, AccountService accountService) {
        this.userService = userService;
        this.converter = userConverter;
        this.accountService = accountService;
    }

    @PostMapping("/CreateUser")
    public ResponseEntity<?> createAccount(@Valid @RequestBody List<CreateUserRequest> request) throws IOException {
        List<ErrorList> itemErrors = new ArrayList<>();

        for (int i = 0; i < request.size(); i++) {
            Map<String, String> fieldErrors = new HashMap<>();
            Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(request.get(i));
            for (ConstraintViolation<CreateUserRequest> violation : violations) {
                String field = violation.getPropertyPath().toString();
                String message = violation.getMessage();
                fieldErrors.put(field, message);
            }
            if (!fieldErrors.isEmpty()) {
                itemErrors.add(new ErrorList(i, fieldErrors));
            }
        }
        if (!itemErrors.isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse(itemErrors);
            userService.errorsToFile(itemErrors);
            return ResponseEntity.badRequest().body(errorResponse);
        }
        for (CreateUserRequest userRequests : request) {
            Account account = converter.requestToUserAccount(userRequests);
            accounts.add(account);
        }
        userService.createAccounts(accounts);
        return ResponseEntity.ok(new AccountCreationResponse("Accounts created successfully"));
    }

    @GetMapping
    public ResponseEntity<?> getUserById(@RequestParam Long id) {
        try {
            if (accounts.isEmpty()) {
                accounts.addAll(userService.retrieveAccountDetails(id));
            }
            for (Account account : accounts) {
                if (account.getId().equals(id)) {
                    fetchedUserId = account;
                    return ResponseEntity.ok(account);

                }
            }
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Account not found for id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Something went wrong");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/CreateAccounts")
    public ResponseEntity<?> createAccount(@Valid @RequestBody CreateBankAccountRequest request) {
        List<ErrorList> itemErrors = new ArrayList<>();
            Map<String, String> fieldErrors = new HashMap<>();
            Set<ConstraintViolation<CreateBankAccountRequest>> violations = validator.validate(request);
            for (ConstraintViolation<CreateBankAccountRequest> violation : violations) {
                String field = violation.getPropertyPath().toString();
                String message = violation.getMessage();
                fieldErrors.put(field, message);
            }
            if (!fieldErrors.isEmpty()) {
                itemErrors.add(new ErrorList(1, fieldErrors));
            }

        if (!itemErrors.isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse(itemErrors);
            userService.errorsToFile(itemErrors);
            return ResponseEntity.badRequest().body(errorResponse);
        }
        String userId = String.valueOf(fetchedUserId.getId());
        bankAccountResponse = accountService.createBankAccount(userId, converter.requestToBankAccount(request), fetchedUserId.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(bankAccountResponse);
    }

    @GetMapping("/v1/accounts/{accountId}")
    public ResponseEntity<?> getAccountById(@RequestParam Long accountNumb) {
        try {
            if (bankAccountResponse == null) {
                accounts.addAll(userService.retrieveAccountDetails(accountNumb));
            }
            if (bankAccountResponse != null) {
                if (bankAccountResponse.getName().equals(fetchedUserId.getName())) {
                    return ResponseEntity.ok(fetchedAccountId);
                }
            }
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Account not found for id: " + accountNumb);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Something went wrong");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
