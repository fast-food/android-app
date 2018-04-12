package me.aflak.ff3.ui.Menu.view;

import java.util.List;

import me.aflak.ff3.entity.Food;
import me.aflak.ff3.entity.FoodType;
import me.aflak.ff3.entity.Menu;

public interface SelectMenuView {
    void showMenu(Menu menu);
    void showFood(List<Food> foodList);
}
