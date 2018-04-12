package me.aflak.ff3.ui.Main.interactor;

import java.util.ArrayList;
import java.util.List;

import me.aflak.ff3.app.NfcRequest;
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

    public MainInteractorImpl(ObjectManager objectManager, NfcRequest nfcRequest) {
        this.objectManager = objectManager;
        this.nfcRequest = nfcRequest;
    }

    @Override
    public List<Food> parseFood(String str) {
        List<Food> food = new ArrayList<>();
        String[] lines = str.split("\n");
        int length = Integer.valueOf(lines[0]);
        lines = lines[1].split(" ");

        int p=0;
        for(int i=0 ; i<length ; i++){
            int id = Integer.valueOf(lines[p++]);
            FoodType type = FoodType.values()[Integer.valueOf(lines[p++])];
            String name = lines[p++];
            food.add(new Food(id, type, name));
        }

        return food;
    }

    @Override
    public List<Menu> parseMenus(String str) {
        List<Menu> menus = new ArrayList<>();
        String[] lines = str.split("\n");
        int length = Integer.valueOf(lines[0]);
        lines = lines[1].split(" ");

        int p=0;
        for(int i=0 ; i<length ; i++){
            float price = Float.valueOf(lines[p++]);
            int count = Integer.valueOf(lines[p++]);
            Menu m = new Menu(price);
            for(int j=0 ; j<count ; j++){
                m.addType(FoodType.values()[Integer.valueOf(lines[p++])]);
            }
            menus.add(m);
        }
        return menus;
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
