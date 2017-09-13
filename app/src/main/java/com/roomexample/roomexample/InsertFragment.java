package com.roomexample.roomexample;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.roomexample.roomexample.room.Product;
import com.roomexample.roomexample.room.ProductDatabase;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InsertFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InsertFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsertFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_UID = "PRODUCT_UID";
    private static final String ARG_PARAM1 = "PRODUCT_NAME";
    private static final String ARG_PARAM2 = "PRODUCT_PRICE";

    // TODO: Rename and change types of parameters
    private String mParamUid;
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public InsertFragment() {
        // Required empty public constructor
    }
    private EditText inputProductName;
    private EditText inputProductPrice;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InsertFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InsertFragment newInstance(String param1, String param2) {
        InsertFragment fragment = new InsertFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamUid = getArguments().getString(ARG_PARAM_UID);
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_insert, container, false);
        // Inflate the layout for this fragment
        FloatingActionButton fab = (FloatingActionButton)view. findViewById(R.id.fab);
        inputProductName=view.findViewById(R.id.input_product_name);
        inputProductPrice=view.findViewById(R.id.input_product_price);
        inputProductName.setText(mParam1);
        inputProductPrice.setText(mParam2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                if( inputProductName.getText().toString().length() == 0 )
                    inputProductName.setError( "Product name is required!" );
                else if(inputProductPrice.getText().toString().length()== 0)
                    inputProductPrice.setError( "Product price is required!" );
                else{
                    final ProductDatabase database=((MainActivity)getActivity()).getDatabase();
                    final List<Product> products=new ArrayList<>();
                    Product product=new Product();
                    product.setName(inputProductName.getText().toString());
                    product.setPrice(inputProductPrice.getText().toString());
                    products.add(product);
                    new AsyncTask<List<Product>, Integer, Integer>() {
                        @Override
                        protected Integer doInBackground(List<Product>... lists) {
                            int returnFlag=0;
                            if(mParamUid==null || mParamUid.equals(""))
                            {
                                database.getProductDao().insertAll(products);
                                returnFlag=1;

                            }else {
                                product.setUid(Integer.parseInt(mParamUid));
                                database.getProductDao().update(product);
                                returnFlag=2;
                            }
                            return returnFlag;
                        }

                        @Override
                        protected void onPostExecute(Integer rFlag) {
                            inputProductName.setText("");
                            inputProductPrice.setText("");
                            if(rFlag==0)
                                Toast.makeText(getActivity(),"Error !",Toast.LENGTH_LONG).show();
                            if(rFlag==1)
                                Toast.makeText(getActivity(),"Product inserted successfully!",Toast.LENGTH_LONG).show();
                            if(rFlag==2) {
                                Toast.makeText(getActivity(), "Product updated successfully!", Toast.LENGTH_LONG).show();
                                ((MainActivity)getActivity()).navigation.setSelectedItemId(R.id.navigation_update);
                            }

                        }
                    }.execute(products);
                }

            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}

