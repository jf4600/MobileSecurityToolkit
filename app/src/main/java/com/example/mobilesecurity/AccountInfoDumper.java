package com.example.mobilesecurity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

public class AccountInfoDumper {

    public static String dumpAccounts(Context context) {
        StringBuilder output = new StringBuilder();

        try {
            AccountManager am = AccountManager.get(context);
            Account[] accounts = am.getAccounts();

            if (accounts.length == 0) {
                output.append("No accounts found.");
            } else {
                for (Account account : accounts) {
                    output.append("Name: ").append(account.name)
                            .append(" | Type: ").append(account.type).append("\n");
                }
            }
        } catch (SecurityException e) {
            output.append("Access denied: Missing GET_ACCOUNTS permission.\n");
        } catch (Exception e) {
            output.append("Error: ").append(e.getMessage()).append("\n");
        }

        return output.toString();
    }
}
