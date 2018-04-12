package me.aflak.ff3.ui.Main.interactor;

import java.util.List;

import me.aflak.ff3.app.NfcRequest;
import me.aflak.ff3.entity.Food;
import me.aflak.ff3.entity.Menu;

/**
 * Created by Omar on 07/10/2017.
 */

public interface MainInteractor {
    List<Menu> parseMenus(String str);
    List<Food> parseFood(String str);
    void saveMenu(List<Menu> menu);
    void saveFood(List<Food> food);
    NfcRequest getNfcRequest();
}
