package com.luxoft.bankapp.expeption;

/**
 * Created by SCJP on 15.01.2015.
 */
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.CheckingAccount;

public class OverDraftLimitExceededException extends NotEnoughFundsException{



    private CheckingAccount account;



    public OverDraftLimitExceededException(String message, float amount, CheckingAccount account) {
        super(message, amount);
        this.account = account;
    }

    public float getMaxAmountOfAvailableFunds(){
        return account.getOverdraft() + account.getBalance();
    }

    public OverDraftLimitExceededException(String s, float amount) {
        super(s, amount);
    }
}

