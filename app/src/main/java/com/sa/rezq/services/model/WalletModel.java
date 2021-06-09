package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class WalletModel implements Serializable {
    private final String TAG = "WalletModel";
    private final String
            ID                    = "id",
            ORDER_ID              = "order_id",
            TRAN_ID               = "transaction_id",
            BALANCE               = "amount",
            OLD_AMOUNT            = "old_amount",
            UPDATED_AMOUNT        = "updated_amount",
            DATE                  = "created_on",
            TYPE                  = "wallet_type",
            FLAG                  = "added_type",
            CATEGORY_TITLE        = "category_title",
            CATEGORY_ICON         = "category_icon";

    private String
            id                   = null,
            orderId              = null,
            transactionId        = null,
            oldAmount            = null,
            updatedAmount        = null,
            balance              = null,
            date                 = null,
            type                 = null,
            flag                 = null,
            categoryTitle        = null,
            categoryIcon         = null;

    public WalletModel(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOldAmount() {
        return oldAmount;
    }

    public void setOldAmount(String oldAmount) {
        this.oldAmount = oldAmount;
    }

    public String getUpdatedAmount() {
        return updatedAmount;
    }

    public void setUpdatedAmount(String updatedAmount) {
        this.updatedAmount = updatedAmount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public boolean toObject(String jsonObject){
        try{

            JSONObject json = new JSONObject(jsonObject);

            if(json.has(ID)){this.id = json.getString(ID);}
            if(json.has(ORDER_ID)){this.orderId = json.getString(ORDER_ID);}
            if(json.has(TRAN_ID)){this.transactionId = json.getString(TRAN_ID);}
            if(json.has(BALANCE)){this.balance = json.getString(BALANCE);}
            if(json.has(OLD_AMOUNT)){this.oldAmount = json.getString(OLD_AMOUNT);}
            if(json.has(UPDATED_AMOUNT)){this.updatedAmount = json.getString(UPDATED_AMOUNT);}
            if(json.has(DATE)){this.date = json.getString(DATE);}
            if(json.has(TYPE)){this.type = json.getString(TYPE);}
            if(json.has(FLAG)){this.flag = json.getString(FLAG);}
            if(json.has(CATEGORY_TITLE)){this.categoryTitle = json.getString(CATEGORY_TITLE);}
            if(json.has(CATEGORY_ICON)){this.categoryIcon = json.getString(CATEGORY_ICON);}

            return true;
        }catch(Exception ex){
            Log.d(TAG, "Json Exception : " + ex);}
        return false;
    }

    @Override
    public String toString(){
        String returnString = null;

        try{
            JSONObject jsonMain = new JSONObject();

            jsonMain.put(ID, id);
            jsonMain.put(ORDER_ID, orderId);
            jsonMain.put(BALANCE, balance);
            jsonMain.put(TRAN_ID, transactionId);
            jsonMain.put(OLD_AMOUNT, oldAmount);
            jsonMain.put(UPDATED_AMOUNT, updatedAmount);
            jsonMain.put(DATE, date);
            jsonMain.put(TYPE, type);
            jsonMain.put(FLAG, flag);
            jsonMain.put(CATEGORY_TITLE, categoryTitle);
            jsonMain.put(CATEGORY_ICON, categoryIcon);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}


