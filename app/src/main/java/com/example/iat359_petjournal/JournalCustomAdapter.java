package com.example.iat359_petjournal;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class JournalCustomAdapter extends RecyclerView.Adapter<JournalCustomAdapter.MyViewHolder> {
//    declare byte arraylist
    public ArrayList<byte[]> bitmapArray = new ArrayList<byte[]>();
    public ArrayList<String> sizeArray = new ArrayList<String>();

    public JournalCustomAdapter(ArrayList<byte[]> list, ArrayList<String> list2) {
        this.bitmapArray = list;
        this.sizeArray = list2;
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
        String size = sizeArray.get(position);
        holder.photo.setImageBitmap(bmapRes);
        holder.photo.setTag(size);
        holder.photo.setRotation(90);

    }


    @Override
    public int getItemCount() {
//        returns the list size
        return bitmapArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageButton photo;

        public LinearLayout myLayout;

        public ImageButton deletebtn;

        Context context;

//        intializes variables
        public MyViewHolder(View itemView) {
            super(itemView);
            myLayout = (LinearLayout) itemView;

            photo = (ImageButton) itemView.findViewById(R.id.photoItem);
            deletebtn = (ImageButton) itemView.findViewById(R.id.deletebtn);
            deletebtn.setVisibility(View.GONE);
            itemView.setOnClickListener(this);
            context = itemView.getContext();

            photo.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if(deletebtn.getVisibility()==View.GONE) {
                         deletebtn.setVisibility(View.VISIBLE);
                     }
                     else if(deletebtn.getVisibility()==View.VISIBLE){
                         deletebtn.setVisibility(View.GONE);
                     }
                 }
            });



            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        MyDatabase db = new MyDatabase(context);
                        db.deletePhoto(photo.getTag().toString());
                        bitmapArray.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            });

        }

        @Override
        public void onClick(View view) {
        }


    }
}