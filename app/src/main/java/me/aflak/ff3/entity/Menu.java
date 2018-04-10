package me.aflak.ff3.entity;

import com.google.gson.annotations.SerializedName;

public class Menu {
    @SerializedName("size") private MenuSize size;
    @SerializedName("sandwich") private Food sandwich;
    @SerializedName("extra") private Food extra;
    @SerializedName("drink") private Food drink;
    @SerializedName("price") private float price;

    public Menu(MenuSize size, Food sandwich, Food extra, Food drink, float price) {
        this.size = size;
        this.sandwich = sandwich;
        this.extra = extra;
        this.drink = drink;
        this.price = price;
    }

    public MenuSize getSize() {
        return size;
    }

    public void setSize(MenuSize size) {
        this.size = size;
    }

    public Food getSandwich() {
        return sandwich;
    }

    public void setSandwich(Food sandwich) {
        this.sandwich = sandwich;
    }

    public Food getExtra() {
        return extra;
    }

    public void setExtra(Food extra) {
        this.extra = extra;
    }

    public Food getDrink() {
        return drink;
    }

    public void setDrink(Food drink) {
        this.drink = drink;
    }

    public float getPrice() {
        return price;
    }
}
