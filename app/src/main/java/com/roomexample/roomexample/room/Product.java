package com.roomexample.roomexample.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Shyam on 9/9/2017.
 */

@Entity
public class Product implements Serializable {
    @PrimaryKey (autoGenerate = true)
    private int uid;

    @ColumnInfo (name = "name")
    private String name;

    @ColumnInfo (name = "image_url")
    private String price;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
