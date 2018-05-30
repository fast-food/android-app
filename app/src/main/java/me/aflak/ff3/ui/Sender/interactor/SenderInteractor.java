package me.aflak.ff3.ui.Sender.interactor;

import android.os.Bundle;

import me.aflak.ff3.entity.Order;
import me.aflak.ff3.service.NfcRequestQueue;

public interface SenderInteractor {
    String getOrderJson(Bundle bundle);
    NfcRequestQueue getNfcRequestQueue();
}
