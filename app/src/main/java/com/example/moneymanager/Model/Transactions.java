package com.example.moneymanager.Model;

public class Transactions {

    private int trans_ID;
    private String trans_name,trans_type,trans_date;
    private Double trans_amount;
    private int cat_ID;
    private String cat_icon,cat_name;

    public String getCat_icon() {
        return cat_icon;
    }

    public void setCat_icon(String cat_icon) {
        this.cat_icon = cat_icon;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public int getCat_ID() {
        return cat_ID;
    }

    public void setCat_ID(int cat_ID) {
        this.cat_ID = cat_ID;
    }

    public int getTrans_ID() {
        return trans_ID;
    }

    public void setTrans_ID(int trans_ID) {
        this.trans_ID = trans_ID;
    }

    public String getTrans_name() {
        return trans_name;
    }

    public void setTrans_name(String trans_name) {
        this.trans_name = trans_name;
    }

    public String getTrans_type() {
        return trans_type;
    }

    public void setTrans_type(String trans_type) {
        this.trans_type = trans_type;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    public Double getTrans_amount() {
        return trans_amount;
    }

    public void setTrans_amount(Double trans_amount) {
        this.trans_amount = trans_amount;
    }

    public double getAmount() {
        return 0;
    }

    public Object getType() {
        return null;
    }
}
