package com.example.ivan.jantabg;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private Cursor mData; // data to show
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor ///!!!
    public MyRecyclerViewAdapter(Context context, Cursor offersData) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = offersData;
    }

    // inflates the row layout from xml when needed /// !!!
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each row /// !!!
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mData.moveToPosition(position);
        String title = mData.getString(1);
        String price = mData.getString(2);
        String imgPath = mData.getString(3);
        holder.title.setText(title);
        holder.price.setText(price);
        if(imgPath.equals("no_image")){
            holder.img.setImageResource(R.drawable.empty);
        } else {
            holder.img.setImageBitmap(BitmapFactory.decodeFile(imgPath));
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.getCount();
    }


    // stores and recycles views as they are scrolled off screen // !!!
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public TextView price;
        public ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.text_in_row_title);
            price = (TextView) itemView.findViewById(R.id.text_in_row_price);
            img = (ImageView) itemView.findViewById(R.id.image_in_row);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // method for getting id of offer at click position
    public String getItem(int id) {
        mData.moveToPosition(id);
        return mData.getString(0);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}