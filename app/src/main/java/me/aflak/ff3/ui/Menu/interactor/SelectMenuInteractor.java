package me.aflak.ff3.ui.Menu.interactor;


import java.util.List;

import me.aflak.ff3.entity.Food;
import me.aflak.ff3.entity.FoodType;
import me.aflak.ff3.entity.Menu;

public interface SelectMenuInteractor {
    Menu parseMenu(String str);
    List<Food> getFoodList(FoodType foodType);
}
