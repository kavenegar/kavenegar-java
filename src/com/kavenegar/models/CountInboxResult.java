/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kavenegar.models;

import com.kavenegar.models.*;

/**
 *
 * @author mohsen
 */
public class CountInboxResult {
    long startdate;
    long enddate;
    long sumcount; 
    
    public long getStartDate() {
       return startdate;
    }
    public void setStartDate(long startdate) {
        this.startdate = startdate;
    }
    
    public long getEndDate() {
       return enddate;
    }
    public void setEndDate(long enddate) {
        this.enddate = enddate;
    }
    
    public long getSumCount() {
       return sumcount;
    }
    public void setSumCount(long sumcount) {
        this.sumcount = sumcount;
    }
}