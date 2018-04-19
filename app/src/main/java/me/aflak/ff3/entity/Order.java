package me.aflak.ff3.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Order {
    @SerializedName("menu_id") private int menuId;
    @SerializedName("food_ids") private List<Integer> foodIds;

    public Order(){
        menuId = -1;
        foodIds = new ArrayList<>();
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public List<Integer> getFoodIds() {
        return foodIds;
    }

    public void addFoodId(int id) {
        this.foodIds.add(id);
    }
}
