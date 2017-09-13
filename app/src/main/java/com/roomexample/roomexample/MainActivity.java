package com.roomexample.roomexample;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.roomexample.roomexample.dummy.DummyContent;
import com.roomexample.roomexample.room.Product;
import com.roomexample.roomexample.room.ProductDatabase;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectFragment.OnListFragmentInteractionListener ,InsertFragment.OnFragmentInteractionListener,UpadateFragment.OnListFragmentInteractionListener,DeleteFragment.OnListFragmentInteractionListener {

    public BottomNavigationView navigation;
    public ProductDatabase database;

    public  ProductDatabase getDatabase() {
        return database;
    }

    public void setDatabase(ProductDatabase database) {
        this.database = database;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_select:
                    switchToFragmentSelectFragment();
                    return true;
                case R.id.navigation_insert:
                    switchToFragmentInsertFragment(0);
                    return true;
                case R.id.navigation_update:
                    switchToFragmentUpdateFragment();
                    return true;
                case R.id.navigation_delete:
                    switchToFragmentDeleteFragment();
                    return true;
            }
            return false;
        }

    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        database= Room.databaseBuilder(getApplicationContext(),ProductDatabase.class,"room_product")
                .addMigrations(new Migration(1, 2) {
                    @Override
                    public void migrate(SupportSQLiteDatabase database) {
                        // Since we didn't alter the table, there's nothing else to do here.
                        database.execSQL("ALTER TABLE Product "
                                + " ADD COLUMN price TEXT");
                    }
                })
                .build();
        navigation.setSelectedItemId(R.id.navigation_select);
        //switchToFragmentSelectFragment();
    }
    public void switchToFragmentSelectFragment() {
        new AsyncTask(){

            @Override
            protected Object doInBackground(Object[] objects) {
                DummyContent.ITEMS.clear();
                DummyContent.ITEM_MAP.clear();
                List<Product> products1=database.getProductDao().getAll();
                int i=0;
                for (Product product1:products1
                        ) {
                    i++;
                    Log.e("product1",product1.toString());
                    DummyContent.DummyItem item=new DummyContent.DummyItem(product1.getUid()+"",product1.getName(),product1.getPrice());
                    DummyContent.ITEMS.add(item);
                    DummyContent.ITEM_MAP.put(item.id, item);

                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {

                super.onPostExecute(o);
            }
        }.execute();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content, new SelectFragment()).commit();
    }
    public void switchToFragmentInsertFragment(int id) {
        FragmentManager manager = getSupportFragmentManager();
        InsertFragment insertFragment=new InsertFragment();
        if(id==0) {
            manager.beginTransaction().replace(R.id.content,insertFragment).commit();

        }else{
            new AsyncTask<Object,Product,Product>(){

                @Override
                protected Product doInBackground(Object[] objects) {
                    DummyContent.ITEMS.clear();
                    DummyContent.ITEM_MAP.clear();
                    Log.e(getClass().getName()+" product",id+"");
                    Product product=  database.getProductDao().findByid(id);

                    return product;
                }

                @Override
                protected void onPostExecute(Product product) {
                    if(product!=null) {
                        Bundle bundle = new Bundle();

                        bundle.putString("PRODUCT_UID", product.getUid() + "");
                        bundle.putString("PRODUCT_NAME", product.getName());
                        bundle.putString("PRODUCT_PRICE", product.getPrice());

                        insertFragment.setArguments(bundle);
                        manager.beginTransaction().replace(R.id.content, insertFragment).commit();
                    }else
                    {
                        Toast.makeText(MainActivity.this,"Unable to edit product!",Toast.LENGTH_LONG).show();
                    }
                    super.onPostExecute(product);
                }
            }.execute();
        }
    }
    private void switchToFragmentUpdateFragment() {
        new AsyncTask(){

            @Override
            protected Object doInBackground(Object[] objects) {
                DummyContent.ITEMS.clear();
                DummyContent.ITEM_MAP.clear();
                List<Product> products1=database.getProductDao().getAll();
                int i=0;
                for (Product product1:products1
                        ) {
                    i++;
                    DummyContent.DummyItem item=new DummyContent.DummyItem(product1.getUid()+"",product1.getName(),product1.getPrice());
                    DummyContent.ITEMS.add(item);
                    DummyContent.ITEM_MAP.put(item.id, item);

                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {

                super.onPostExecute(o);
            }
        }.execute();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content, new UpadateFragment()).commit();
    }
    private void switchToFragmentDeleteFragment() {
        new AsyncTask(){

            @Override
            protected Object doInBackground(Object[] objects) {
                DummyContent.ITEMS.clear();
                DummyContent.ITEM_MAP.clear();
                List<Product> products1=database.getProductDao().getAll();
                int i=0;
                for (Product product1:products1
                        ) {
                    i++;
                    DummyContent.DummyItem item=new DummyContent.DummyItem(product1.getUid()+"",product1.getName(),product1.getPrice());
                    DummyContent.ITEMS.add(item);
                    DummyContent.ITEM_MAP.put(item.id, item);

                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {

                super.onPostExecute(o);
            }
        }.execute();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content, new DeleteFragment()).commit();
    }
    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
