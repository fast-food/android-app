package me.aflak.ff3.ui.Sender.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import me.aflak.ff3.R;
import me.aflak.ff3.ui.Sender.interactor.SenderInteractor;
import me.aflak.ff3.ui.Sender.view.SenderView;

public class SenderPresenterImpl implements SenderPresenter {
    private SenderView view;
    private SenderInteractor interactor;

    public SenderPresenterImpl(SenderView view, SenderInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onCreate(Activity activity) {
        Intent intent = activity.getIntent();
        String key = activity.getResources().getString(R.string.key_intent_order);
        String gson = intent.getExtras().getString(key, null);

    }
}
