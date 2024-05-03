package com.example.moneymanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneymanager.Database.DBhelper;
import com.example.moneymanager.Model.Person;
import com.example.moneymanager.R;
import com.example.moneymanager.Utils.AlertDialog;
import com.example.moneymanager.databinding.SignupBinding;

public class SignupActivity<dbhelp> extends AppCompatActivity {
    CheckBox checkBox;
    AlertDialog dialog;
    SignupBinding binding;

    DBhelper dBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.signup);

        dialog = new AlertDialog(SignupActivity.this);
        dBhelper=new DBhelper(this);


        checkBox = (CheckBox) findViewById(R.id.checkBox);

        binding.btnsingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent signin = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(signin);
            }
        });

        binding.btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });

    }
    public  boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void checkValidation() {
        String firstname = binding.edfirstname.getText().toString();
        String lastname = binding.edlastname.getText().toString();
        String email = binding.edemail.getText().toString();
        String phonenumber =binding. edphone.getText().toString();
        String password = binding.edpassword.getText().toString();
        String  repassword =binding. edrepassword.getText().toString();


        if (firstname.isEmpty()) {
            dialog.ShowValidationAlertDialog("Please enter the Firstname");

        } else if (lastname.isEmpty()) {
            dialog.ShowValidationAlertDialog("Please enter the Lastname");
        }
        else if (phonenumber.isEmpty() || phonenumber.trim().length() != 10){
            dialog.ShowValidationAlertDialog("Please enter valid Mobile number");
        }
        else if (email.isEmpty()) {
            dialog.ShowValidationAlertDialog("Please enter valid email");
        }
        else if (!isValidEmail(email)) {
            dialog.ShowValidationAlertDialog("Please enter valid email");
        }
        else if (password.isEmpty()) {
            dialog.ShowValidationAlertDialog("Please enter the password");
        }
        else if (repassword.isEmpty()) {
            dialog.ShowValidationAlertDialog("Please enter the re-password");
        }
       else if(!password.equals(repassword)){
            dialog.ShowValidationAlertDialog("Password and confirm password not matched");
        }
       else if(!binding.checkBox.isChecked()){
            dialog.ShowValidationAlertDialog("Please agree the terms and conditions");
        }
        else {
            Person persondata=new Person();
            persondata.setFirstname(firstname);
            persondata.setLastname(lastname);
            persondata.setEmail(email);
            persondata.setPassword(password);
            persondata.setPhone(phonenumber);

            if(dBhelper.userExist(dBhelper.getReadableDatabase(),email)>0){
                Toast.makeText(SignupActivity.this,"Already Exist, please try another email address",Toast.LENGTH_SHORT).show();
            }else{
                dBhelper.insertPersonData(dBhelper.getWritableDatabase(),persondata);
                Toast.makeText(SignupActivity.this,"Sign up successfully",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}













