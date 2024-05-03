package com.example.moneymanager.Adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.moneymanager.Activity.AddIncomeExpenseActivity;
import com.example.moneymanager.Model.Category;
import com.example.moneymanager.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class IncomeExpenseAdapter extends RecyclerView.Adapter<IncomeExpenseAdapter.MyViewHolder> {

    private Context activityContext;
    ArrayList<Category> arrList;
    public boolean isTrans=false;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName;
        public ImageView imgIcon;

        public MyViewHolder(View view) {
            super(view);
            imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
            txtName = view.findViewById(R.id.txtName);
        }
    }

    public IncomeExpenseAdapter(Context activity, ArrayList<Category> list,boolean isTrans) {
        this.activityContext = activity;
        this.arrList=list;
        this.isTrans=isTrans;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_incomeexpense, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        try
        {
            holder.txtName.setText(arrList.get(position).getName());
            Glide.with(activityContext)
                    .load(Uri.parse("file:///android_asset/icons/"+arrList.get(position).getIcon()))
                    .into(holder.imgIcon);
        }
        catch(Exception ex)
        {
            return;
        }

        holder.imgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isTrans){
                    ((AddIncomeExpenseActivity)activityContext).setResultData(arrList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrList.size();
    }
}