/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kavenegar;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;


import com.kavenegar.enums.*;
import com.kavenegar.excepctions.*;
import com.kavenegar.utils.*;
import com.kavenegar.models.*;

/**
 *
 * @author Kavenegar
 */
public class KavenegarApi {

 String apikey;
 static final String apipath = "https://api.kavenegar.com/v1/%s/%s/%s.%s";
 public KavenegarApi(String _apikey) {
  this.apikey = _apikey;
 }

 private String getApiPath(String base, String method, String output) {
  return String.format(apipath, apikey, base, method, output);
 }

 private JsonElement Execute(String path, Object...params) throws BaseException {
  UrlEncodedFormEntity parameters;
  parameters = ApiHelper.createParams(params);
  HttpClient client = new DefaultHttpClient();
  HttpPost post = new HttpPost(path);
  post.setEntity(parameters);
  HttpResponse response;
  try {
   response = client.execute(post);
  } catch (Exception ex) {
   throw new HttpException("Http Request Exception", 0);
  }
  JsonObject result = ApiHelper.getResult(response);
  if (result == null) {
   int httpcode = response.getStatusLine().getStatusCode();
   if (httpcode != 200) {
    throw new HttpException("Http request exception , code : " + httpcode, httpcode);
   }
  } else {
   JsonObject return_json = result.get("return").getAsJsonObject();
   if (200 == return_json.get("status").getAsInt()) {} else {
    throw new ApiException(return_json.get("message").getAsString(), return_json.get("status").getAsInt());
   }
  }
  return result.get("entries");
 }

 /*
    Send
 */
 public SendResult Send(String sender, String receptor, String message) throws BaseException {
  return Send(sender, receptor, message, MessageType.MobileMemory, 0);
 }
 public SendResult Send(String sender, String receptor, String message, MessageType type, long date) throws BaseException {
  return Send(sender, new String[] {
   receptor
  }, message, type, date).get(0);
 }
 public List < SendResult > Send(String sender, String[] receptors, String message) throws BaseException {
  return Send(sender, receptors, message, MessageType.MobileMemory, 0);
 }
 public List < SendResult > Send(String sender, String[] receptors, String message, MessageType type, long date) throws BaseException {
  return Send(sender, receptors, message, type, date, null);
 }
 public SendResult Send(String sender, String receptor, String message, MessageType type, long date, String localid) throws BaseException {
  return Send(sender, new String[] {
   receptor
  }, message, MessageType.MobileMemory, 0, new String[] {
   localid
  }).get(0);
 }
 public SendResult Send(String sender, String receptor, String message, String localid) throws BaseException {
  return Send(sender, receptor, message, MessageType.MobileMemory, 0, localid);
 }
 public List < SendResult > Send(String sender, String[] receptor, String message, MessageType type, long date, String[] localid) throws BaseException {
  String path = getApiPath("sms", "send", "json");
  JsonArray entry = Execute(path, "sender", sender, "receptor", StringUtils.Join(",", receptor), "message", message, "type", type.getValue(), "date", date, StringUtils.Join(",", localid)).getAsJsonArray();
  List < SendResult > list = new ArrayList < > ();
  for (int i = 0; i < entry.size(); i++) {
   JsonObject obj = entry.get(i).getAsJsonObject();
   SendResult result = new SendResult();
   result.setCost(obj.get("cost").getAsInt());
   result.setDate(obj.get("date").getAsLong());
   result.setMessageId(obj.get("messageid").getAsLong());
   result.setMessage(obj.get("message").getAsString());
   result.setReceptor(obj.get("receptor").getAsString());
   result.setStatus(obj.get("status").getAsInt());
   result.setStatusText(obj.get("statustext").getAsString());
   list.add(result);
  }
  return list;
 }

 /*
   SendArray
 */
 public List < SendResult > SendArray(String[] senders, String[] receptors, String[] messages) throws BaseException {
  return SendArray(senders, receptors, messages, null);
 }
 public List < SendResult > SendArray(String[] senders, String[] receptors, String[] messages, String localid) throws BaseException {
  MessageType[] types = new MessageType[receptors.length];
  for (int i = 0; i < receptors.length; i++) {
   types[i] = MessageType.MobileMemory;
  }
  String[] localids = new String[receptors.length];
  for (int i = 0; i < receptors.length; i++) {
   localids[i] = localid;
  }
  return SendArray(senders, receptors, messages, types, 0, localids);
 }
 public List < SendResult > SendArray(String[] senders, String[] receptors, String[] messages, MessageType[] types, long date, String localid) throws BaseException {
  String[] localids = new String[receptors.length];
  for (int i = 0; i < receptors.length; i++) {
   localids[i] = localid;
  }
  return SendArray(senders, receptors, messages, types, 0, localids);
 }
 public List < SendResult > SendArray(String sender, String[] receptors, String[] messages) throws BaseException {
  return SendArray(sender, receptors, messages, null);
 }
 public List < SendResult > SendArray(String sender, String[] receptors, String[] messages, String localid) throws BaseException {
  MessageType[] types = new MessageType[receptors.length];
  for (int i = 0; i < types.length; i++) {
   types[i] = MessageType.MobileMemory;
  }
  String[] senders = new String[receptors.length];
  for (int i = 0; i < receptors.length; i++) {
   senders[i] = sender;
  }
  return SendArray(senders, receptors, messages, types, 0, localid);
 }
 public List < SendResult > SendArray(String sender, String[] receptors, String[] messages, MessageType[] types, long date, String localid) throws BaseException {
  String[] senders = new String[receptors.length];
  for (int i = 0; i < receptors.length; i++) {
   senders[i] = sender;
  }
  String[] localids = new String[receptors.length];
  for (int i = 0; i < receptors.length; i++) {
   localids[i] = localid;
  }
  return SendArray(senders, receptors, messages, types, 0, localids);
 }
 public List < SendResult > SendArray(String[] senders, String[] receptors, String[] messages, MessageType[] type, long date, String[] localids) throws BaseException {
  String path = getApiPath("sms", "sendarray", "json");
  JsonArray json_receptors = new JsonArray();
  JsonArray json_senders = new JsonArray();
  JsonArray json_messages = new JsonArray();
  JsonArray json_types = new JsonArray();
  for (int i = 0; i < messages.length; i++) {
   json_receptors.add(new JsonPrimitive(receptors[i]));
   json_senders.add(new JsonPrimitive(senders[i]));
   json_messages.add(new JsonPrimitive(messages[i]));
   json_types.add(new JsonPrimitive(type[i].getValue()));
  }
  JsonArray entry = Execute(path, "sender", json_senders.toString(), "receptor", json_receptors.toString(), "message", json_messages.toString(), "date", date, "type", json_types.toString(), "localids", localids).getAsJsonArray();
  List < SendResult > list = new ArrayList < > ();
  for (int i = 0; i < entry.size(); i++) {
   JsonObject obj = entry.get(i).getAsJsonObject();
   SendResult result = new SendResult();
   result.setCost(obj.get("cost").getAsInt());
   result.setDate(obj.get("date").getAsLong());
   result.setMessageId(obj.get("messageid").getAsLong());
   result.setMessage(obj.get("message").getAsString());
   result.setReceptor(obj.get("receptor").getAsString());
   result.setStatus(obj.get("status").getAsInt());
   result.setStatusText(obj.get("statustext").getAsString());
   list.add(result);
  }
  return list;
 }

 /*
   Status
 */
 public List < StatusResult > Status(Long[] messageid) throws BaseException {
  String path = getApiPath("sms", "status", "json");
  String id = StringUtils.Join(",", messageid);
  JsonArray array = Execute(path, "messageid", id).getAsJsonArray();
  List < StatusResult > list = new ArrayList < > ();
  for (int i = 0; i < array.size(); i++) {
   StatusResult result = new StatusResult();
   JsonObject entry = array.get(i).getAsJsonObject();
   result.setMessageId(entry.get("messageid").getAsInt());
   result.setStatus(MessageStatus.valueOf(entry.get("status").getAsInt()));
   result.setStatusText(entry.get("statustext").getAsString());
   list.add(result);
  }
  return list;
 }
 public StatusResult Status(Long messageid) throws BaseException {
  return Status(new Long[] {
   messageid
  }).get(0);
 }

 /*
   StatusLocalMessageIdResult
 */
 public List < StatusLocalMessageIdResult > StatusLocalMessageId(Long[] localids) throws BaseException {
  String path = getApiPath("sms", "statuslocalmessageid", "json");
  String id = StringUtils.Join(",", localids);
  JsonArray array = Execute(path, "localid", id).getAsJsonArray();
  List < StatusLocalMessageIdResult > list = new ArrayList < > ();
  for (int i = 0; i < array.size(); i++) {
   StatusLocalMessageIdResult result = new StatusLocalMessageIdResult();
   JsonObject entry = array.get(i).getAsJsonObject();
   result.setMessageId(entry.get("messageid").getAsInt());
   result.setStatus(MessageStatus.valueOf(entry.get("status").getAsInt()));
   result.setStatusText(entry.get("statustext").getAsString());
   list.add(result);
  }
  return list;
 }
 public StatusLocalMessageIdResult StatusLocalMessageId(Long localid) throws BaseException {
  return StatusLocalMessageId(new Long[] {
   localid
  }).get(0);
 }

 /*
   Select
 */
 public List < SendResult > Select(Long[] ids) throws BaseException {
  String path = getApiPath("sms", "select", "json");
  String massageids = StringUtils.Join(",", ids);
  JsonArray array = Execute(path, "messageid", massageids).getAsJsonArray();
  List < SendResult > list = new ArrayList < > ();
  for (int i = 0; i < array.size(); i++) {
   SendResult result = new SendResult();
   JsonObject entry = array.get(i).getAsJsonObject();
   result.setCost(entry.get("cost").getAsInt());
   result.setDate(entry.get("date").getAsLong());
   result.setMessageId(entry.get("messageid").getAsLong());
   result.setMessage(entry.get("message").getAsString());
   result.setReceptor(entry.get("receptor").getAsString());
   result.setStatus(entry.get("status").getAsInt());
   result.setStatusText(entry.get("statustext").getAsString());
   list.add(result);
  }
  return list;
 }
 public SendResult Select(long messageid) throws BaseException {
  return Select(new Long[] {
   messageid
  }).get(0);
 }

 /*
   SelectOutbox
 */
 public List < SendResult > SelectOutbox(long startdate) throws BaseException {
  return SelectOutbox(startdate, 0, "");
 }
 public List < SendResult > SelectOutbox(long startdate, long enddate) throws BaseException {
  return SelectOutbox(startdate, enddate, "");
 }
 public List < SendResult > SelectOutbox(long startdate, long enddate, String sender) throws BaseException {
  String path = getApiPath("sms", "selectoutbox", "json");
  JsonArray array = Execute(path, "startdate", startdate, "enddate", enddate, "sender", sender).getAsJsonArray();
  List < SendResult > list = new ArrayList < > ();
  for (int i = 0; i < array.size(); i++) {
   SendResult result = new SendResult();
   JsonObject entry = array.get(i).getAsJsonObject();
   result.setCost(entry.get("cost").getAsInt());
   result.setDate(entry.get("date").getAsLong());
   result.setMessageId(entry.get("messageid").getAsLong());
   result.setMessage(entry.get("message").getAsString());
   result.setReceptor(entry.get("receptor").getAsString());
   result.setStatus(entry.get("status").getAsInt());
   result.setStatusText(entry.get("statustext").getAsString());
   list.add(result);
  }
  return list;
 }

 /*
   LatestOutbox
 */
 public List < SendResult > LatestOutbox() throws BaseException {
  long pagesize = 3000;
  return LatestOutbox(pagesize, "");
 }
 public List < SendResult > LatestOutbox(Long pagesize) throws BaseException {
  return LatestOutbox(pagesize, "");
 }
 public List < SendResult > LatestOutbox(Long pagesize, String sender) throws BaseException {
  String path = getApiPath("sms", "latestoutbox", "json");
  JsonArray array = Execute(path, "pagesize", pagesize, "sender", sender).getAsJsonArray();
  List < SendResult > list = new ArrayList < > ();
  for (int i = 0; i < array.size(); i++) {
   SendResult result = new SendResult();
   JsonObject entry = array.get(i).getAsJsonObject();
   result.setMessageId(entry.get("messageid").getAsLong());
   result.setMessage(entry.get("message").getAsString());
   result.setSender(entry.get("sender").getAsString());
   result.setReceptor(entry.get("receptor").getAsString());
   result.setStatus(entry.get("status").getAsInt());
   result.setStatusText(entry.get("statustext").getAsString());
   result.setCost(entry.get("cost").getAsInt());
   result.setDate(entry.get("date").getAsLong());
   list.add(result);
  }
  return list;
 }

 /*
   CountOutbox
 */
 public CountOutboxResult CountOutbox(long startdate) throws BaseException {
  return CountOutbox(startdate, 0, 0);
 }
 public CountOutboxResult CountOutbox(long startdate, long enddate) throws BaseException {
  return CountOutbox(startdate, enddate, 0);
 }
 public CountOutboxResult CountOutbox(long startdate, long enddate, int status) throws BaseException {
  String path = getApiPath("sms", "countoutbox", "json");
  JsonObject entry = Execute(path, "startdate", startdate, "enddate", enddate, "status", status).getAsJsonArray().get(0).getAsJsonObject();
  CountOutboxResult result = new CountOutboxResult();
  result.setStartDate(entry.get("startdate").getAsLong());
  result.setEndDate(entry.get("enddate").getAsLong());
  result.setSumPart(entry.get("sumpart").getAsLong());
  result.setSumCount(entry.get("sumcount").getAsLong());
  result.setCost(entry.get("cost").getAsLong());
  return result;
 }

 /*
   Cancel
 */
 public List < StatusResult > Cancel(Long[] messageids) throws BaseException {
  String path = getApiPath("sms", "cancel", "json");
  String id = StringUtils.Join(",", messageids);
  JsonArray array = Execute(path, "messageid", id).getAsJsonArray();
  List < StatusResult > list = new ArrayList < > ();
  for (int i = 0; i < array.size(); i++) {
   StatusResult result = new StatusResult();
   JsonObject entry = array.get(i).getAsJsonObject();
   result.setMessageId(entry.get("messageid").getAsInt());
   result.setStatus(MessageStatus.valueOf(entry.get("status").getAsInt()));
   result.setStatusText(entry.get("statustext").getAsString());
  }
  return list;
 }
 public StatusResult Cancel(Long messageid) throws BaseException {
  return Cancel(new Long[] {
   messageid
  }).get(0);
 }

 /*
   Receive
 */
 public List < ReciveResult > Receive(String linenumber, int isread) throws BaseException {
   String path = getApiPath("sms", "receive", "json");
   JsonElement res = Execute(path, "linenumber", linenumber, "isread", isread);
   if (res.isJsonNull()) {
    return new ArrayList < > ();
   }
   JsonArray array = res.getAsJsonArray();
   List < ReciveResult > list = new ArrayList < > ();
   for (int i = 0; i < array.size(); i++) {
    JsonObject json_obj = array.get(i).getAsJsonObject();
    ReciveResult result = new ReciveResult();
    result.setMessageId(json_obj.get("messageid").getAsInt());
    result.setDate(json_obj.get("date").getAsLong());
    result.setMessage(json_obj.get("message").getAsString());
    result.setSender(json_obj.get("sender").getAsString());
    result.setReceptor(json_obj.get("receptor").getAsString());
    list.add(result);
   }
   return list;
  }
  /*
    CountInbox
  */
 public CountInboxResult CountInbox(long startdate, String linenumber) throws BaseException {
  return CountInbox(startdate, 0, linenumber, 0);
 }
 public CountInboxResult CountInbox(long startdate, long enddate, String linenumber) throws BaseException {
  return CountInbox(startdate, enddate, linenumber, 0);
 }
 public CountInboxResult CountInbox(long startdate, long enddate, String linenumber, int isread) throws BaseException {
  String path = getApiPath("sms", "countinbox", "json");
  JsonObject entry = Execute(path, "startdate", startdate, "enddate", enddate, "linenumber", linenumber, "isread", isread).getAsJsonArray().get(0).getAsJsonObject();
  CountInboxResult result = new CountInboxResult();
  result.setStartDate(entry.get("startdate").getAsLong());
  result.setEndDate(entry.get("enddate").getAsLong());
  result.setSumCount(entry.get("sumcount").getAsLong());
  return result;
 }

 /*
   SendByPostalCode
 */
 public List < SendResult > SendByPostalCode(long postalcode, String sender, String message, long mcistartIndex, long mcicount, long mtnstartindex, long mtncount) throws BaseException {
  return SendByPostalCode(postalcode, sender, message, mcistartIndex, mcicount, mtnstartindex, mtncount, 0);
 }
 public List < SendResult > SendByPostalCode(long postalcode, String sender, String message, long mcistartIndex, long mcicount, long mtnstartindex, long mtncount, long date) throws BaseException {
  String path = getApiPath("sms", "sendpostalcode", "json");
  JsonArray entry = Execute(path, "postalcode", postalcode, "sender", sender, "message", message, "mcistartIndex", mcistartIndex, "mcicount", mtnstartindex, "mtnstartindex", mtncount, "mtncount", "date", date).getAsJsonArray();
  List < SendResult > list = new ArrayList < > ();
  for (int i = 0; i < entry.size(); i++) {
   JsonObject obj = entry.get(i).getAsJsonObject();
   SendResult result = new SendResult();
   result.setCost(obj.get("cost").getAsInt());
   result.setDate(obj.get("date").getAsLong());
   result.setMessageId(obj.get("messageid").getAsLong());
   result.setMessage(obj.get("message").getAsString());
   result.setReceptor(obj.get("receptor").getAsString());
   result.setStatus(obj.get("status").getAsInt());
   result.setStatusText(obj.get("statustext").getAsString());
   list.add(result);
  }
  return list;
 }


 /*
   CountPostalCode
 */
 public List < CountPostalCodeResult > CountPostalCode(long postalcode) throws BaseException {
  String path = getApiPath("sms", "countpostalcode", "json");
  JsonArray array = Execute(path, "postalcode", postalcode).getAsJsonArray();
  List < CountPostalCodeResult > list = new ArrayList < > ();
  for (int i = 0; i < array.size(); i++) {
   CountPostalCodeResult result = new CountPostalCodeResult();
   JsonObject entry = array.get(i).getAsJsonObject();
   result.setSection(entry.get("section").getAsString());
   result.setValue(entry.get("value").getAsLong());
   list.add(result);
  }
  return list;
 }


 /*
   AccountInfo
 */
 public AccountInfoResult AccountInfo() throws BaseException {
  String path;
  path = getApiPath("account", "info", "json");
  JsonObject entry = Execute(path).getAsJsonObject();
  AccountInfoResult result = new AccountInfoResult();
  result.setRemainCredit(entry.get("remaincredit").getAsLong());
  result.setExpireDate(entry.get("expiredate").getAsLong());
  result.setType(entry.get("type").getAsString());
  return result;
 }


 /*
   AccountConfig
 */
 public AccountConfigResult AccountConfig(String apilogs, String dailyreport, String debugmode, String defaultsender, int mincreditalarm, String resendfailed) throws BaseException {
  String path = getApiPath("account", "config", "json");
  JsonObject entry = Execute(path, "apilogs", dailyreport, "dailyreport", debugmode, "debugmode", debugmode, "defaultsender", defaultsender, "mincreditalarm", mincreditalarm, "mincreditalarm", mincreditalarm).getAsJsonObject();
  AccountConfigResult result = new AccountConfigResult();
  result.setApilogs(entry.get("apilogs").getAsString());
  result.setDailyReport(entry.get("dailyreport").getAsString());
  result.setDebugMode(entry.get("debugmode").getAsString());
  result.setDefaultSender(entry.get("DefaultSender").getAsString());
  result.setResendFailed(entry.get("resendfailed").getAsString());
  result.setMinCreditAlarm(entry.get("Mincreditalarm").getAsInt());
  return result;
 }

 /*
    VerifyLookup
  */
 public SendResult VerifyLookup(String receptor, String token, String token2, String token3, String template) throws BaseException {
  String path = getApiPath("verify", "lookup", "json");
   JsonArray array = Execute(path, "receptor", receptor, "token", token, "token2", token2, "token3", token3, "template", template).getAsJsonArray();
   JsonObject entry = array.get(0).getAsJsonObject();
   SendResult result = new SendResult();
   result.setCost(entry.get("cost").getAsInt());
   result.setDate(entry.get("date").getAsLong());
   result.setMessageId(entry.get("messageid").getAsLong());
   result.setMessage(entry.get("message").getAsString());
   result.setReceptor(entry.get("receptor").getAsString());
   result.setStatus(entry.get("status").getAsInt());
   result.setStatusText(entry.get("statustext").getAsString());
  return result;
 }
 public SendResult VerifyLookup(String receptor, String token, String template) throws BaseException {
  return VerifyLookup(receptor, token, "", "", template);
 }

}