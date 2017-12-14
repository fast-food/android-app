package me.aflak.ff3.ui.Main.Presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;

import me.aflak.ff3.app.NfcCardService;
import me.aflak.ff3.ui.Main.Interactor.MainInteractor;
import me.aflak.ff3.ui.Main.View.MainView;

/**
 * Created by Omar on 07/10/2017.
 */

public class MainPresenterImpl implements MainPresenter {
    private MainView mainView;
    private MainInteractor mainInteractor;

    public MainPresenterImpl(MainView mainView, MainInteractor mainInteractor) {
        this.mainView = mainView;
        this.mainInteractor = mainInteractor;
    }

    @Override
    public void checkForNfc(Context context) {
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(context);
        if (!nfc.isEnabled()) {
            mainView.showToast("Please activate NFC");
            context.startActivity(new Intent(android.provider.Settings.ACTION_NFC_SETTINGS));
        }
    }

    @Override
    public void onStart(Context context) {
        mainView.showNfcImage(true);
        mainView.showText("");
        IntentFilter hceNotificationsFilter = new IntentFilter();
        hceNotificationsFilter.addAction(NfcCardService.ACTION_NOTIFY_DATA);
        context.registerReceiver(hceNotificationsReceiver, hceNotificationsFilter);
    }

    @Override
    public void onStop(Context context) {
        context.unregisterReceiver(hceNotificationsReceiver);
    }

    private BroadcastReceiver hceNotificationsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras()!=null) {
                String data = intent.getStringExtra(NfcCardService.hceData);
                mainView.showNfcImage(false);
                mainView.showMenu(mainInteractor.parseMenus(data));
            }
        }
    };
}
