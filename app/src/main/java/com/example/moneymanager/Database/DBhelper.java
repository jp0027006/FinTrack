package com.example.moneymanager.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.moneymanager.Model.Category;
import com.example.moneymanager.Model.Person;
import com.example.moneymanager.Model.Transactions;

import java.util.ArrayList;
import java.util.List;

public class DBhelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "moneymanagerDB";
    private static final int DB_VERSION = 1;

    private static final String tbl_person = "tbl_person";
    private static final String tbl_category = "tbl_category";
    private static final String tbl_transaction = "tbl_transaction";
    private static final String tbl_feedback = "tbl_feedback";
    private static final String perID = "per_ID";
    private static final String catID = "cat_ID";
    private static final String transID = "trans_ID";

    private static final String feedID = "feed_ID";

    public DBhelper(Context mcontext) {
        super(mcontext, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "Create table " + tbl_person + " ( " + perID + " integer primary key autoincrement, fname text, lname text, phonenumber text, email text, password text)";
        db.execSQL(query);
        query = "Create table " + tbl_category + " ( " + catID + " integer primary key autoincrement, cat_name text, cat_icon text, cat_type integer)";
        db.execSQL(query);
        query = "Create table " + tbl_transaction + " ( " + transID + " integer primary key autoincrement, trans_name text, trans_type text, trans_amount numeric, cat_ID integer, cat_icon text, cat_name text, trans_date DATETIME)";
        db.execSQL(query);
        query = "Create table " + tbl_feedback + " ( " + feedID + " integer primary key autoincrement, person_name text, person_email text, feedback_mes text)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void updatepassword(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("password", newPassword);
        int id = db.update(tbl_person, value, "email = ?", new String[]{email});
        Log.e("updateID", "" + id);
    }

    public void insertPersonData(SQLiteDatabase db, Person person) {


        ContentValues value = new ContentValues();
        value.put("fname", person.getFirstname());
        value.put("lname", person.getLastname());
        value.put("email", person.getEmail());
        value.put("phonenumber", person.getPhone());
        value.put("password", person.getPassword());
        long id = db.insert(tbl_person, null, value);
        Log.e("InsertID", "" + id);
    }
    public void updatePersonData(SQLiteDatabase db, Person person,String email) {

        ContentValues value = new ContentValues();
        value.put("fname", person.getFirstname());
        value.put("lname", person.getLastname());
        value.put("email", person.getEmail());
        value.put("phonenumber", person.getPhone());
        value.put("password", person.getPassword());
        int id = db.update(tbl_person, value, "email = ?", new String[]{email});

        Log.e("updateID", "" + id);


    }

    public void insertCategoryData(SQLiteDatabase db, Category category) {

        ContentValues value = new ContentValues();
        value.put("cat_name", category.getName());
        value.put("cat_icon", category.getIcon());
        value.put("cat_type", category.getType());
        long id = db.insert(tbl_category, null, value);
    }
    public void insertTransactionData(SQLiteDatabase db, Transactions data) {

        ContentValues value = new ContentValues();
        value.put("trans_name", data.getTrans_name());
        value.put("trans_type", data.getTrans_type());
        value.put("cat_ID", data.getCat_ID());
        value.put("trans_amount", data.getTrans_amount());
        value.put("cat_icon", data.getCat_icon());
        value.put("cat_name", data.getCat_name());
        value.put("trans_date", data.getTrans_date());
        long id = db.insert(tbl_transaction, null, value);
    }


    public int userExist(SQLiteDatabase db, String email) {
        int count = 0;
        String query = "select * from " + tbl_person + " where email= '" + email + "'";
        Cursor cursor = db.rawQuery(query, null);
        count = cursor.getCount();
        return count;
    }
    public boolean getCountCategory(SQLiteDatabase db) {
        String query = "select * from " + tbl_category;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
    public ArrayList<Category> getCategory(SQLiteDatabase db,int type) {
        String query = "select * from " + tbl_category + " where cat_type='" + type +"'";
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Category> Arrcategory=new ArrayList<>();
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            while (cursor.isAfterLast() == false) {
                Category   category = new Category();
                category.setCatID(cursor.getInt(0));
                category.setName(cursor.getString(1));
                category.setIcon(cursor.getString(2));
                category.setType(cursor.getInt(3));
                Arrcategory.add(category);
                cursor.moveToNext();
            }
        }
        return Arrcategory;
    }

    public String getCategoryIconByID(SQLiteDatabase db,int id) {
        String query = "select * from " + tbl_category + " where cat_ID='" + id +"'";
        Cursor cursor = db.rawQuery(query, null);
        String catIcon="";
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            while (cursor.isAfterLast() == false) {
                catIcon=cursor.getString(2);
            }
        }
        return catIcon;
    }

    public boolean checkCredentials(SQLiteDatabase db, String email, String pass) {
        String query = "select * from " + tbl_person + " where email='" + email + "' and password='" + pass + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
    public Person getPersonData(SQLiteDatabase db, String email, String pass) {
        String query = "select * from " + tbl_person + " where email='" + email + "' and password='" + pass + "'";
        Cursor cursor = db.rawQuery(query, null);
        Person persondata=null;
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (cursor.isAfterLast() == false) {
                persondata = new Person();
                persondata.setPerID(cursor.getInt(0));
                persondata.setFirstname(cursor.getString(1));
                persondata.setLastname(cursor.getString(2));
                persondata.setPhone(cursor.getString(3));
                persondata.setEmail(cursor.getString(4));
                persondata.setPassword(cursor.getString(5));
                cursor.moveToNext();
            }

        } else {
            persondata=null;

        }
        return persondata;

    }

    public ArrayList<Transactions> getTransactions(SQLiteDatabase db,String start_date,String end_date){
        ArrayList<Transactions> arrayList=new ArrayList<>();
        String query="select  * from tbl_transaction where trans_date BETWEEN '"+start_date+"' AND '"+end_date+"' ORDER by  trans_date DESC";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            arrayList=new ArrayList<>();
            while (cursor.isAfterLast() == false) {

                Transactions transactions=new Transactions();
                transactions.setTrans_ID(cursor.getInt(0));
                transactions.setTrans_name(cursor.getString(1));
                transactions.setTrans_type(cursor.getString(2));
                transactions.setTrans_amount(cursor.getDouble(3));
                transactions.setCat_ID(cursor.getInt(4));
                transactions.setCat_icon(cursor.getString(5));
                transactions.setCat_name(cursor.getString(6));
                transactions.setTrans_date(cursor.getString(7));

                arrayList.add(transactions);

                cursor.moveToNext();
            }
        } else {
            Log.e("No Transaction", "No data available");
        }
        Log.e("Transaction Size", ""+arrayList.size());
        return arrayList;
    }

    public ArrayList<Transactions> getTransactionsWithTotalIncomeAmount(SQLiteDatabase db, String start_date, String end_date) {
        ArrayList<Transactions> arrayList = new ArrayList<>();
        double totalIncomeAmount = 0.0;

        String query = "SELECT * FROM tbl_transaction WHERE trans_date BETWEEN '" + start_date + "' AND '" + end_date + "' ORDER BY trans_date DESC";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                Transactions transactions = new Transactions();
                transactions.setTrans_ID(cursor.getInt(0));
                transactions.setTrans_name(cursor.getString(1));
                transactions.setTrans_type(cursor.getString(2));
                transactions.setTrans_amount(cursor.getDouble(3));
                transactions.setCat_ID(cursor.getInt(4));
                transactions.setCat_icon(cursor.getString(5));
                transactions.setCat_name(cursor.getString(6));
                transactions.setTrans_date(cursor.getString(7));
                if ("1".equals(cursor.getString(2))) {
                    totalIncomeAmount += cursor.getDouble(3);
                }

                arrayList.add(transactions);

                cursor.moveToNext();
            }
        } else {
            Log.e("No Transaction", "No data available");
        }
        Transactions totalIncomeItem = new Transactions();
        totalIncomeItem.setTrans_amount(totalIncomeAmount);
        arrayList.add(0, totalIncomeItem);

        Log.e("Transaction Size", "" + arrayList.size());
        return arrayList;
    }



    public ArrayList<Person> getAllPersonData(SQLiteDatabase db) {

        ArrayList<Person> arrayList = new ArrayList<>();
        String query = "select * from " + tbl_person;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {

            while (cursor.isAfterLast() == false) {

                Person persondata = new Person();
                persondata.setPerID(cursor.getInt(0));
                persondata.setFirstname(cursor.getString(1));
                persondata.setLastname(cursor.getString(2));
                persondata.setEmail(cursor.getString(3));
                persondata.setPassword(cursor.getString(4));

                arrayList.add(persondata);


                cursor.moveToNext();
            }

        } else {
            Log.e("No Records", "No data available");
        }

        Log.e("Person Size", "" + arrayList.size());

        return arrayList;

    }

    public List<Transactions> getAllTransactions() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Transactions> transactionsList = new ArrayList<>();

        String query = "SELECT * FROM " + tbl_transaction + " ORDER BY trans_date DESC";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Transactions transaction = new Transactions();
                transaction.setTrans_ID(cursor.getInt(cursor.getColumnIndex(transID)));
                transaction.setTrans_name(cursor.getString(cursor.getColumnIndex("trans_name")));
                transaction.setTrans_type(cursor.getString(cursor.getColumnIndex("trans_type")));
                transaction.setTrans_amount(cursor.getDouble(cursor.getColumnIndex("trans_amount")));
                transaction.setCat_ID(cursor.getInt(cursor.getColumnIndex(catID)));
                transaction.setCat_icon(cursor.getString(cursor.getColumnIndex("cat_icon")));
                transaction.setCat_name(cursor.getString(cursor.getColumnIndex("cat_name")));
                transaction.setTrans_date(cursor.getString(cursor.getColumnIndex("trans_date")));

                transactionsList.add(transaction);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return transactionsList;
    }
}