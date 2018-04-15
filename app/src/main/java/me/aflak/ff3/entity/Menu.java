package me.aflak.ff3.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    @SerializedName("price") private float price;
    @SerializedName("food_type") private List<FoodType> types;

    public Menu(float price) {
        this.price = price;
        this.types = new ArrayList<>();
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<FoodType> getTypes() {
        return types;
    }

    public void addType(FoodType type) {
        this.types.add(type);
    }
}
