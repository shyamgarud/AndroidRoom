package com.roomexample.roomexample.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;

/**
 * Created by Shyam on 9/9/2017.
 */
@Database(entities = {Product.class}, version = 1)
public abstract class ProductDatabase extends RoomDatabase{
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };
    public abstract ProductDao getProductDao();

}
