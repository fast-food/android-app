package me.aflak.ff3.ui.Main.Presenter;

import android.content.Context;

/**
 * Created by Omar on 07/10/2017.
 */

public interface MainPresenter {
    void checkForNfc(Context context);
    void onStart(Context context);
    void onStop(Context context);
}
