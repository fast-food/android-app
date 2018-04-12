package me.aflak.ff3.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandHelper {
    private List<Integer> list;
    private int index;

    public RandHelper() {
        this.list = new ArrayList<>();
        this.index = 0;
    }

    public void setMax(int max) {
        this.index = 0;

        list.clear();
        for (int i=0; i<max; i++) {
            list.add(i);
        }

        shuffle();
    }

    private void shuffle(){
        Collections.shuffle(list);
    }

    public int nextInt(){
        if(index==list.size()){
            shuffle();
            index = 0;
        }
        return list.get(index++);
    }

}
