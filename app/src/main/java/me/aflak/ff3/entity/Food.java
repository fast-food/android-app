package me.aflak.ff3.entity;

import android.content.Context;

import me.aflak.ff3.R;

/**
 * Created by Omar on 14/12/2017.
 */

public class Food {
    private String name;
    private FoodType type;
    private int id;

    public Food(int id, FoodType type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FoodType getType() {
        return type;
    }

    public void setType(FoodType type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int toString(FoodType type){
        switch (type){
            case SANDWICH:
                return R.string.food_sandwich;
            case DRINK:
                return R.string.food_drink;
            case EXTRA:
                return R.string.food_extra;
            case DESERT:
                return R.string.food_desert;
            default:
                return R.string.food_undefined;
        }
    }

    public static int getImage(FoodType type){
        switch (type){
            case SANDWICH:
                return R.drawable.sandwich;
            case DRINK:
                return R.drawable.drink;
            case EXTRA:
                return R.drawable.extra;
            case DESERT:
                return R.drawable.desert;
            default:
                return -1;
        }
    }
}
