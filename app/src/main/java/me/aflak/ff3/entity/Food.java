package me.aflak.ff3.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Omar on 14/12/2017.
 */

public class Food {
    @SerializedName("name") private String name;
    @SerializedName("id") private int id;

    public Food(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
