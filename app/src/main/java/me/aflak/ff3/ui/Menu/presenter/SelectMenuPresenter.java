package me.aflak.ff3.ui.Menu.presenter;

import android.app.Activity;

import me.aflak.ff3.entity.Food;
import me.aflak.ff3.entity.FoodType;

public interface SelectMenuPresenter {
    void onCreate(Activity activity);
    void onFoodTypeClick(FoodType foodType);
    void selectFood(Food food);
}
