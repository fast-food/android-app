package me.aflak.ff3.ui.Main.interactor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import me.aflak.ff3.MyApp;
import me.aflak.ff3.R;
import me.aflak.ff3.entity.Food;
import me.aflak.ff3.entity.Menu;
import me.aflak.ff3.model.ObjectManager;
import me.aflak.ff3.service.NfcRequestQueue;

/**
 * Created by Omar on 07/10/2017.
 */

public class MainInteractorImpl implements MainInteractor{
    @Inject ObjectManager objectManager;
    @Inject NfcRequestQueue nfcRequestQueue;
    @Inject Gson gson;

    public MainInteractorImpl() {
        MyApp.app().appComponent().inject(this);
    }

    @Override
    public List<Food> parseFood(String str) {
        Type listType = new TypeToken<List<Food>>(){}.getType();
        return gson.fromJson(str, listType);
    }

    @Override
    public List<Menu> parseMenus(String str) {
        Type listType = new TypeToken<List<Menu>>(){}.getType();
        return gson.fromJson(str, listType);
    }

    @Override
    public void saveMenu(List<Menu> menu) {
        objectManager.put(R.string.key_pref_menus, menu);
    }

    @Override
    public void saveFood(List<Food> food) {
        objectManager.put(R.string.key_pref_food, food);
    }

    @Override
    public NfcRequestQueue getNfcRequestQueue() {
        return nfcRequestQueue;
    }
}
