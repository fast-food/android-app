package me.aflak.ff3.ui.Menu.presenter;

import android.app.Activity;
import android.widget.GridView;

public interface SelectMenuPresenter {
    void onCreate(Activity activity);
    void setGridView(GridView gridView);
    void onDone();
}
