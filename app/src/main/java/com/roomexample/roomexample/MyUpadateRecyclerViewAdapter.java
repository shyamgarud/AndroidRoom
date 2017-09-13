package com.roomexample.roomexample;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.roomexample.roomexample.UpadateFragment.OnListFragmentInteractionListener;
import com.roomexample.roomexample.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyUpadateRecyclerViewAdapter extends RecyclerView.Adapter<MyUpadateRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    public int mSelectedItem = -1;
    private List mItems;
    Activity mActivity;
    public MyUpadateRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener, Activity activity) {
        mValues = items;
        mListener = listener;
        mActivity=activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_upadate, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentNameView.setText(mValues.get(position).name);
        holder.mContentPriceView.setText(mValues.get(position).price);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
        holder.mRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                     ((MainActivity)mActivity).navigation.setSelectedItemId(R.id.navigation_insert);
                    ((MainActivity)mActivity).switchToFragmentInsertFragment(Integer.parseInt(mValues.get(position).id));
                }
            }
        });
        holder.mRadioButton.setChecked(position == mSelectedItem);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentNameView;
        public final TextView mContentPriceView;
        public final RadioButton mRadioButton;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentNameView = (TextView) view.findViewById(R.id.name);
            mContentPriceView = (TextView) view.findViewById(R.id.price);
            mRadioButton= (RadioButton) view.findViewById(R.id.radioButton);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentNameView.getText() + "'";
        }
    }
}
