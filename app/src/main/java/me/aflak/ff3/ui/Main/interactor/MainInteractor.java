package me.aflak.ff3.ui.Main.interactor;

import java.util.List;

import me.aflak.ff3.entity.Food;
import me.aflak.ff3.entity.Menu;
import me.aflak.ff3.service.NfcRequestQueue;

/**
 * Created by Omar on 07/10/2017.
 */

public interface MainInteractor {
    List<Menu> parseMenus(String str);
    List<Food> parseFood(String str);
    void saveMenu(List<Menu> menu);
    void saveFood(List<Food> food);
    NfcRequestQueue getNfcRequestQueue();
}
