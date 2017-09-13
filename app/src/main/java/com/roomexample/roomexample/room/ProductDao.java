package com.roomexample.roomexample.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Shyam on 9/9/2017.
 */

@Dao
public interface ProductDao {
    @Query("SELECT * FROM product")
    List<Product> getAll();

    @Query("SELECT * FROM product WHERE name LIKE :name LIMIT 1")
    Product findByName(String name);

    @Query("SELECT * FROM product WHERE uid LIKE :uid LIMIT 1")
    Product findByid(int uid);

    @Insert
    void insertAll(List<Product> products);

    @Update
    void update (Product product);

    @Delete
    void delete(Product product);

    @Delete
    public void deleteProducts(List<Product> products);
}
