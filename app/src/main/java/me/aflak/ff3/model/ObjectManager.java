package me.aflak.ff3.model;

import android.content.SharedPreferences;
import android.content.res.Resources;

import com.google.gson.Gson;

/**
 * Created by Omar on 07/10/2017.
 */

public class ObjectManager {
    private SharedPreferences sharedPreferences;
    private Resources resources;
    private Gson gson;

    public ObjectManager(SharedPreferences sharedPreferences, Resources resources, Gson gson) {
        this.sharedPreferences = sharedPreferences;
        this.resources = resources;
        this.gson = gson;
    }

    public void put(String key, Object object){
        sharedPreferences.edit()
                .putString(key, gson.toJson(object))
                .apply();
    }

    public void put(int resId, Object object){
        put(resources.getString(resId), object);
    }

    public <T> T get(String key, Class<T> type){
        return gson.fromJson(sharedPreferences.getString(key, null), type);
    }

    public <T> T get(int resId, Class<T> type){
        return get(resources.getString(resId), type);
    }
}
