package com.kavenegar.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CursorPaginationModel {

    @JsonProperty("prevcursor")
    private int prevCursor;

    @JsonProperty("currentcursor")
    private int currentCursor;

    @JsonProperty("nextcursor")
    private int nextCursor;

    @JsonProperty("pagesize")
    private int pageSize;

    @JsonProperty("totalcount")
    private long totalCount;

    public CursorPaginationModel() {}

    public int getPrevCursor() {
        return prevCursor;
    }

    public void setPrevCursor(int prevCursor) {
        this.prevCursor = prevCursor;
    }

    public int getCurrentCursor() {
        return currentCursor;
    }

    public void setCurrentCursor(int currentCursor) {
        this.currentCursor = currentCursor;
    }

    public int getNextCursor() {
        return nextCursor;
    }

    public void setNextCursor(int nextCursor) {
        this.nextCursor = nextCursor;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
