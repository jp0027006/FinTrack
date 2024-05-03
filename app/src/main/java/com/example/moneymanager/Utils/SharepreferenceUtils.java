package com.example.moneymanager.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.moneymanager.Model.Person;
import com.google.gson.Gson;
import static android.content.Context.MODE_PRIVATE;


public class SharepreferenceUtils {

    private Context mcontext;
    private SharedPreferences prefs;
    private String MY_PREFS_NAME = "moneyManager";
    private SharedPreferences.Editor editor;


    public SharepreferenceUtils(Context mcontext) {
        this.mcontext = mcontext;
        this.prefs = mcontext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

    }


    public void setAutologin(boolean isautologin) {

        editor = prefs.edit();
        editor.putBoolean("isAutoLogin", isautologin);
        editor.commit();

    }

    public boolean getAutologin() {
        return prefs.getBoolean("isAutoLogin", false);
    }

    public void clearSession() {
        editor = prefs.edit();
        editor.clear().commit();

    }

    public void saveStringData(String key, String value){
        editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public String getStringData(String key){
        return  prefs.getString(key,"");
    }

    public void setPerson( Person loc) {
        Gson gson = new Gson();
        String json = gson.toJson(loc);
        editor = prefs.edit();
        editor.putString("person", json);
        editor.commit();

    }

    public Person getPerson() {
        Gson gson = new Gson();
        String json = prefs.getString("person", null);
        return gson.fromJson(json, Person.class);
    }

}
