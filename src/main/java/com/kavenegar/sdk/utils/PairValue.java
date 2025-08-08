/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kavenegar.sdk.utils;

import java.util.Objects;

/**
 * The code was originally written by Hadi and later developed further by Reza.
 */
public class PairValue {
    private String Key = null;
    private String Value = null;

    public PairValue(String Key, String Value) {
        this.Key = Key;
        this.Value = Value;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String Key) {
        this.Key = Key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String Value) {
        this.Value = Value;
    }

    /**
     * Checks if the stored key matches the provided key.
     */
    public boolean keyEquals(String otherKey) {
        return Objects.equals(this.Key, otherKey);
    }

    /**
     * Checks if the stored value matches the provided value.
     */
    public boolean valueEquals(String otherValue) {
        return Objects.equals(this.Value, otherValue);
    }

    /**
     * Swaps key and value.
     */
    public void swap() {
        String temp = this.Key;
        this.Key = this.Value;
        this.Value = temp;
    }

    /**
     * Returns a string representation in key=value format.
     */
    @Override
    public String toString() {
        return Key + "=" + Value;
    }

    /**
     * Equality check based on both key and value.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PairValue other = (PairValue) obj;
        return Objects.equals(Key, other.Key) && Objects.equals(Value, other.Value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Key, Value);
    }
}
