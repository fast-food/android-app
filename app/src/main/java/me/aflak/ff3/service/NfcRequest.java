package me.aflak.ff3.service;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import me.aflak.ff3.model.ObjectManager;

public class NfcRequest {
    private ObjectManager objectManager;
    private List<Byte> requests;

    public NfcRequest(ObjectManager objectManager){
        this.objectManager = objectManager;
        this.requests = new ArrayList<>();
    }

    public void push(Byte character) {
        requests.add(character);
    }

    public void pop() {
        if(!requests.isEmpty()){
            requests.remove(0);
        }
    }

    public Byte element() {
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
        Type listType = new TypeToken<List<Byte>>(){}.getType();
        requests = objectManager.get("nfc_requests", listType);
        if(requests==null){
            requests = new ArrayList<>();
        }
    }
}
