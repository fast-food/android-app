package me.aflak.ff3.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Omar on 14/12/2017.
 */

public class Food {
    @SerializedName("name") private String name;
    @SerializedName("price") private float price;

    public Food(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
