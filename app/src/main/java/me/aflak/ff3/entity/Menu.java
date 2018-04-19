package me.aflak.ff3.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    @SerializedName("id") private int id;
    @SerializedName("price") private float price;
    @SerializedName("food_type") private List<FoodType> types;

    public Menu() {
        this.id = -1;
        this.price = -1;
        this.types = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
