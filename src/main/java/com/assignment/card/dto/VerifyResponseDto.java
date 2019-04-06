package com.assignment.card.dto;

/**
 * Created by Mmussadiq on 4/6/2019.
 */
public class VerifyResponseDto {

    private Boolean success;
    private CardDto payload;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public CardDto getPayload() {
        return payload;
    }

    public void setPayload(CardDto payload) {
        this.payload = payload;
    }
}
