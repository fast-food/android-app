package me.aflak.ff3.service;

public class NfcRequest {
    private String string;
    private int code;

    public NfcRequest(String string, int code) {
        this.string = string;
        this.code = code;
    }

    public String getString() {
        return string;
    }

    public int getCode() {
        return code;
    }
}
