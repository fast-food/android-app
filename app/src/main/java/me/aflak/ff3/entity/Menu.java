package me.aflak.ff3.entity;

import com.google.gson.annotations.SerializedName;

public class Menu {
    @SerializedName("size") private MenuSize size;
    @SerializedName("sandwich") private Food sandwich;
    @SerializedName("extra") private Food extra;
    @SerializedName("drink") private Food drink;
    @SerializedName("dessert") private Food dessert;
    @SerializedName("price") private float price;

    public Menu(MenuSize size, Food sandwich, Food extra, Food drink, Food dessert, float price) {
        this.size = size;
        this.sandwich = sandwich;
        this.extra = extra;
        this.drink = drink;
        this.dessert = dessert;
        this.price = price;
    }

    private void updatePrice(){
        int sizeInt = 1;
        if(size==MenuSize.Small){
            sizeInt = 1;
        }
        if(size==MenuSize.Medium){
            sizeInt = 2;
        }
        if(size==MenuSize.Large){
            sizeInt = 3;
        }
        price = sandwich.getPrice()+extra.getPrice()+drink.getPrice()+dessert.getPrice()+sizeInt*0.20f;
    }

    public MenuSize getSize() {
        return size;
    }

    public void setSize(MenuSize size) {
        this.size = size;
        updatePrice();
    }

    public Food getSandwich() {
        return sandwich;
    }

    public void setSandwich(Food sandwich) {
        this.sandwich = sandwich;
        updatePrice();
    }

    public Food getExtra() {
        return extra;
    }

    public void setExtra(Food extra) {
        this.extra = extra;
        updatePrice();
    }

    public Food getDrink() {
        return drink;
    }

    public void setDrink(Food drink) {
        this.drink = drink;
        updatePrice();
    }

    public Food getDessert() {
        return dessert;
    }

    public void setDessert(Food dessert) {
        this.dessert = dessert;
        updatePrice();
    }

    public float getPrice() {
        return price;
    }
}
