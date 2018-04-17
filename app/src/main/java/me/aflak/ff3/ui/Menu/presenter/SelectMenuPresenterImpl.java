package me.aflak.ff3.ui.Menu.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import me.aflak.ff3.R;
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
    public void onCreate(Activity activity) {
        Intent intent = activity.getIntent();
        if(intent.getExtras()!=null){
            String key = activity.getResources().getString(R.string.key_intent_menu);
            String str = intent.getExtras().getString(key);
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
