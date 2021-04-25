package com.sample.fit13;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private ArrayList<ExampleItem> mExampleList;
    private OnItemClickListener mlistener;

    Context c;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener = listener;
    }


    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_log, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mlistener);
        return evh;
    }

    public ExampleAdapter(Context c, ArrayList<ExampleItem> exampleList){
        this.c = c;
        mExampleList = exampleList;
    }



    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);
        holder.mCloseImageView.setImageResource(currentItem.getCloseImg());
        holder.mWeightImageView.setImageResource(currentItem.getWeightImg());
        holder.mTitle.setText(currentItem.getTitle());
        holder.mDuration.setText(currentItem.getDuration());
        holder.mSeeDetails.setText("See Details");

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {

                String gTitle= mExampleList.get(position).getTitle();
                String gDuration = mExampleList.get(position).getDuration();
                String gDate = mExampleList.get(position).getDate();
                String gDescription = mExampleList.get(position).getDescription();
                ArrayList<ExampleItem> send = mExampleList;

                Intent intent = new Intent(c, WorkoutDetail.class);
                intent.putExtra("Ex", send);
                intent.putExtra("iTitle", gTitle);
                intent.putExtra("iDur", gDuration);
                intent.putExtra("iDate", gDate);
                intent.putExtra("iDes", gDescription);
                c.startActivity(intent);



            }
        });




    }


    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView mWeightImageView, mCloseImageView;
        TextView mTitle, mDuration, mDate, mDescription, mSeeDetails;
        ItemClickListener itemClickListener;

        public ExampleViewHolder(@NonNull View itemView, final OnItemClickListener listener) {


            super(itemView);
            mWeightImageView = itemView.findViewById(R.id.imageView);
            mTitle = itemView.findViewById(R.id.log_title);
            mDuration = itemView.findViewById(R.id.log_duration);
            mCloseImageView = itemView.findViewById(R.id.imageView2);
            mDescription = itemView.findViewById(R.id.detail_description);
            mDate = itemView.findViewById(R.id.detail_date);
            mSeeDetails = itemView.findViewById(R.id.log_link);

            mSeeDetails.setOnClickListener(this);
            mCloseImageView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onDeleteClick(position);
                    }

                }
            });

        }

        public void setItemClickListener(ItemClickListener ic){
            this.itemClickListener = ic;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClickListener(v, getLayoutPosition());
        }

    }



}


