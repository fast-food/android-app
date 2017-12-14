package me.aflak.ff3.ui.Main.Interactor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import me.aflak.ff3.entity.Menu;

/**
 * Created by Omar on 07/10/2017.
 */

public class MainInteractorImpl implements MainInteractor{
    private Gson gson;

    public MainInteractorImpl(Gson gson) {
        this.gson = gson;
    }

    @Override
    public List<Menu> parseMenus(String json) {
        Type listType = new TypeToken<ArrayList<Menu>>(){}.getType();
        return gson.fromJson(json, listType);
    }
}
