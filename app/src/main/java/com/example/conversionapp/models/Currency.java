package com.example.conversionapp.models;

import com.google.gson.annotations.SerializedName;

public class Currency {
    @SerializedName("currency_code")
    private String currencyCode;
    @SerializedName("unit_value")
    private int unitValue;
    @SerializedName("buying_rate")
    private float buyingRate;
    @SerializedName("median_rate")
    private float medianRate;
    @SerializedName("selling_rate")
    private float sellingRate;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public int getUnitValue() {
        return unitValue;
    }

    public float getMedianRate() {
        return medianRate;
    }

    public float getBuyingRate() {
        return buyingRate;
    }

    public float getSellingRate() {
        return sellingRate;
    }

    @Override
    public String toString(){
        return currencyCode;
    }
}
