package me.aflak.ff3.ui.Menu.interactor;

import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.aflak.ff3.MyApp;
import me.aflak.ff3.R;
import me.aflak.ff3.entity.Food;
import me.aflak.ff3.entity.FoodType;
import me.aflak.ff3.entity.Menu;
import me.aflak.ff3.entity.Order;
import me.aflak.ff3.model.ObjectManager;

public class SelectMenuInteractorImpl implements SelectMenuInteractor {
    private SparseArray<Food> foodList;

    @Inject ObjectManager objectManager;
    @Inject Gson gson;

    public SelectMenuInteractorImpl() {
        MyApp.app().appComponent().inject(this);
        foodList = new SparseArray<>();
    }

    @Override
    public Menu parseMenu(String str) {
        return gson.fromJson(str, Menu.class);
    }

    @Override
    public void setFood(int index, Food food) {
        if(foodList.get(index, null)==null) {
            foodList.put(index, food);
        }
        else{
            foodList.setValueAt(index, food);
        }
    }

    @Override
    public SparseArray<Food> getChosenFood() {
        return foodList;
    }

    @Override
    public List<Food> getFoodList(FoodType foodType) {
        Type listType = new TypeToken<List<Food>>(){}.getType();
        List<Food> foodList = objectManager.get(R.string.key_pref_food, listType);

        if(foodList!=null){
            List<Food> filtered = new ArrayList<>();
            for(Food f : foodList){
                if(f.getType()==foodType){
                    filtered.add(f);
                }
            }
            return filtered;
        }
        return null;
    }

    @Override
    public int getSelectedFoodIndex(List<Food> food, int index) {
        Food tmp = this.foodList.get(index, null);
        if(tmp!=null){
            for(int i=0 ; i<food.size() ; i++){
                if(food.get(i).getId()==tmp.getId()){
                    return i;
                }
            }
        }
        return 0;
    }
}
