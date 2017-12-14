package me.aflak.ff3.ui.Main.Interactor;

import java.util.List;

import me.aflak.ff3.entity.Menu;

/**
 * Created by Omar on 07/10/2017.
 */

public interface MainInteractor {
    List<Menu> parseMenus(String json);
}
