package me.aflak.ff3.service;

import android.content.Intent;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import me.aflak.ff3.MyApp;

import static me.aflak.ff3.model.NfcUtils.*;

public class NfcCardService extends HostApduService {
    public static final String ACTION_NOTIFY_MENU = "NOTIFY_HCE_MENU";
    public static final String ACTION_NOTIFY_FOOD = "NOTIFY_HCE_FOOD";
    public static final String hceData = "hcdData";

    public static Byte REQUEST_MENU = 0x01;
    public static Byte REQUEST_FOOD = 0x02;

    public static Map<Byte, String> REQUEST_ACTION_MAP = new HashMap<Byte, String>()
    {{
        put(REQUEST_MENU, ACTION_NOTIFY_MENU);
        put(REQUEST_FOOD, ACTION_NOTIFY_FOOD);
    }};

    private static final String TAG = "NfcCardService";
    private static final String AID = "F222222222";
    private static final String SELECT_APDU_HEADER = "00A40400";
    private static final byte[] SELECT_OK_SW = HexStringToByteArray("9000");
    private static final byte[] UNKNOWN_CMD_SW = HexStringToByteArray("0000");
    private static final byte[] SELECT_APDU = BuildSelectApdu(AID);

    private String longMessage;

    @Inject NfcRequest nfcRequest;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApp.app().appComponent().inject(this);
        longMessage = "";
    }

    @Override
    public void onDeactivated(int reason) {}

    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        Log.d(TAG, "Received APDU: " + ByteArrayToHexString(commandApdu));
        APDUCmd cmd = APDUCmd.parse(commandApdu);

        if(cmd.getIns()==0x01){
            longMessage += cmd.getStringData();
        }
        else if(cmd.getIns()==0x02){
            longMessage += cmd.getStringData();

            // broadcast result
            String action = REQUEST_ACTION_MAP.get(cmd.getCla());
            if(action!=null){
                nfcRequest.pop();
                nfcRequest.save();
                new BroadcastInBackground(longMessage, action).start();
            }

            // send next request if any
            nfcRequest.load();
            Byte nextRequest = nfcRequest.element();
            if(nextRequest!=null){
                return ConcatArrays(new byte[]{nextRequest}, SELECT_OK_SW);
            }

            longMessage = "";
        }
        else{
            // broadcast result
            String action = REQUEST_ACTION_MAP.get(cmd.getCla());
            if(action!=null){
                nfcRequest.pop();
                nfcRequest.save();
                String data = cmd.getStringData();
                new BroadcastInBackground(data, action).start();
            }

            // send next request if any
            nfcRequest.load();
            Byte nextRequest = nfcRequest.element();
            if(nextRequest!=null){
                return ConcatArrays(new byte[]{nextRequest}, SELECT_OK_SW);
            }
        }

        return SELECT_OK_SW;
    }

    public static byte[] BuildSelectApdu(String aid) {
        return HexStringToByteArray(SELECT_APDU_HEADER + String.format("%02X", aid.length() / 2) + aid);
    }

    private class BroadcastInBackground extends Thread implements Runnable{
        private String data;
        private String action;

        BroadcastInBackground(String data, String action){
            this.data = data;
            this.action = action;
        }

        @Override
        public void run() {
            super.run();
            Intent intent = new Intent(action);
            intent.putExtra(hceData, data);
            sendBroadcast(intent);
        }
    }
}
