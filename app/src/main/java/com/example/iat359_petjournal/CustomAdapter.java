package com.example.iat359_petjournal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    public ArrayList<String> eventList;
    Context context;

    public CustomAdapter(ArrayList<String> list) {
        this.eventList = list;
    }

    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_row,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomAdapter.MyViewHolder holder, int position) {

        String[]  results = (eventList.get(position).toString()).split(",");
        holder.idTextView.setText(results[0]);
        holder.taskTextView.setText(results[1]);
        holder.startTimeTextView.setText(results[2]);
        holder.endTimeTextView.setText(results[3]);
    }


    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView idTextView, taskTextView, startTimeTextView, endTimeTextView;
        public LinearLayout myLayout;
        public Button deleteEvent;

        Context context;

        public MyViewHolder(View itemView) {
            super(itemView);
            myLayout = (LinearLayout) itemView;

            idTextView = (TextView) itemView.findViewById(R.id.idTextView);
            taskTextView = (TextView) itemView.findViewById(R.id.taskTextView);
            startTimeTextView = (TextView) itemView.findViewById(R.id.startTimeTextView);
            endTimeTextView = (TextView) itemView.findViewById(R.id.endTimeTextView);
            deleteEvent = (Button) itemView.findViewById(R.id.deleteEvent);

            itemView.setOnClickListener(this);
            context = itemView.getContext();
            deleteEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Log.d("mylog", "" + position);
                        MyDatabase db = new MyDatabase(context);
                        db.deleteEvent(idTextView.getText().toString());
                        eventList.remove(position);
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