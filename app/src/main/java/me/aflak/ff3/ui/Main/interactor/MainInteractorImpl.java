package me.aflak.ff3.ui.Main.interactor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import me.aflak.ff3.service.NfcRequest;
import me.aflak.ff3.entity.Food;
import me.aflak.ff3.entity.FoodType;
import me.aflak.ff3.entity.Menu;
import me.aflak.ff3.model.ObjectManager;

/**
 * Created by Omar on 07/10/2017.
 */

public class MainInteractorImpl implements MainInteractor{
    private ObjectManager objectManager;
    private NfcRequest nfcRequest;
    private Gson gson;

    public MainInteractorImpl(ObjectManager objectManager, NfcRequest nfcRequest, Gson gson) {
        this.objectManager = objectManager;
        this.nfcRequest = nfcRequest;
        this.gson = gson;
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
        objectManager.put("menus", menu);
    }

    @Override
    public void saveFood(List<Food> food) {
        objectManager.put("food", food);
    }

    @Override
    public NfcRequest getNfcRequest() {
        return nfcRequest;
    }
}
