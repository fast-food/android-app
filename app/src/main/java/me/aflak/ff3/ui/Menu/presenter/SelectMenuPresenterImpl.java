package me.aflak.ff3.ui.Menu.presenter;

import android.app.Activity;
import android.content.Intent;
import android.util.SparseArray;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

import me.aflak.ff3.R;
import me.aflak.ff3.entity.Food;
import me.aflak.ff3.entity.FoodType;
import me.aflak.ff3.entity.Menu;
import me.aflak.ff3.entity.Order;
import me.aflak.ff3.ui.Menu.interactor.SelectMenuInteractor;
import me.aflak.ff3.ui.Menu.view.SelectMenuView;

public class SelectMenuPresenterImpl implements SelectMenuPresenter {
    private SelectMenuView selectMenuView;
    private SelectMenuInteractor selectMenuInteractor;
    private Menu menu;

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
            menu = selectMenuInteractor.parseMenu(str);
            selectMenuView.showMenu(menu);
        }
    }

    @Override
    public void setGridView(GridView gridView) {
        gridView.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public void onDone() {
        SparseArray<Food> foods = selectMenuInteractor.getChosenFood();
        if(foods.size()!=menu.getTypes().size()){
            selectMenuView.showSelectErrorPopup();
            return;
        }

        Order order = new Order();
        order.setMenuId(menu.getId());
        for(int i=0 ; i<foods.size() ; i++){
            order.addFoodId(foods.valueAt(i).getId());
        }

        selectMenuView.navigateToSender(order);
    }

    private AdapterView.OnItemClickListener onItemClickListener = (parent, view, position, id) -> {
        final FoodType foodType = selectMenuView.getItem(position);
        final List<Food> foodList = selectMenuInteractor.getFoodList(foodType);
        final int checkItem = selectMenuInteractor.getSelectedFoodIndex(foodList, position);

        selectMenuView.showFoodList(foodList, checkItem, position1 -> {
            Food food = foodList.get(position1);
            selectMenuInteractor.setFood(position, food);
            selectMenuView.checkItem(position, food);
        });
    };

    public interface OnFoodSelectedListener{
        void onFoodSelected(int position);
    }
}
