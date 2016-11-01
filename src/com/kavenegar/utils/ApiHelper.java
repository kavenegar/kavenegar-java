/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kavenegar.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import com.kavenegar.utils.*;


public class ApiHelper {
    /*
     * In Method Stream e barghashi az response ra khande va be json tabdil mikonad 
     */
    public static JsonObject getResult(HttpResponse response) {
        HttpEntity entity = response.getEntity();
        InputStream stream = null;

        try {
            stream = entity.getContent();
            Gson gson = new Gson();
            String result = IOUtils.toString(stream, "UTF-8");
            JsonObject json = (JsonObject) gson.fromJson(result, JsonElement.class);
            return json;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }
    /*
     * In Method Baraye Rahat add Karadane parameter dar HttpPost mibashad 
     */
    public static UrlEncodedFormEntity createParams(Object... params) {
        try {
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            for (int i = 0; i < params.length; i += 2) {
                String key = params[i].toString();
                Object value = params[i + 1];
                if (value == null) {
                    continue;
                }
                formparams.add(new BasicNameValuePair(key, value.toString()));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            return entity;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    /*
     *  In Method code barghasti az json ra be Enum ( ErrorCode ) tabdil mikonad
     */
    public static JsonObject getJsonResult(JsonObject json) {
        JsonObject result = (JsonObject) json.get("return");
        return result;
    }
     
}
