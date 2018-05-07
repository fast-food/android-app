package me.aflak.ff3.ui.Sender.interactor;

import android.content.res.Resources;
import android.os.Bundle;

import com.google.gson.Gson;

import javax.inject.Inject;

import me.aflak.ff3.MyApp;
import me.aflak.ff3.R;
import me.aflak.ff3.entity.Order;
import me.aflak.ff3.service.NfcRequestQueue;

public class SenderInteractorImpl implements SenderInteractor {
    @Inject Resources resources;
    @Inject NfcRequestQueue nfcRequestQueue;

    public SenderInteractorImpl(){
        MyApp.app().appComponent().inject(this);
    }

    @Override
    public String getJsonOrder(Bundle bundle) {
        if(bundle!=null){
            String key = resources.getString(R.string.key_intent_order);
            return bundle.getString(key, null);
        }
        return null;
    }

    @Override
    public NfcRequestQueue getNfcRequestQueue() {
        return nfcRequestQueue;
    }
}
