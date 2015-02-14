package com.luxoft.bankapp.exception;

import com.luxoft.bankapp.model.CheckingAccount;

/**
 * Created by SCJP on 15.01.2015.
 */

public class OverDraftLimitExceededException extends NotEnoughFundsException {

private CheckingAccount checkingAccount;


    public OverDraftLimitExceededException(float amount, CheckingAccount checkingAccount) {
        super(amount);
        this.checkingAccount = checkingAccount;
    }

    @Override
    public String getMessage () {
        StringBuilder sb = new StringBuilder();
        sb.append("Yoy cant get more than ");
        sb.append(checkingAccount.getBalance() + checkingAccount.getOverdraft());
        return sb.toString();
    }
}

