package me.aflak.ff3.ui.Main.Interactor;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.aflak.ff3.entity.Food;
import me.aflak.ff3.entity.Menu;
import me.aflak.ff3.entity.MenuSize;

/**
 * Created by Omar on 07/10/2017.
 */

public class MainInteractorImpl implements MainInteractor{
    public MainInteractorImpl() {
    }

    private Food getFood(String str){
        String lines[] = str.split(" ");
        if(lines.length==2) {
            return new Food(Integer.valueOf(lines[0]), lines[1]);
        }
        return null;
    }

    @Override
    public List<Menu> parseMenus(String str) {
        List<Menu> menus = new ArrayList<>();
        String[] lines = str.split("\n");
        int p = 0;

        int length = Integer.valueOf(lines[p++]);
        for(int i=0 ; i<length ; i++){
            float price = Float.valueOf(lines[p++]);
            MenuSize size = MenuSize.values()[Integer.valueOf(lines[p++])];
            Food sandwich = getFood(lines[p++]);
            Food extra = getFood(lines[p++]);
            Food drink = getFood(lines[p++]);
            menus.add(new Menu(size, sandwich, extra, drink, price));
        }
        return menus;
    }
}
