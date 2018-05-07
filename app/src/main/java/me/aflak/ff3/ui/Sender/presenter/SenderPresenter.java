package me.aflak.ff3.ui.Sender.presenter;

import android.app.Activity;
import android.content.Context;

public interface SenderPresenter {
    void onCreate(Activity activity);
    void onStart(Context context);
    void onStop(Context context);
}
