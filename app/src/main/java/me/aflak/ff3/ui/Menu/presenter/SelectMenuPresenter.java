package me.aflak.ff3.ui.Menu.presenter;

import android.content.Intent;

import me.aflak.ff3.entity.Food;
import me.aflak.ff3.entity.FoodType;

public interface SelectMenuPresenter {
    void onCreate(Intent intent);
    void onFoodTypeClick(FoodType foodType);
    void selectFood(Food food);
}
