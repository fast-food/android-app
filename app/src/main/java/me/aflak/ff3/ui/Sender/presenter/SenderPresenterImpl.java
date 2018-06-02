package me.aflak.ff3.ui.Sender.presenter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import me.aflak.ff3.R;
import me.aflak.ff3.service.NfcCardService;
import me.aflak.ff3.service.NfcRequest;
import me.aflak.ff3.service.NfcRequestQueue;
import me.aflak.ff3.service.NfcRequestType;
import me.aflak.ff3.ui.Sender.interactor.SenderInteractor;
import me.aflak.ff3.ui.Sender.view.SenderView;

public class SenderPresenterImpl implements SenderPresenter {
    private SenderView view;
    private SenderInteractor interactor;
    private boolean hasAllData;

    public SenderPresenterImpl(SenderView view, SenderInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
        this.hasAllData = false;
    }

    @Override
    public void onCreate(Activity activity) {
        Intent intent = activity.getIntent();
        String orderJson = interactor.getOrderJson(intent.getExtras());

        String baseUrl = activity.getResources().getString(R.string.server_base_url);
        String orderUri = activity.getResources().getString(R.string.server_uri_order);
        String orderKey = activity.getResources().getString(R.string.server_order_key);

        NfcRequestQueue queue = interactor.getNfcRequestQueue();
        queue.clear();
        NfcRequest nfcRequest = new NfcRequest(NfcRequestType.POST, baseUrl+orderUri);
        nfcRequest.addData(orderKey, orderJson);
        queue.push(nfcRequest, 1);
        queue.save();
    }

    @Override
    public void onStart(Context context) {
        IntentFilter hceNotificationsFilter = new IntentFilter();
        hceNotificationsFilter.addAction(NfcCardService.ACTION_DATA);
        hceNotificationsFilter.addAction(NfcCardService.ACTION_START);
        hceNotificationsFilter.addAction(NfcCardService.ACTION_STOP);
        hceNotificationsFilter.addAction(NfcCardService.ACTION_LINK_LOSS);
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
                switch (action) {
                    case NfcCardService.ACTION_DATA:
                        if (intent.getExtras() != null) {
                            String json = intent.getStringExtra(NfcCardService.DATA);
                            int requestCode = intent.getIntExtra(NfcCardService.CODE, -1);

                            switch (requestCode) {
                                case 1:
                                    view.displayWaitingMessage();
                                    break;
                            }
                        }
                        break;
                    case NfcCardService.ACTION_START:
                        view.startAnimation();
                        break;
                    case NfcCardService.ACTION_STOP:
                        view.stopAnimation();
                        hasAllData = true;
                        break;
                    case NfcCardService.ACTION_LINK_LOSS:
                        if (!hasAllData) {
                            view.stopAnimation();
                            view.showToast(R.string.activity_sender_connection_lost);
                        }
                        break;
                }
            }
        }
    };

}
