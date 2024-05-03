package com.example.moneymanager.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.example.moneymanager.Activity.LoginActivity;
import com.example.moneymanager.R;

public class AlertDialog {
    public Context mcontext;
    android.app.AlertDialog.Builder builder;

    public AlertDialog(Context context) {
        this.mcontext=context;

    }

    public void ShowValidationAlertDialog(String msg){
        builder=new android.app.AlertDialog.Builder(mcontext);
        builder.setTitle(mcontext.getResources().getString(R.string.app_name));
        builder.setMessage(msg);
        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();

            }
        });
        android.app.AlertDialog  dialog=builder.create();
        dialog.show();
    }

    public void ShowLogoutAlertDialog(Context mcontext){
        builder=new android.app.AlertDialog.Builder(mcontext);

        builder.setTitle(mcontext.getResources().getString(R.string.app_name));
        builder.setMessage("Are you sure want to logout?");
        builder.setCancelable(false);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                SharepreferenceUtils sharepreferenceUtils=new SharepreferenceUtils(mcontext);
                sharepreferenceUtils.clearSession();
                Intent login=new Intent(mcontext, LoginActivity.class);
                mcontext.startActivity(login);
                ((Activity)mcontext).finish();


            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        android.app.AlertDialog  dialog=builder.create();
        dialog.show();
    }

}
