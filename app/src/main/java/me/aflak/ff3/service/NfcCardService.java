package me.aflak.ff3.service;

import android.content.Intent;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import me.aflak.ff3.MyApp;

import static me.aflak.ff3.model.NfcUtils.*;

public class NfcCardService extends HostApduService {
    public static final String ACTION = "me.aflak.ff3.service.NfcCardService.HCE_DATA";
    public static final String DATA = "hceData";
    public static final String CODE = "hceCode";

    private static final String TAG = "NfcCardService";
    private static final String AID = "F222222222";
    private static final String SELECT_APDU_HEADER = "00A40400";
    private static final byte[] SELECT_OK_SW = HexStringToByteArray("9000");
    private static final byte[] UNKNOWN_CMD_SW = HexStringToByteArray("0000");
    private static final byte[] SELECT_APDU = BuildSelectApdu(AID);

    private String longMessage;
    private NfcRequest lastRequest;
    @Inject NfcRequestQueue nfcRequest;

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

        if(StartsWith(cmd.getHeader(), SELECT_APDU)){
            // send next request if any
            nfcRequest.load();
            lastRequest = nfcRequest.element();
            if(lastRequest!=null){
                return ConcatArrays(lastRequest.getString().getBytes(), SELECT_OK_SW);
            }

            return SELECT_OK_SW;
        }

        if(cmd.getParams()[0] == (byte)0x00){
            // broadcast result
            nfcRequest.pop();
            nfcRequest.save();
            new BroadcastInBackground(cmd.getStringData(), lastRequest.getCode()).start();

            // send next request if any
            nfcRequest.load();
            lastRequest = nfcRequest.element();
            if(lastRequest!=null){
                return ConcatArrays(lastRequest.getString().getBytes(), SELECT_OK_SW);
            }
        }
        else if(cmd.getParams()[0] == (byte)0x01){
            longMessage += cmd.getStringData();
            return SELECT_OK_SW;
        }
        else if(cmd.getParams()[0] == (byte)0x02){
            longMessage += cmd.getStringData();

            // broadcast result
            nfcRequest.pop();
            nfcRequest.save();
            new BroadcastInBackground(longMessage, lastRequest.getCode()).start();
            longMessage = "";

            // send next request if any
            nfcRequest.load();
            lastRequest = nfcRequest.element();
            if(lastRequest!=null){
                return ConcatArrays(lastRequest.getString().getBytes(), SELECT_OK_SW);
            }

            return SELECT_OK_SW;
        }

        return UNKNOWN_CMD_SW;
    }

    public static byte[] BuildSelectApdu(String aid) {
        return HexStringToByteArray(SELECT_APDU_HEADER + String.format("%02X", aid.length() / 2) + aid);
    }

    private static boolean StartsWith(byte[] a, byte[] b){
        int length = a.length>b.length?b.length:a.length;
        for(int i=0 ; i<length ; i++){
            if(a[i]!=b[i]){
                return false;
            }
        }
        return true;
    }

    private class BroadcastInBackground extends Thread implements Runnable{
        private String data;
        private int requestCode;

        BroadcastInBackground(String data, int requestCode){
            this.data = data;
            this.requestCode = requestCode;
        }

        @Override
        public void run() {
            super.run();
            Intent intent = new Intent(ACTION);
            intent.putExtra(DATA, data);
            intent.putExtra(CODE, requestCode);
            sendBroadcast(intent);
        }
    }
}
