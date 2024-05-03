package com.example.moneymanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.moneymanager.Model.Category;
import com.example.moneymanager.Model.Person;
import com.example.moneymanager.R;

import java.util.ArrayList;

public class ForgotscrenActivity extends AppCompatActivity {
    EditText Email;
    Button submit;
    ViewDataBinding binding;
    ArrayList<Person> arrayList=new ArrayList<>();
    ImageView imgBack;
    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotscren);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgotscren);
        Email=findViewById(R.id.Email);
        submit=findViewById(R.id.submit);
        imgBack=findViewById(R.id.imgBack);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setResultData(person);
                Toast.makeText(ForgotscrenActivity.this, "Succesfully Change", Toast.LENGTH_SHORT).show();

            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imgBack=new Intent(ForgotscrenActivity.this, LoginActivity.class);
                startActivity(imgBack);
            }
        });









    }
    public void setResultData(Person model){
        Intent intent=getIntent();
        intent.putExtra("Person",model);
        setResult(RESULT_OK,intent);
        finish();

    }

}