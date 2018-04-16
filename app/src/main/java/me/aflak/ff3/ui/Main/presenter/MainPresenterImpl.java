package me.aflak.ff3.ui.Main.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;

import java.util.List;

import me.aflak.ff3.R;
import me.aflak.ff3.service.NfcCardService;
import me.aflak.ff3.entity.Food;
import me.aflak.ff3.entity.Menu;
import me.aflak.ff3.service.NfcRequest;
import me.aflak.ff3.service.NfcRequestQueue;
import me.aflak.ff3.ui.Main.interactor.MainInteractor;
import me.aflak.ff3.ui.Main.view.MainView;

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
    public void onCreate(Context context) {
        mainView.showNfcImage(true);
        String baseUrl = context.getResources().getString(R.string.server_base_url);
        String foodUri = context.getResources().getString(R.string.server_uri_food);
        String menuUri = context.getResources().getString(R.string.server_uri_menu);

        NfcRequestQueue queue = mainInteractor.getNfcRequestQueue();
        queue.clear();
        queue.push(new NfcRequest(baseUrl+foodUri, 1));
        queue.push(new NfcRequest(baseUrl+menuUri, 2));
        queue.save();
    }

    @Override
    public void onStart(Context context) {
        IntentFilter hceNotificationsFilter = new IntentFilter();
        hceNotificationsFilter.addAction(NfcCardService.ACTION_DATA);
        hceNotificationsFilter.addAction(NfcCardService.ACTION_START);
        hceNotificationsFilter.addAction(NfcCardService.ACTION_STOP);
        context.registerReceiver(hceNotificationsReceiver, hceNotificationsFilter);
    }

    @Override
    public void onStop(Context context) {
        context.unregisterReceiver(hceNotificationsReceiver);
    }

    @Override
    public void onMenuClick(Menu menu) {
        mainView.navigateToMenu(menu);
    }

    private BroadcastReceiver hceNotificationsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action!=null){
                if(action.equals(NfcCardService.ACTION_DATA)){
                    if(intent.getExtras()!=null) {
                        String json = intent.getStringExtra(NfcCardService.DATA);
                        int requestCode = intent.getIntExtra(NfcCardService.CODE, -1);

                        switch (requestCode){
                            case 0:
                                break;
                            case 1:
                                List<Food> food = mainInteractor.parseFood(json);
                                mainInteractor.saveFood(food);
                                break;
                            case 2:
                                List<Menu> menu = mainInteractor.parseMenus(json);
                                mainInteractor.saveMenu(menu);
                                mainView.showNfcImage(false);
                                mainView.clearMenu();
                                mainView.showMenu(menu);
                                break;
                        }
                    }
                }
                else if(action.equals(NfcCardService.ACTION_START)){
                    mainView.startAnimation();
                }
                else if(action.equals(NfcCardService.ACTION_STOP)){
                    mainView.stopAnimation();
                }
            }
        }
    };
}
