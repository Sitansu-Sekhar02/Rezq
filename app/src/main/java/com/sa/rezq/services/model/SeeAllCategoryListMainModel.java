package com.sa.rezq.services.model;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class SeeAllCategoryListMainModel implements Serializable {
    private final String TAG = "SeeAllCategoryListMainModel";

    private final String
            RESPONSE            = "response",
            STATUS              = "status_bool",
            STATUSLOGIN         = "status",
            MESSAGE             = "status";

    SeeAllCategoryListModel
            seeAllCategoryListModel      = null;

    String statusMessage = null;

    String message = null;
    boolean isStatus=false,isStatusLogin=false;

    public SeeAllCategoryListMainModel(){}

    public SeeAllCategoryListModel getSeeAllCategoryListModel() {
        return seeAllCategoryListModel;
    }

    public void setSeeAllCategoryListModel(SeeAllCategoryListModel seeAllCategoryListModel) {
        this.seeAllCategoryListModel = seeAllCategoryListModel;
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

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public boolean isStatusLogin() {
        return isStatusLogin;
    }

    public void setStatusLogin(boolean statusLogin) {
        isStatusLogin = statusLogin;
    }

    @SuppressLint("LongLogTag")
    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);
            if(json.has(MESSAGE)){this.message = json.getString(MESSAGE);}
            if(json.has(STATUS)){this.isStatus = json.getBoolean(STATUS);}
            if(json.has(STATUSLOGIN)){this.isStatusLogin = json.getBoolean(STATUSLOGIN);}

            if(json.has(RESPONSE)) {
                JSONArray array = json.getJSONArray(RESPONSE);
                SeeAllCategoryListModel listModelLocal = new SeeAllCategoryListModel();
                if(listModelLocal.toObject(array)){this.seeAllCategoryListModel = listModelLocal;}
                else{this.seeAllCategoryListModel = null;}
            }

            return true;
        }catch(Exception ex){
            Log.d(TAG, "Json Exception : " + ex);}
        return false;
    }

    @SuppressLint("LongLogTag")
    @Override
    public String toString(){
        String returnString = null;
        try{
            JSONObject jsonMain = new JSONObject();
            jsonMain.put(STATUS, isStatus);
            jsonMain.put(STATUSLOGIN, isStatusLogin);
            jsonMain.put(MESSAGE, message);
            jsonMain.put(RESPONSE, seeAllCategoryListModel!=null?new JSONArray(seeAllCategoryListModel.toString(true)):null);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}
