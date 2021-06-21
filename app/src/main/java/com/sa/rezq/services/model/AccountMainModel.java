package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class AccountMainModel implements Serializable {
    private final String TAG = "AccountMainModel";
    private final String
            RESPONSE            = "response",
            STATUS              = "status",
            MESSAGE             = "message";

    AccountListModel
            accountListModel      = null;


    String message = null;
    boolean isStatus=false;

    public AccountMainModel(){}


    public AccountListModel getAccountListModel() {
        return accountListModel;
    }

    public void setAccountListModel(AccountListModel accountListModel) {
        this.accountListModel = accountListModel;
    }

    public boolean isStatus() {
        return isStatus;
    }

    public void setStatus(boolean status) {
        isStatus = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);
            if(json.has(MESSAGE)){this.message = json.getString(MESSAGE);}
            if(json.has(STATUS)){this.isStatus = json.getBoolean(STATUS);}

            if(json.has(RESPONSE)){
                AccountListModel accountListModel = new AccountListModel();
                JSONArray jsonArray = new JSONArray();
                jsonArray = json.getJSONArray(RESPONSE);
                if(jsonArray!=null){accountListModel.toObject(jsonArray);}
                this.accountListModel = accountListModel;
            }

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
            jsonMain.put(STATUS, isStatus);
            jsonMain.put(MESSAGE, message);
            jsonMain.put(RESPONSE, this.accountListModel != null? new JSONArray(this.accountListModel.toString(true)) : new JSONArray());

            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}
