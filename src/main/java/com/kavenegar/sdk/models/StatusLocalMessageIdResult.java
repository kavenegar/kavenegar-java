/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kavenegar.sdk.models;

import com.google.gson.JsonObject;

/**
 * @author mohsen
 */
public class StatusLocalMessageIdResult extends StatusResult {

    long localId;

    public StatusLocalMessageIdResult(JsonObject json) {
        super(json);
        this.localId = json.get("localid").getAsLong();
    }

    public long getLocalId() {
        return localId;
    }
}
