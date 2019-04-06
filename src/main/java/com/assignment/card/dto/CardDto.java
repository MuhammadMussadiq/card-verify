package com.assignment.card.dto;

/**
 * Created by Mmussadiq on 4/5/2019.
 */
public class CardDto {

    private String scheme;
    private String type;
    private String bank;

    public CardDto() {
    }

    public CardDto(String scheme, String type, String bank) {
        this.scheme = scheme;
        this.type = type;
        this.bank = bank;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
