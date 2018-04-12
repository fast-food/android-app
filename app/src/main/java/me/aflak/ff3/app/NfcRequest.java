package me.aflak.ff3.app;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import me.aflak.ff3.model.ObjectManager;

public class NfcRequest {
    private ObjectManager objectManager;
    private List<Character> requests;

    public NfcRequest(ObjectManager objectManager){
        this.objectManager = objectManager;
        this.requests = new ArrayList<>();
    }

    public void push(Character character) {
        requests.add(character);
    }

    public void pop() {
        if(!requests.isEmpty()){
            requests.remove(0);
        }
    }

    public Character element() {
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
        Type listType = new TypeToken<List<Character>>(){}.getType();
        requests = objectManager.get("nfc_requests", listType);
        if(requests==null){
            requests = new ArrayList<>();
        }
    }
}
