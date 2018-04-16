package me.aflak.ff3.service;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import me.aflak.ff3.model.ObjectManager;

public class NfcRequestQueue {
    private ObjectManager objectManager;
    private List<NfcRequest> requests;

    public NfcRequestQueue(ObjectManager objectManager){
        this.objectManager = objectManager;
        this.requests = new ArrayList<>();
    }

    public void push(NfcRequest request) {
        requests.add(request);
    }

    public void pop() {
        if(!requests.isEmpty()){
            requests.remove(0);
        }
    }

    public NfcRequest element() {
        if(!requests.isEmpty()){
            return requests.get(0);
        }
        return null;
    }

    public boolean isEmpty(){
        return requests.isEmpty();
    }

    public void clear(){
        requests.clear();
    }

    public void save(){
        objectManager.put("nfc_requests", requests);
    }

    public void load(){
        Type listType = new TypeToken<List<NfcRequest>>(){}.getType();
        requests = objectManager.get("nfc_requests", listType);
        if(requests==null){
            requests = new ArrayList<>();
        }
    }
}
