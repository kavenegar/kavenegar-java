package com.kavenegar.sdk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kavenegar.sdk.enums.MediaFileStatus;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaResult {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("mimetype")
    private String mimeType;

    @JsonProperty("extension")
    private String extension;

    @JsonProperty("resolution")
    private String resolution;

    @JsonProperty("status_desc")
    private String statusDescription;

    @JsonProperty("size")
    private int size;

    @JsonProperty("duration")
    private int duration;

    @JsonProperty("status")
    private int status;

    public MediaResult() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getStatus() {
        return status;
    }

    public MediaFileStatus getMediaFileStatus() {
        return MediaFileStatus.fromValue(status);
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
