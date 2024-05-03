package com.example.moneymanager.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moneymanager.Database.DBhelper;
import com.example.moneymanager.Model.Category;
import com.example.moneymanager.Model.Transactions;
import com.example.moneymanager.R;
import com.example.moneymanager.databinding.TransactionBinding;

import java.util.Calendar;

public class TransactionActivity extends AppCompatActivity {
    TransactionBinding binding;
    Category category;
    Transactions transactions;
    DBhelper dBhelper;
    String Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.transaction);
        dBhelper=new DBhelper(this);


        binding.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TransactionActivity.this,AddIncomeExpenseActivity.class);
                intent.putExtra("isFrom","transaction");
                startActivityForResult(intent,1001);
            }
        });
        binding.btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(binding.edtName.getText().toString().trim().isEmpty()){
                    Toast.makeText(TransactionActivity.this,"please enter the name",Toast.LENGTH_SHORT).show();
                }
                else if(binding.amount.getText().toString().trim().isEmpty()){
                    Toast.makeText(TransactionActivity.this,"please enter amount",Toast.LENGTH_SHORT).show();
                }
                else if(binding.category.getText().toString().trim().isEmpty()){
                    Toast.makeText(TransactionActivity.this,"please select category",Toast.LENGTH_SHORT).show();
                }
                else if(binding.calender.getText().toString().trim().isEmpty()){
                    Toast.makeText(TransactionActivity.this,"please select the date",Toast.LENGTH_SHORT).show();
                }
                else{
                    transactions=new Transactions();
                    transactions.setCat_ID(category.getCatID());
                    transactions.setTrans_name(binding.edtName.getText().toString());
                    transactions.setTrans_amount(Double.parseDouble(binding.amount.getText().toString()));
                    transactions.setTrans_type(""+category.getType());
                    transactions.setCat_icon(category.getIcon());
                    transactions.setCat_name(category.getName());
                    transactions.setTrans_date(Date);

                    dBhelper.insertTransactionData(dBhelper.getWritableDatabase(),transactions);
                    Toast.makeText(TransactionActivity.this,"Transaction Added Successfully",Toast.LENGTH_SHORT).show();

                    Intent intent=getIntent();
                    setResult(RESULT_OK,intent);
                    finish();

                    Intent i = new Intent(TransactionActivity.this,HomeScreenActivity.class);
                    startActivity(i);
                }
            }
        });
        binding.Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }

        });

        final Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                Date=year+"-"+(monthOfYear + 1)+"-"+dayOfMonth+" 00:00:00";
                binding.calender.setText(dayOfMonth + " / " + (monthOfYear + 1) + " / " + year);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        binding.calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1001 && resultCode==RESULT_OK){

            category=(Category)data.getSerializableExtra("category");
            binding.category.setText(category.getName());
            Glide.with(this)
                    .load(Uri.parse("file:///android_asset/icons/"+category.getIcon()))
                    .into(binding.imgIcon);

        }
    }
}