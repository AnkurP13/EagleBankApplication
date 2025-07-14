package org.example.converter;

import org.example.bean.CreateBankAccountRequest;
import org.example.domain.Address;
import org.example.bean.AddressRequest;
import org.example.bean.CreateUserRequest;
import org.example.domain.Account;
import org.example.domain.BankAccount;
import org.springframework.stereotype.Component;

@Component
public class RequestToCdm {

    public Account requestToUserAccount(CreateUserRequest request) {
        if (request == null) return null;

        Account account = new Account();
        account.setName(request.getName());
        account.setEmail(request.getEmail());
        account.setPhone(request.getPhoneNumber());

        Address address = new Address();
        AddressRequest addressReq = request.getAddress();
        if (addressReq != null) {
            address.setLine1(addressReq.getLine1());
            address.setLine2(addressReq.getLine2());
            address.setLine3(addressReq.getLine3());
            address.setTown(addressReq.getTown());
            address.setCounty(addressReq.getCounty());
            address.setPostcode(addressReq.getPostcode());
        }
        account.setAddress(address);
        return account;
    }

    public BankAccount requestToBankAccount(CreateBankAccountRequest request){
        BankAccount bankAccount = new BankAccount();
        if(request != null){
            bankAccount.setName(request.getName());
            bankAccount.setAccountType(request.getAccountType());
        }
        return bankAccount;
    }

}
