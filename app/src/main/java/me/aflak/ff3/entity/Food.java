package me.aflak.ff3.entity;

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
}
