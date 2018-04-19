package me.aflak.ff3.ui.Menu.view;

import java.util.List;

import me.aflak.ff3.entity.Food;
import me.aflak.ff3.entity.FoodType;
import me.aflak.ff3.entity.Menu;
import me.aflak.ff3.entity.Order;
import me.aflak.ff3.ui.Menu.presenter.SelectMenuPresenterImpl.OnFoodSelectedListener;

public interface SelectMenuView {
    void showMenu(Menu menu);
    void showFoodList(List<Food> foodList, int checkItem, OnFoodSelectedListener listener);
    FoodType getItem(int position);
    void checkItem(int position, Food food);
    void showSelectErrorPopup();
    void navigateToSender(Order order);
}
