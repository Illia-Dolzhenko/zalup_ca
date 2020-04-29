package com.hotmail.dolzhik.zalup_ca.entities;

public enum RateCategory {
    TITS("Tits rating"),
    PUSSY("Pussy rating"),
    BODY("Body rating"),
    FACE("Face rating"),
    ASS("Ass rating"),
    COCK("Cock rating");

    private String description;
    RateCategory(String description) {
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
