package com.luxoft.bankapp.expeption;

/**
 * Created by SCJP on 15.01.2015.
 */

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.CheckingAccount;

public class OverDraftLimitExceededException extends NotEnoughFundsException {


    private float sumClientThatClientCanTake;

    public float getSumClientThatClientCanTake() {
        return sumClientThatClientCanTake;
    }

    public OverDraftLimitExceededException(float overdriftPlusBalance, float sumClientWant) {


        this.sumClientThatClientCanTake = overdriftPlusBalance - sumClientWant;


    }


}

