package org.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bean.CreateBankAccountResponse;
import org.example.bean.ErrorResponse;
import org.example.constants.BankConstants;
import org.example.domain.Account;
import org.example.exceptions.jsonParsingException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.constants.BankConstants.CREATE_ACCOUNT_DIR;
import static org.example.constants.BankConstants.CREATE_USER_DIR;
import static org.example.constants.BankConstants.ERROR_FILE_DIR;
import static org.example.constants.BankConstants.FILE_CREATE_ACCOUNT;
import static org.example.constants.BankConstants.FILE_CREATE_USER;

public class BankUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void userJsonConverstion(List<Account> createAccounts) {
        try {
            File directory = new File(CREATE_USER_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File(directory, FILE_CREATE_USER);

            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, createAccounts);
        } catch (Exception e) {
            throw new jsonParsingException("Error occurred whilst json conversion");
        }
    }

    public static void accountJsonConverstion(CreateBankAccountResponse bankAccount) {
        try {
            File directory = new File(CREATE_ACCOUNT_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File(directory, FILE_CREATE_ACCOUNT);

            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, bankAccount);
        } catch (Exception e) {
            throw new jsonParsingException("Error occurred whilst json conversion");
        }
    }

    public static List<Account> loadAccountsFromFile() throws IOException {
        List<Account> allAccounts = new ArrayList<>();
        try {
            File file = new File(CREATE_USER_DIR, FILE_CREATE_USER);
            allAccounts = Arrays.asList(mapper.readValue(file, Account[].class));
        }catch (Exception e){
            throw new jsonParsingException("Error occurred whilst json conversion");
        }
        return allAccounts;
    }

    public static void writeErrorsToFile(ErrorResponse errorResponse) {
        try {
            clearFile(ERROR_FILE_DIR);
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(ERROR_FILE_DIR), errorResponse);
        } catch (Exception e) {
            throw new jsonParsingException("Error occurred whilst json conversion");
        }
    }

    public static void clearFile(String path) {
        try (FileWriter fw = new FileWriter(path, false)) {
            // false = overwrite mode
            fw.write(""); // write nothing (clears the file)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
