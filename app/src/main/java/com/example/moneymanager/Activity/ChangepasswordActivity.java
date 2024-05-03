package com.example.moneymanager.Activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.moneymanager.Database.DBhelper;
import com.example.moneymanager.Model.Person;
import com.example.moneymanager.R;
import com.example.moneymanager.Utils.AlertDialog;
import com.example.moneymanager.Utils.SharepreferenceUtils;

public class ChangepasswordActivity extends AppCompatActivity {
    ImageView back;
    DBhelper db;
    EditText oldpass;
    EditText newpass;
    EditText email_user;
    private Person personData;
    SharepreferenceUtils sharepreferenceUtils;
    Button submit;
    AlertDialog dialog;
    EditText repeatpass;
    TextView Forgot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);
        dialog = new AlertDialog(ChangepasswordActivity.this);
        submit = findViewById(R.id.submit);
        oldpass = findViewById(R.id.oldpass);
        newpass = findViewById(R.id.Newpass);
        repeatpass = findViewById(R.id.repeat);
        sharepreferenceUtils = new SharepreferenceUtils(this);
        personData=sharepreferenceUtils.getPerson();
        email_user = findViewById(R.id.email_for);
        db=new DBhelper(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_user.getText().toString();
                String old = oldpass.getText().toString();
                String newP = newpass.getText().toString();
                String Repeat = repeatpass.getText().toString();

                if(old.isEmpty() || newP.isEmpty() || Repeat.isEmpty()) {
                    dialog.ShowValidationAlertDialog("Some Field Is Empty");
                }
                else if(!newP.matches(Repeat)){
                    dialog.ShowValidationAlertDialog("New and Repeat password does not match");
                }
                else if (newP.matches(old)) {
                    dialog.ShowValidationAlertDialog("New and Old password are same");
                }
                else{
                    boolean checkCredentials = db.checkCredentials(db.getReadableDatabase(),email,old);
                    if (checkCredentials) {
                        personData.setPassword(newP);
                        db.updatepassword(email, newP);
                        sharepreferenceUtils.setPerson(personData);
                        Toast.makeText(ChangepasswordActivity.this , "Password has been changed" , Toast.LENGTH_SHORT).show();
                        Intent passwordchange=new Intent(ChangepasswordActivity.this, HomeScreenActivity.class);
                        startActivity(passwordchange);
                    }
                    else {
                        Toast.makeText(ChangepasswordActivity.this,"email password does not match",Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
        Forgot=findViewById(R.id.Forgot);
        Forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Forgot=new Intent(ChangepasswordActivity.this,ForgotscrenActivity.class);
                startActivity(Forgot);
            }
        });
        back=findViewById(R.id.imgBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }

        });
    }
}