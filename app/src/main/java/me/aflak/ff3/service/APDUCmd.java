package me.aflak.ff3.service;

import java.util.ArrayList;
import java.util.List;

import static me.aflak.ff3.model.NfcUtils.ByteArrayToHexString;

public class APDUCmd {
    private byte cla;
    private byte ins;
    private byte[] params;
    private byte lc;
    private List<Byte> data;
    private byte le;

    public APDUCmd(){
        cla = 0x00;
        ins = 0x00;
        params = new byte[2];
        lc = 0x00;
        data = new ArrayList<>();
        le = 0x00;
    }

    static APDUCmd parse(byte[] bytes){
        APDUCmd cmd = new APDUCmd();

        cmd.cla = bytes[0];
        cmd.ins = bytes[1];
        cmd.params[0] = bytes[2];
        cmd.params[1] = bytes[3];
        cmd.lc = bytes[4];

        int length = Integer.parseInt(ByteArrayToHexString(new byte[]{cmd.lc}), 16);

        for(int i=0 ; i<length ; i++){
            cmd.data.add(bytes[5+i]);
        }
        if(5+cmd.lc<bytes.length){
            cmd.le = bytes[5+length];
        }

        return cmd;
    }

    public byte getCla() {
        return cla;
    }

    public byte getIns() {
        return ins;
    }

    public byte[] getParams() {
        return params;
    }

    public byte getLc() {
        return lc;
    }

    public List<Byte> getBytesData() {
        return data;
    }

    public String getStringData(){
        Byte[] b = data.toArray(new Byte[data.size()]);
        byte[] b2 = new byte[b.length];
        for (int i = 0; i < b.length; i++) {
            b2[i] = b[i];
        }
        return new String(b2);
    }

    public byte getLe() {
        return le;
    }
}
