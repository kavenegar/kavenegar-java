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
public class CountOutboxResult extends CountInboxResult{
    long sumpart;
    long cost;

    public long getSumPart() {
       return sumpart;
    }
    public void setSumPart(long sumpart) {
        this.sumpart = sumpart;
    }
    public long getCost() {
       return cost;
    }
    public void setCost(long cost) {
        this.cost = cost;
    }  
}