package com.assignment.card.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mmussadiq on 4/6/2019.
 */
public class StatsDto {
    private Boolean success;
    private Integer start;
    private Integer limit;
    private Long size;
    private Map<String, Integer> payload;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Map<String, Integer> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Integer> payload) {
        this.payload = payload;
    }
}
