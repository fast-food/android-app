package me.aflak.ff3.entity;

import com.google.gson.annotations.SerializedName;

public enum FoodType {
    @SerializedName("0") SANDWICH(0),
    @SerializedName("1") EXTRA(1),
    @SerializedName("2") DRINK(2),
    @SerializedName("3") DESERT(3),
    @SerializedName("4") UNDEFINED(4);

    private final int value;
    public int getValue(){
        return value;
    }

    FoodType(int value) {
        this.value = value;
    }
}
