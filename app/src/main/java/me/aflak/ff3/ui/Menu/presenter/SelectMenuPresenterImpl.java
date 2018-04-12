package me.aflak.ff3.ui.Menu.presenter;

import android.content.Intent;
import android.util.Log;

import java.util.List;

import me.aflak.ff3.entity.Food;
import me.aflak.ff3.entity.FoodType;
import me.aflak.ff3.entity.Menu;
import me.aflak.ff3.ui.Menu.interactor.SelectMenuInteractor;
import me.aflak.ff3.ui.Menu.view.SelectMenuView;

public class SelectMenuPresenterImpl implements SelectMenuPresenter {
    private SelectMenuView selectMenuView;
    private SelectMenuInteractor selectMenuInteractor;

    public SelectMenuPresenterImpl(SelectMenuView selectMenuView, SelectMenuInteractor selectMenuInteractor){
        this.selectMenuView = selectMenuView;
        this.selectMenuInteractor = selectMenuInteractor;
    }

    @Override
    public void onCreate(Intent intent) {
        if(intent.getExtras()!=null){
            String str = intent.getExtras().getString("menu");
            Menu menu = selectMenuInteractor.parseMenu(str);
            selectMenuView.showMenu(menu);
        }
    }

    @Override
    public void onFoodTypeClick(FoodType foodType) {
        List<Food> foodList = selectMenuInteractor.getFoodList(foodType);
        selectMenuView.showFood(foodList);
    }

    @Override
    public void selectFood(Food food) {
    }
}
