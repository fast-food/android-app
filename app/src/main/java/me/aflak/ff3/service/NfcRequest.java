package me.aflak.ff3.service;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NfcRequest {
    @SerializedName("type") private int type;
    @SerializedName("url") private String url;
    @SerializedName("data") private List<DataPair> data;

    public NfcRequest(int type, String url) {
        this.type = type;
        this.url = url;
        this.data = new ArrayList<>();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<DataPair> getData() {
        return data;
    }

    public void addData(String key, String value) {
        this.data.add(new DataPair(key, value));
    }

    public static class DataPair{
        String key;
        String value;

        DataPair(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
