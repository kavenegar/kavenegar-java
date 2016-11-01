/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kavenegar.models;

import com.kavenegar.models.*;

/**
 *
 * @author Kave
 */
public class AccountConfigResult {

    String apilogs;
    String dailyreport;
    String debugmode;
    String defaultsender;
    int mincreditalarm;
    String resendfailed;

    public String getApilogs() {
        return apilogs;
    }public void setApilogs(String apilogs) {
        this.apilogs=apilogs;
    }
   
    public String getDailyReport() {
        return dailyreport;
    }public void setDailyReport(String dailyreport) {
        this.dailyreport=dailyreport;
    }
    
    public String getDebugMode() {
        return debugmode;
    }public void setDebugMode(String debugmode) {
        this.debugmode=debugmode;
    }
    
    public String getDefaultSender() {
        return defaultsender;
    }public void setDefaultSender(String defaultsender) {
        this.defaultsender=defaultsender;
    }
    public int getMinCreditAlarm() {
        return mincreditalarm;
    }public void setMinCreditAlarm(int mincreditalarm) {
        this.mincreditalarm=mincreditalarm;
    }
    
    public String getResendFailed() {
        return resendfailed;
    }public void setResendFailed(String resendfailed) {
        this.resendfailed=resendfailed;
    }
   


    
}
