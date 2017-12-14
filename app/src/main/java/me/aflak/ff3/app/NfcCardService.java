package me.aflak.ff3.app;

import android.content.Intent;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;

import javax.inject.Inject;

import me.aflak.ff3.MyApp;
import me.aflak.ff3.model.ObjectManager;

import static me.aflak.ff3.model.NfcUtils.*;

public class NfcCardService extends HostApduService {
    public static final String ACTION_NOTIFY_DATA = "NOTIFY_HCE_DATA";
    public static final String hceData = "hcdData";

    private static final String TAG = "NfcCardService";
    private static final String AID = "F222222222";
    private static final String SELECT_APDU_HEADER = "00A40400";
    private static final byte[] SELECT_OK_SW = HexStringToByteArray("9000");
    private static final byte[] UNKNOWN_CMD_SW = HexStringToByteArray("0000");
    private static final byte[] SELECT_APDU = BuildSelectApdu(AID);

    private static String REQUEST_MENU = "1";

    @Inject ObjectManager objectManager;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApp.app().appComponent().inject(this);
    }

    @Override
    public void onDeactivated(int reason) {}

    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        Log.d(TAG, "Received APDU: " + ByteArrayToHexString(commandApdu));
        String stringApdu = new String(commandApdu);
        if (Arrays.equals(SELECT_APDU, commandApdu)) {
            return ConcatArrays(REQUEST_MENU.getBytes(), SELECT_OK_SW);
        }
        else if(stringApdu.startsWith(REQUEST_MENU)){
            Log.d(TAG, stringApdu.substring(1));
            Intent intent = new Intent(ACTION_NOTIFY_DATA);
            intent.putExtra(hceData, stringApdu.substring(1));
            this.sendBroadcast(intent);
            return SELECT_OK_SW;
        }
        else{
            return UNKNOWN_CMD_SW;
        }
    }

    public static byte[] BuildSelectApdu(String aid) {
        return HexStringToByteArray(SELECT_APDU_HEADER + String.format("%02X", aid.length() / 2) + aid);
    }
}
