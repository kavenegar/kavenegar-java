package com.kavenegar.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaListResult {

    @JsonProperty("page")
    private int page;

    @JsonProperty("size")
    private int size;

    @JsonProperty("total")
    private long total;

    @JsonProperty("list")
    private List<MediaResult> list;

    public MediaListResult() {}

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<MediaResult> getList() {
        return list;
    }

    public void setList(List<MediaResult> list) {
        this.list = list;
    }
}
