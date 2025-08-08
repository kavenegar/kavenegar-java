package com.kavenegar.sdk.models;

import com.google.gson.JsonObject;
import java.util.Objects;

/**
 * Represents the status result of a message identified by a local ID.
 * Extends the generic StatusResult with an additional localId field.
 *
 * The code was originally written by Mohsen and later developed further by Reza.
 */
public class StatusLocalMessageIdResult extends StatusResult {

    private final long localId;

    public StatusLocalMessageIdResult(JsonObject json) {
        super(json);
        this.localId = json.has("localid") && !json.get("localid").isJsonNull()
                ? json.get("localid").getAsLong() : -1L;
    }

    public long getLocalId() {
        return localId;
    }

    /**
     * Validates whether the local ID is a positive value.
     */
    public boolean isValidLocalId() {
        return localId > 0;
    }

    /**
     * Provides a human-readable summary of the result.
     */
    public String getSummary() {
        return String.format("Local ID: %d | Status: %s (%d)",
                localId, getStatusText(), getStatus());
    }

    /**
     * Compares the local ID with another result.
     */
    public boolean hasSameLocalId(StatusLocalMessageIdResult other) {
        return other != null && this.localId == other.localId;
    }

    /**
     * Checks if the status indicates failure.
     */
    public boolean isFailedStatus() {
        return getStatus() != 1;
    }

    @Override
    public String toString() {
        return "StatusLocalMessageIdResult{" +
                "localId=" + localId +
                ", status=" + getStatus() +
                ", statusText='" + getStatusText() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatusLocalMessageIdResult)) return false;
        if (!super.equals(o)) return false;
        StatusLocalMessageIdResult that = (StatusLocalMessageIdResult) o;
        return localId == that.localId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), localId);
    }
}
