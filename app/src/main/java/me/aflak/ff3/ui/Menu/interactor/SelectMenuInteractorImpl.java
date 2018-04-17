package me.aflak.ff3.ui.Menu.interactor;

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
import me.aflak.ff3.model.ObjectManager;

public class SelectMenuInteractorImpl implements SelectMenuInteractor {
    @Inject ObjectManager objectManager;
    @Inject Gson gson;

    public SelectMenuInteractorImpl() {
        MyApp.app().appComponent().inject(this);
    }

    @Override
    public Menu parseMenu(String str) {
        return gson.fromJson(str, Menu.class);
    }

    @Override
    public List<Food> getFoodList(FoodType foodType) {
        Type listType = new TypeToken<List<Food>>(){}.getType();
        List<Food> food = objectManager.get(R.string.key_pref_food, listType);
        if(food!=null){
            List<Food> filtered = new ArrayList<>();
            for(Food f : food){
                if(f.getType()==foodType){
                    filtered.add(f);
                }
            }
            return filtered;
        }
        return null;
    }
}
