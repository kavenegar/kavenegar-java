/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kavenegar.sdk.models;

import com.google.gson.JsonObject;
import java.util.Objects;

/**
 * Represents the result of a postal code count operation.
 * Contains a message count associated with a specific geographical section.
 * Provides utility methods for validation, comparison, and structured reporting.
 *
  */
public class CountPostalCodeResult {

    private final String section;
    private final Long value;

    public CountPostalCodeResult(JsonObject json) {
        this.section = json.has("section") && !json.get("section").isJsonNull()
                ? json.get("section").getAsString() : "";
        this.value = json.has("value") && !json.get("value").isJsonNull()
                ? json.get("value").getAsLong() : 0L;
    }

    public String getSection() {
        return section;
    }

    public long getValue() {
        return value;
    }

    /**
     * Checks whether the count result has zero value.
     */
    public boolean isEmpty() {
        return value == null || value == 0;
    }

    /**
     * Generates a summary of the postal code count result.
     */
    public String getSummary() {
        return String.format("Section: %s, Message Count: %d", section, value);
    }

    /**
     * Compares if two results belong to the same section.
     */
    public boolean hasSameSection(CountPostalCodeResult other) {
        return other != null && this.section.equalsIgnoreCase(other.section);
    }

    /**
     * Checks whether the message count is above a given threshold.
     */
    public boolean isAboveThreshold(long threshold) {
        return value > threshold;
    }

    /**
     * Provides a normalized score for sorting or ranking purposes.
     * For example, scale the value to a range between 0 and 1 given a max value.
     */
    public double getNormalizedScore(long maxValue) {
        return maxValue > 0 ? (double) value / maxValue : 0.0;
    }

    /**
     * Determines if the section contains a specific keyword (case-insensitive).
     */
    public boolean sectionContains(String keyword) {
        return section != null && keyword != null && section.toLowerCase().contains(keyword.toLowerCase());
    }

    /**
     * Creates a new instance of CountPostalCodeResult with an updated value.
     */
    public CountPostalCodeResult withUpdatedValue(long newValue) {
        JsonObject json = new JsonObject();
        json.addProperty("section", this.section);
        json.addProperty("value", newValue);
        return new CountPostalCodeResult(json);
    }

    /**
     * Returns the result with the higher message count.
     */
    public CountPostalCodeResult max(CountPostalCodeResult other) {
        if (other == null || this.value >= other.value) {
            return this;
        }
        return other;
    }

    /**
     * Returns the result with the lower message count.
     */
    public CountPostalCodeResult min(CountPostalCodeResult other) {
        if (other == null || this.value <= other.value) {
            return this;
        }
        return other;
    }

    @Override
    public String toString() {
        return "CountPostalCodeResult{" +
                "section='" + section + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountPostalCodeResult)) return false;
        CountPostalCodeResult that = (CountPostalCodeResult) o;
        return Objects.equals(section, that.section) &&
               Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(section, value);
    }
}
