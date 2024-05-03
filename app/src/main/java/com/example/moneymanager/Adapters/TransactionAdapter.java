package com.example.moneymanager.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moneymanager.Activity.AddIncomeExpenseActivity;
import com.example.moneymanager.Database.DBhelper;
import com.example.moneymanager.Model.Category;
import com.example.moneymanager.Model.Transactions;
import com.example.moneymanager.R;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {

    private Context activityContext;
    ArrayList<Transactions> arrList;
    DBhelper dBhelper;
    public TransactionAdapter(Context activity, ArrayList<Transactions> list) {
        this.activityContext = activity;
        this.arrList=list;
        dBhelper=new DBhelper(activityContext);
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName,txtAmount,txtDate;
        public ImageView imgIcon;

        public MyViewHolder(View view) {
            super(view);
            imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
            txtName = view.findViewById(R.id.txtName);
            txtAmount=view.findViewById(R.id.txtAmount);
            txtDate=view.findViewById(R.id.txtDate);
        }
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_transaction, parent, false);

        return new MyViewHolder(itemView);
    }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            try {
                Transactions transaction = arrList.get(position);

                holder.txtName.setText(transaction.getTrans_name());
                Glide.with(activityContext)
                        .load(Uri.parse("file:///android_asset/icons/" + transaction.getCat_icon()))
                        .into(holder.imgIcon);
                String[] date = transaction.getTrans_date().split(" ");
                holder.txtDate.setText(date[0]);

                if (position == 0 || !date[0].equals(arrList.get(position - 1).getTrans_date().split(" ")[0])) {
                    holder.txtDate.setVisibility(View.VISIBLE);
                } else {
                    holder.txtDate.setVisibility(View.GONE);
                }
                if (transaction.getTrans_type().equals("1")) {
                    holder.txtAmount.setTextColor(activityContext.getResources().getColor(R.color.green));
                    holder.txtAmount.setText("+" + String.valueOf(transaction.getTrans_amount()));
                } else {
                    holder.txtAmount.setTextColor(activityContext.getResources().getColor(R.color.red));
                    holder.txtAmount.setText("-" + String.valueOf(transaction.getTrans_amount()));
                }
            } catch (Exception e) {
                Toast.makeText(activityContext, "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        @Override
    public int getItemCount() {
        return arrList.size();
    }
}