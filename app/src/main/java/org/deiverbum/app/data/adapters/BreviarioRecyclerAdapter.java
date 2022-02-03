package org.deiverbum.app.data.adapters;

// Created by cedano on 19/10/17.
//@version 2.0


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.deiverbum.app.R;

import java.util.ArrayList;

@Deprecated
public class BreviarioRecyclerAdapter extends RecyclerView.Adapter<BreviarioRecyclerAdapter.ViewHolder> {

    private ItemListener mListener;
    private ArrayList<BreviarioDataModel> mValues;
    private Context mContext;

    public BreviarioRecyclerAdapter(ArrayList<BreviarioDataModel> values, ItemListener itemListener) {

        mValues = values;
        //mContext = context;
        mListener = itemListener;
    }

    @Override
    public BreviarioRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_main_items, parent, false);
        return new BreviarioRecyclerAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValues.get(position));

    }

    @Override
    public int getItemCount() {

        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(BreviarioDataModel item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewMaterial;
        TextView textView;
        RelativeLayout relativeLayout;
        BreviarioDataModel item;

        ViewHolder(View v) {

            super(v);

            v.setOnClickListener(this);
            textViewMaterial = v.findViewById(R.id.tv_Material);
            textViewMaterial.setBackgroundColor(Color.GRAY);

            textView = v.findViewById(R.id.tv_Elemento);
            relativeLayout = v.findViewById(R.id.relativeLayout);

        }

        public void setData(BreviarioDataModel item) {
            this.item = item;

            textView.setText(item.text);
            textViewMaterial.setText(item.letra);
            // textViewMaterial.setBackground(v.findViewById(R.id.textViewMaterial));
            //textView.setGravity(Gravity.CENTER);
            //textView.setTextColor(Color.GRAY);

//            textViewMaterial.setBackgroundResource(item.drawable);
            textViewMaterial.setBackgroundColor(item.color);
            relativeLayout.setBackgroundColor(item.color);
/*
            int newHeight = 250; // New height in pixels
            int newWidth = 250; // New width in pixels
            imageView.getLayoutParams().height = newHeight;

            // Apply the new width for ImageView programmatically
            imageView.getLayoutParams().width = newWidth;
*/
        }


        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }
}
