package com.luxoft.bankapp.exception;

/**
 * Created by SCJP on 15.01.2015.
 */

public class OverDraftLimitExceededException extends NotEnoughFundsException {


    private float sumClientThatClientCanTake;

    public float getSumClientThatClientCanTake() {
        return sumClientThatClientCanTake;
    }

    public OverDraftLimitExceededException(float sumClientWant) {


        this.sumClientThatClientCanTake =  sumClientWant;


    }


}

