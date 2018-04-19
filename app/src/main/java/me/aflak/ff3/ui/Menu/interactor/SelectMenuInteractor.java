package me.aflak.ff3.ui.Menu.interactor;

import android.util.SparseArray;

import java.util.List;

import me.aflak.ff3.entity.Food;
import me.aflak.ff3.entity.FoodType;
import me.aflak.ff3.entity.Menu;
import me.aflak.ff3.entity.Order;

public interface SelectMenuInteractor {
    Menu parseMenu(String str);
    List<Food> getFoodList(FoodType foodType);
    void setFood(int index, Food food);
    SparseArray<Food> getChosenFood();
    int getSelectedFoodIndex(List<Food> foodList, int index);
}
