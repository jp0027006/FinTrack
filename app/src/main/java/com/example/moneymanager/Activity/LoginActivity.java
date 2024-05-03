package com.example.moneymanager.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.moneymanager.Database.DBhelper;
import com.example.moneymanager.Model.Category;
import com.example.moneymanager.Model.Person;
import com.example.moneymanager.R;
import com.example.moneymanager.Utils.AlertDialog;
import com.example.moneymanager.Utils.PermissionManager;
import com.example.moneymanager.Utils.SharepreferenceUtils;
import com.example.moneymanager.databinding.ActivityLoginactivityBinding;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class LoginActivity extends AppCompatActivity {

    AlertDialog dialog;
    DBhelper db;
    ActivityLoginactivityBinding binding;
    SharepreferenceUtils sharepreferenceUtils;
    Context mcontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mcontext=this;
        binding = ActivityLoginactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharepreferenceUtils=new SharepreferenceUtils(this);
        dialog = new AlertDialog(LoginActivity.this);
        db=new DBhelper(this);
        InsertCategory();
        askPermissions();
        binding.btnsingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent signup = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(signup);
            }
        });

        binding.forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgotIntent = new Intent(LoginActivity.this, ForgotscrenActivity.class);
                startActivity(forgotIntent);

            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
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

        String Email = binding.edEmail.getText().toString();
        String password = binding.etpassword.getText().toString();

        if (Email.isEmpty()) {
            dialog.ShowValidationAlertDialog("please enter the email");
        }
        else if (!isValidEmail(Email)) {
            dialog.ShowValidationAlertDialog("please enter valid email");
        }
        else if (password.isEmpty()) {
            dialog.ShowValidationAlertDialog("please enter the password");
        } else {
            boolean checkCredentials = db.checkCredentials(db.getReadableDatabase(), Email, password);
            if (checkCredentials) {
                Person person=db.getPersonData(db.getReadableDatabase(),Email,password);
                sharepreferenceUtils.setPerson(person);
                sharepreferenceUtils.setAutologin(true);
                Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(LoginActivity.this, "Welcome to FinTrack", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(LoginActivity.this,"email password dont  match",Toast.LENGTH_SHORT).show();
            }

        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = mcontext.getAssets().open("Category.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void InsertCategory(){
        if(!db.getCountCategory(db.getReadableDatabase())){
            new InsertData().execute();
        }
    }


    private final class InsertData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            try {
                String response=loadJSONFromAsset();
                if(!response.isEmpty()){
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.optJSONArray("data");
                    if(jsonArray.length()>0){
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject obj=jsonArray.optJSONObject(i);
                            Category category=new Category();
                            category.setName(obj.optString("name"));
                            category.setIcon(obj.optString("icon"));
                            category.setType(obj.optInt("type"));
                            db.insertCategoryData(db.getWritableDatabase(),category);
                        }
                    }
                }

            }catch (Exception e){

            }

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }

    private String[] permissions = new String[]{
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,

    };
    private void askPermissions() {
        ActivityCompat.requestPermissions(LoginActivity.this, permissions, 7);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 7) {
            if (!PermissionManager.verifyPermission(grantResults)) {
                Set<String> set = new HashSet<>();
                for (String perm : permissions) {
                    if (!PermissionManager.checkPermission(this, perm)
                            && ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
                        set.add(perm);
                    }
                }
                if (set.size() > 0) {
                    String someRationalMsg = "You have to give permission for to use this application.";
                    Toast.makeText(LoginActivity.this, someRationalMsg, Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
}


