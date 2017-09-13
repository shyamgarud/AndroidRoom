package com.roomexample.roomexample;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.roomexample.roomexample.dummy.DummyContent;
import com.roomexample.roomexample.dummy.DummyContent.DummyItem;
import com.roomexample.roomexample.room.Product;
import com.roomexample.roomexample.room.ProductDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class DeleteFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DeleteFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static DeleteFragment newInstance(int columnCount) {
        DeleteFragment fragment = new DeleteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete_list, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.delete_recycler_view);
        // Set the adapter
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        MyDeleteRecyclerViewAdapter myDeleteRecyclerViewAdapter=new MyDeleteRecyclerViewAdapter(DummyContent.ITEMS, mListener);
        recyclerView.setAdapter(myDeleteRecyclerViewAdapter);

        FloatingActionButton fab = (FloatingActionButton)view. findViewById(R.id.fab_delete);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<List<Product>, Integer, Integer>() {
                    @Override
                    protected Integer doInBackground(List<Product>... lists) {
                        int returnFlag=0;
                        ProductDatabase database=((MainActivity)getActivity()).getDatabase();
                        List<Product> products=new ArrayList<Product>();
                        List<DummyItem> dummyItemList=new ArrayList<DummyItem>();
                        for (DummyItem dummyItem:DummyContent.ITEMS
                                ) {
                            if(dummyItem.isSelected) {
                                Product product=new Product();
                                product.setUid(Integer.parseInt(dummyItem.id));
                                products.add(product);
                                returnFlag++;
                                dummyItemList.add(dummyItem);
                            }
                        }
                        DummyContent.ITEMS.removeAll(dummyItemList);
                        database.getProductDao().deleteProducts(products);

                        return returnFlag;
                    }

                    @Override
                    protected void onPostExecute(Integer rFlag) {
                        myDeleteRecyclerViewAdapter.notifyDataSetChanged();
                        if(rFlag==0)
                            Toast.makeText(getActivity(),"No record found !",Toast.LENGTH_LONG).show();
                        if(rFlag==1)
                            Toast.makeText(getActivity(),rFlag+" record deleted successfully!",Toast.LENGTH_LONG).show();
                        if(rFlag==2) {
                            Toast.makeText(getActivity(),rFlag+" records deleted successfully!",Toast.LENGTH_LONG).show();

                        }

                    }
                }.execute();


            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
