/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kavenegar.sdk;

import com.google.gson.*;
import com.kavenegar.sdk.enums.MessageType;
import com.kavenegar.sdk.excepctions.ApiException;
import com.kavenegar.sdk.excepctions.BaseException;
import com.kavenegar.sdk.excepctions.HttpException;
import com.kavenegar.sdk.models.*;
import com.kavenegar.sdk.utils.PairValue;
import com.kavenegar.sdk.utils.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author Kavenegar
 */
public class KavenegarApi {


    static final String API_PATH = "%s://api.kavenegar.com/v1/%s/%s.json";

    private String apiKey;
    private Boolean safe;

    public KavenegarApi(String apiKey) {
        this.apiKey = apiKey;
        this.safe =Boolean.FALSE;
    }
	
    public KavenegarApi(String apiKey ,Boolean safe) {
        this.apiKey = apiKey;
        this.safe = safe;
    }
    private String getApiPath(String method) {
        if(this.safe==Boolean.TRUE)
            return String.format(API_PATH, "https",apiKey, method);
        else
            return String.format(API_PATH, "http",apiKey, method);
    }

    public UrlEncodedFormEntity createParams(Object... params) {
        try {
            List<NameValuePair> formparams = new ArrayList<>();
            for (int i = 0; i < params.length; i += 2) {
                String key = params[i].toString();
                Object value = params[i + 1];
                if (value == null) {
                    continue;
                }
                formparams.add(new BasicNameValuePair(key, value.toString()));
            }
            return new UrlEncodedFormEntity(formparams, "UTF-8");
        } catch (Exception ex) {
            return null;
        }
    }

    private JsonElement execute(String path, Object... params) throws BaseException {
        UrlEncodedFormEntity parameters;
        parameters = createParams(params);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(path);
        post.setEntity(parameters);

        try (CloseableHttpResponse response = client.execute(post)) {

            int httpCode = response.getStatusLine().getStatusCode();
            if (httpCode != 200) {
                throw new HttpException("Http request exception , code : " + httpCode, httpCode);
            }

            String entity = EntityUtils.toString(response.getEntity());
            JsonObject json = new JsonParser().parse(entity).getAsJsonObject();

            JsonObject returnJson = json.get("return").getAsJsonObject();
            if (returnJson.get("status").getAsInt() != 200) {
                throw new ApiException(returnJson.get("message").getAsString(), returnJson.get("status").getAsInt());
            }
            return json.get("entries");

        } catch (IOException e1) {
            throw new HttpException("Http Request Exception", 0);
        } catch (ApiException e2) {
            throw e2;
        }
    }

    /*
       Send
    */

    public List<SendResult> send(String sender, List<String> receptors, String message, MessageType type, long date, List<String> localIds) throws BaseException {

        JsonArray entry = execute(getApiPath("sms/send"),
                "sender", sender,
                "receptor", StringUtils.join(",", receptors),
                "message", message,
                "type", type.getValue(),
                "date", date,
                "localid", localIds == null ? "" : StringUtils.join(",", localIds)
        ).getAsJsonArray();

        List<SendResult> list = new ArrayList<>();
        for (int i = 0; i < entry.size(); i++) {
            JsonObject json = entry.get(i).getAsJsonObject();
            SendResult result = new SendResult(json);
            list.add(result);
        }
        return list;
    }

    public SendResult send(String sender, String receptor, String message) throws BaseException {
        return send(sender, receptor, message, MessageType.MobileMemory, 0);
    }

    public SendResult send(String sender, String receptor, String message, MessageType type, long date) throws BaseException {
        return send(sender, Arrays.asList(receptor), message, type, date).get(0);
    }

    public List<SendResult> send(String sender, List<String> receptors, String message) throws BaseException {
        return send(sender, receptors, message, MessageType.MobileMemory, 0);
    }

    public List<SendResult> send(String sender, List<String> receptors, String message, MessageType type, long date) throws BaseException {
        return send(sender, receptors, message, type, date, null);
    }

    public SendResult send(String sender, String receptor, String message, MessageType type, long date, String localId) throws BaseException {
        List<String> receptors = Arrays.asList(receptor);
        List<String> localIds = Arrays.asList(localId);
        return send(sender, receptors, message, MessageType.MobileMemory, 0, localIds).get(0);
    }

    public SendResult send(String sender, String receptor, String message, String localId) throws BaseException {
        return send(sender, receptor, message, MessageType.MobileMemory, 0, localId);
    }


    /*
      SendArray
    */

    public List<SendResult> sendArray(List<String> senders, List<String> receptors, List<String> messages, List<MessageType> types, long date, List<String> localIds) throws BaseException {
        String path = getApiPath("sms/sendarray");
        JsonArray jsonReceptors = new JsonArray();
        JsonArray jsonSenders = new JsonArray();
        JsonArray jsonMessages = new JsonArray();
        JsonArray jsonTypes = new JsonArray();
        for (int i = 0; i < messages.size(); i++) {
            jsonReceptors.add(new JsonPrimitive(receptors.get(i)));
            jsonSenders.add(new JsonPrimitive(senders.get(i)));
            jsonMessages.add(new JsonPrimitive(messages.get(i)));
            jsonTypes.add(new JsonPrimitive(types.get(i).getValue()));
        }
        JsonArray entry = execute(path, "sender", jsonSenders.toString(), "receptor", jsonReceptors.toString(), "message", jsonMessages.toString(), "date", date, "type", jsonTypes.toString(), "localids", localIds).getAsJsonArray();
        List<SendResult> list = new ArrayList<>();
        for (int i = 0; i < entry.size(); i++) {
            JsonObject json = entry.get(i).getAsJsonObject();
            SendResult result = new SendResult(json);
            list.add(result);
        }
        return list;
    }

    public List<SendResult> sendArray(List<String> senders, List<String> receptors, List<String> messages) throws BaseException {
        return sendArray(senders, receptors, messages, null);
    }

    public List<SendResult> sendArray(List<String> senders, List<String> receptors, List<String> messages, String localId) throws BaseException {
        List<MessageType> types = new ArrayList<>();
        List<String> localIds = new ArrayList<>();
        for (int i = 0; i < receptors.size(); i++) {
            types.add(MessageType.MobileMemory);
            localIds.add(localId);
        }

        return sendArray(senders, receptors, messages, types, 0, localIds);
    }

    public List<SendResult> sendArray(List<String> senders, List<String> receptors, List<String> messages, List<MessageType> types, long date, String localId) throws BaseException {
        List<String> localIds = new ArrayList<>();
        for (int i = 0; i < receptors.size(); i++) {
            localIds.add(localId);
        }
        return sendArray(senders, receptors, messages, types, date, localIds);
    }

    public List<SendResult> sendArray(String sender, List<String> receptors, List<String> messages) throws BaseException {
        return sendArray(sender, receptors, messages, null);
    }

    public List<SendResult> sendArray(String sender, List<String> receptors, List<String> messages, String localId) throws BaseException {
        List<MessageType> types = new ArrayList<>();
        for (int i = 0; i < types.size(); i++) {
            types.add(MessageType.MobileMemory);
        }
        List<String> senders = new ArrayList<>();
        for (int i = 0; i < receptors.size(); i++) {
            senders.add(sender);
        }
        return sendArray(senders, receptors, messages, types, 0, localId);
    }

    public List<SendResult> sendArray(String sender, List<String> receptors, List<String> messages, List<MessageType> types, long date, String localId) throws BaseException {
        List<String> senders = new ArrayList<>();
        List<String> localIds = new ArrayList<>();
        for (int i = 0; i < receptors.size(); i++) {
            senders.add(sender);
            localIds.add(localId);
        }

        return sendArray(senders, receptors, messages, types, date, localIds);
    }



    /*
      Status
    */

    public List<StatusResult> status(List<Long> messageId) throws BaseException {
        String path = getApiPath("sms/status");
        String id = StringUtils.join(",", messageId);
        JsonArray array = execute(path, "messageid", id).getAsJsonArray();
        List<StatusResult> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JsonObject json = array.get(i).getAsJsonObject();
            StatusResult result = new StatusResult(json);
            list.add(result);
        }
        return list;
    }

    public StatusResult status(Long messageId) throws BaseException {
        return status(Arrays.asList(messageId)).get(0);
    }


    /*
      StatusLocalMessageIdResult
    */

    public List<StatusLocalMessageIdResult> statusLocalMessageId(List<Long> localIds) throws BaseException {
        String path = getApiPath("sms/statuslocalmessageid");
        String id = StringUtils.join(",", localIds);
        JsonArray array = execute(path, "localid", id).getAsJsonArray();
        List<StatusLocalMessageIdResult> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JsonObject json = array.get(i).getAsJsonObject();
            list.add(new StatusLocalMessageIdResult(json));
        }
        return list;
    }

    public StatusLocalMessageIdResult statusLocalMessageId(Long localId) throws BaseException {
        return statusLocalMessageId(Arrays.asList(localId)).get(0);
    }

    /*
      Select
    */
    public List<SendResult> select(List<Long> ids) throws BaseException {
        String path = getApiPath("sms/select");
        String massageids = StringUtils.join(",", ids);
        JsonArray array = execute(path, "messageid", massageids).getAsJsonArray();
        List<SendResult> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JsonObject json = array.get(i).getAsJsonObject();
            SendResult result = new SendResult(json);
            list.add(result);
        }
        return list;
    }

    public SendResult select(long messageId) throws BaseException {
        List<Long> messageIds = new ArrayList<>();
        messageIds.add(messageId);
        return select(messageIds).get(0);
    }

    /*
      SelectOutbox
    */

    public List<SendResult> selectOutbox(long startDate, long endDate, String sender) throws BaseException {
        String path = getApiPath("sms/selectoutbox");
        JsonArray array = execute(path, "startdate", startDate, "enddate", endDate, "sender", sender).getAsJsonArray();
        List<SendResult> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JsonObject json = array.get(i).getAsJsonObject();
            list.add(new SendResult(json));
        }
        return list;
    }

    public List<SendResult> selectOutbox(long startDate) throws BaseException {
        return selectOutbox(startDate, 0, "");
    }

    public List<SendResult> selectOutbox(long startDate, long endDate) throws BaseException {
        return selectOutbox(startDate, endDate, "");
    }



    /*
      LatestOutbox
    */

    public List<SendResult> latestOutbox(Long pageSize, String sender) throws BaseException {
        String path = getApiPath("sms/latestoutbox");
        JsonArray array = execute(path, "pagesize", pageSize, "sender", sender).getAsJsonArray();
        List<SendResult> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JsonObject json = array.get(i).getAsJsonObject();
            SendResult result = new SendResult(json);
            list.add(result);
        }
        return list;
    }

    public List<SendResult> latestOutbox() throws BaseException {
        return latestOutbox(3000L, "");
    }

    public List<SendResult> latestOutbox(Long pageSize) throws BaseException {
        return latestOutbox(pageSize, "");
    }



    /*
      CountOutbox
    */

    public CountOutboxResult countOutbox(long startDate, long endDate, int status) throws BaseException {
        String path = getApiPath("sms/countoutbox");
        JsonObject entry = execute(path, "startdate", startDate, "enddate", endDate, "status", status).getAsJsonArray().get(0).getAsJsonObject();
        return new CountOutboxResult(entry);
    }

    public CountOutboxResult countOutbox(long startDate) throws BaseException {
        return countOutbox(startDate, 0, 0);
    }

    public CountOutboxResult countOutbox(long startDate, long endDate) throws BaseException {
        return countOutbox(startDate, endDate, 0);
    }

    /*
      Cancel
    */

    public List<StatusResult> cancel(List<Long> messageIds) throws BaseException {
        String path = getApiPath("sms/cancel");
        String id = StringUtils.join(",", messageIds);
        JsonArray array = execute(path, "messageid", id).getAsJsonArray();
        List<StatusResult> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JsonObject json = array.get(i).getAsJsonObject();
            StatusResult result = new StatusResult(json);
        }
        return list;
    }

    public StatusResult cancel(Long messageId) throws BaseException {
        return cancel(Arrays.asList(messageId)).get(0);
    }

    /*
      Receive
    */
    public List<ReceiveResult> receive(String lineNumber, int isRead) throws BaseException {
        String path = getApiPath("sms/receive");
        JsonElement res = execute(path, "linenumber", lineNumber, "isread", isRead);
        if (res.isJsonNull()) {
            return new ArrayList<>();
        }
        JsonArray array = res.getAsJsonArray();
        List<ReceiveResult> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JsonObject json = array.get(i).getAsJsonObject();
            list.add(new ReceiveResult(json));
        }
        return list;
    }

    /*
      CountInbox
    */


    public CountInboxResult countInbox(long startDate, long endDate, String lineNumber, int isRead) throws BaseException {
        String path = getApiPath("sms/countinbox");
        JsonObject entry = execute(path, "startdate", startDate, "enddate", endDate, "linenumber", lineNumber, "isread", isRead).getAsJsonArray().get(0).getAsJsonObject();
        return new CountInboxResult(entry);
    }

    public CountInboxResult countInbox(long startDate, String lineNumber) throws BaseException {
        return countInbox(startDate, 0, lineNumber, 0);
    }

    public CountInboxResult countInbox(long startDate, long endDate, String lineNumber) throws BaseException {
        return countInbox(startDate, endDate, lineNumber, 0);
    }


    /*
      SendByPostalCode
    */


    public List<SendResult> sendByPostalCode(long postalCode, String sender, String message, long mciStartIndex, long mciCount, long mtnStartIndex, long mtnCount, long date) throws BaseException {
        String path = getApiPath("sms/sendpostalcode");
        JsonArray entry = execute(path, "postalcode", postalCode, "sender", sender, "message", message, "mcistartIndex", mciStartIndex, "mcicount", mciCount, "mtnstartindex", mtnStartIndex, "mtncount", mtnCount, "date", date).getAsJsonArray();
        List<SendResult> list = new ArrayList<>();
        for (int i = 0; i < entry.size(); i++) {
            JsonObject json = entry.get(i).getAsJsonObject();
            SendResult result = new SendResult(json);
            list.add(result);
        }
        return list;
    }

    public List<SendResult> sendByPostalCode(long postalCode, String sender, String message, long mciStartIndex, long mciCount, long mtnStartIndex, long mtnCount) throws BaseException {
        return sendByPostalCode(postalCode, sender, message, mciStartIndex, mciCount, mtnStartIndex, mtnCount, 0);
    }

    /*
      CountPostalCode
    */

    public List<CountPostalCodeResult> countPostalCode(Long postalCode) throws BaseException {
        String path = getApiPath("sms/countpostalcode");
        JsonArray array = execute(path, "postalcode", postalCode).getAsJsonArray();
        List<CountPostalCodeResult> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JsonObject entry = array.get(i).getAsJsonObject();
            list.add(new CountPostalCodeResult(entry));
        }
        return list;
    }


    /*
      AccountInfo
    */
    public AccountInfoResult accountInfo() throws BaseException {
        String path = getApiPath("account/info");
        JsonObject json = execute(path).getAsJsonObject();
        return new AccountInfoResult(json);
    }


    /*
      AccountConfig
    */
    public AccountConfigResult accountConfig(String apiLogs, String dailyReport, String debugMode, String defaultSender, int minCreditAlarm, String resendFailed) throws BaseException {
        String path = getApiPath("account/config");
        JsonObject json = execute(path, "apilogs", dailyReport, "dailyreport", debugMode, "debugmode", debugMode, "defaultsender", defaultSender, "mincreditalarm", minCreditAlarm, "mincreditalarm", resendFailed).getAsJsonObject();
        return new AccountConfigResult(json);
    }

    /*
       VerifyLookup
     */

    public SendResult verifyLookup(String receptor, String token, String token2, String token3, String template) throws BaseException {
        String path = getApiPath("verify/lookup");
        JsonArray array = execute(path, "receptor", receptor, "token", token, "token2", token2, "token3", token3, "template", template).getAsJsonArray();
        JsonObject json = array.get(0).getAsJsonObject();
        return new SendResult(json);
    }
    
     public SendResult verifyLookup(String receptor, String token, String token2, String token3, String template,List<PairValue> params) throws BaseException {
        String path = getApiPath("verify/lookup");
        String token10=null;
        String token20=null;     
        for (PairValue Key : params) {
            if (null != Key.getKey()) switch (Key.getKey()) {
                case "token10":
                    token10 = Key.getValue();
                    break;
                case "token20":
                    token20 = Key.getValue();
                    break;
            }
        }
        JsonArray array = execute(path, "receptor", receptor, "token", token, "token2", token2, "token3", token3, "template", template,"token10",token10,"token20",token20).getAsJsonArray();
        JsonObject json = array.get(0).getAsJsonObject();
        return new SendResult(json);
    }

    public SendResult verifyLookup(String receptor, String token, String template) throws BaseException {
        return verifyLookup(receptor, token, "", "", template);
    }

    public List<SendResult> CallMakeTTS(String message,List<String> receptors,Long date, List<String> localIds) throws BaseException {
        JsonArray entry = execute(getApiPath("call/maketts"),
                "receptor", StringUtils.join(",", receptors),
                "message", message,                
                "date", date,
                "localid", localIds == null ? "" : StringUtils.join(",", localIds)
        ).getAsJsonArray();

        List<SendResult> list = new ArrayList<>();
        for (int i = 0; i < entry.size(); i++) {
            JsonObject json = entry.get(i).getAsJsonObject();
            SendResult result = new SendResult(json);
            list.add(result);
        }
        return list;
    }
    
    public SendResult CallMakeTTS(String message,String receptor) throws BaseException {
        List<String> receptors = new ArrayList<>();
        receptors.add(receptor);
        return CallMakeTTS(message,receptors).get(0);
    }
    
     public List<SendResult> CallMakeTTS(String message,List<String> receptor) throws BaseException {
       return CallMakeTTS(message,receptor,null,null);
    }
     
    public List<SendResult> CallMakeTTS(String message,String receptor,Long date) throws BaseException {
        List<String> receptors = new ArrayList<>();
        receptors.add(receptor);
       return CallMakeTTS(message,receptors,date);
    }
    
    public List<SendResult> CallMakeTTS(String message,List<String> receptor,Long date) throws BaseException {
       return CallMakeTTS(message,receptor,date,null);
    }
    
     public List<SendResult> CallMakeTTS(String message,List<String> receptor,List<String> localId) throws BaseException {
       return CallMakeTTS(message,receptor,null,localId);
    }
     
     public List<SendResult> CallMakeTTS(String message,List<String> receptor,String localId) throws BaseException {
         List<String> localIds = new ArrayList<>();
        localIds.add(localId);
       return CallMakeTTS(message,receptor,null,localIds);
    }
  
}
