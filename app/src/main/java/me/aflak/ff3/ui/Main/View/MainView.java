package me.aflak.ff3.ui.Main.View;

import java.util.List;

import me.aflak.ff3.entity.Menu;

/**
 * Created by Omar on 07/10/2017.
 */

public interface MainView {
    void showMenu(List<Menu> menuList);
    void showNfcImage(boolean state);
    void showToast(String message);
    void clearMenu();
}
