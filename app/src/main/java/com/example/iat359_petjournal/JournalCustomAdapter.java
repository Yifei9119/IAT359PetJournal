package com.example.iat359_petjournal;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class JournalCustomAdapter extends RecyclerView.Adapter<JournalCustomAdapter.MyViewHolder> {
//    declare byte arraylist
    ArrayList<byte[]> bitmapArray = new ArrayList<byte[]>();

    public JournalCustomAdapter(ArrayList<byte[]> list) {
        this.bitmapArray = list;
    }

//    inflate the individual item layout to viewholder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.photolist,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        bind the bitmaparray that gets the position and sets te imagebitmap
        Bitmap bmapRes = BitmapFactory.decodeByteArray(bitmapArray.get(position), 0, bitmapArray.get(position).length);
        holder.photo.setImageBitmap(bmapRes);
        holder.photo.setTag(bmapRes.toString());
        holder.photo.setRotation(90);

    }


    @Override
    public int getItemCount() {
//        returns the list size
        return bitmapArray.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageButton photo;

        public LinearLayout myLayout;

        Context context;

//        intializes variables
        public MyViewHolder(View itemView) {
            super(itemView);
            myLayout = (LinearLayout) itemView;

            photo = (ImageButton) itemView.findViewById(R.id.photoItem);

            itemView.setOnClickListener(this);
            context = itemView.getContext();

        }

        @Override
        public void onClick(View view) {
        }


    }
}