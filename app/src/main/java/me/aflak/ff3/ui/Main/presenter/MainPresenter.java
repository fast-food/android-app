package me.aflak.ff3.ui.Main.presenter;

import android.content.Context;

import me.aflak.ff3.entity.Menu;

/**
 * Created by Omar on 07/10/2017.
 */

public interface MainPresenter {
    void checkForNfc(Context context);
    void onCreate();
    void onStart(Context context);
    void onStop(Context context);
    void onMenuClick(Menu menu);
}
