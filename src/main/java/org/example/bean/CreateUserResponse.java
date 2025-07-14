package org.example.bean;

import org.example.domain.Account;

import java.util.List;

public class CreateUserResponse {

    public static class AccountCreationResponse {
        private String message;

        public AccountCreationResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
