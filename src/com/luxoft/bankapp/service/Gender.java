package com.luxoft.bankapp.service;

/**
 * Created by SCJP on 14.01.2015.
 */
public enum Gender {
    MALE("Mr."), FEMALE("Ms.");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGenderPrefix() {
        return gender;
    }
}
