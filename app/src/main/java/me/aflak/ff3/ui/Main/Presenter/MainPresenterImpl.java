package me.aflak.ff3.ui.Main.Presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;

import java.util.List;

import me.aflak.ff3.app.NfcCardService;
import me.aflak.ff3.app.NfcRequest;
import me.aflak.ff3.entity.Food;
import me.aflak.ff3.entity.Menu;
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
    public void onCreate() {
        mainView.showNfcImage(true);
        NfcRequest nfcRequest = mainInteractor.getNfcRequest();
        nfcRequest.clear();
        nfcRequest.push(NfcCardService.REQUEST_FOOD);
        nfcRequest.push(NfcCardService.REQUEST_MENU);
        nfcRequest.save();
    }

    @Override
    public void onStart(Context context) {
        IntentFilter hceNotificationsFilter = new IntentFilter();
        hceNotificationsFilter.addAction(NfcCardService.ACTION_NOTIFY_MENU);
        hceNotificationsFilter.addAction(NfcCardService.ACTION_NOTIFY_FOOD);
        context.registerReceiver(hceNotificationsReceiver, hceNotificationsFilter);
    }

    @Override
    public void onStop(Context context) {
        context.unregisterReceiver(hceNotificationsReceiver);
    }

    private BroadcastReceiver hceNotificationsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action!=null){
                if(action.equals(NfcCardService.ACTION_NOTIFY_FOOD)){
                    if (intent.getExtras()!=null) {
                        String data = intent.getStringExtra(NfcCardService.hceData);
                        List<Food> food = mainInteractor.parseFood(data);
                        mainInteractor.saveFood(food);
                    }
                }
                else if(action.equals(NfcCardService.ACTION_NOTIFY_MENU)){
                    if(intent.getExtras()!=null) {
                        String data = intent.getStringExtra(NfcCardService.hceData);
                        List<Menu> menu = mainInteractor.parseMenus(data);
                        mainInteractor.saveMenu(menu);
                        mainView.showNfcImage(false);
                        mainView.clearMenu();
                        mainView.showMenu(menu);
                    }
                }
            }
        }
    };
}
