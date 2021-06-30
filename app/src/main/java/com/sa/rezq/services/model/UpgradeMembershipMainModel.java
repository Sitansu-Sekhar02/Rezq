package com.sa.rezq.services.model;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class UpgradeMembershipMainModel implements Serializable {
    private final String TAG = "UpgradeMembershipMainModel";
    private final String
            RESPONSE            = "response",
            STATUS              = "status",
            MESSAGE             = "message";

    UpgradeMembershipModel
            upgradeMembershipModel      = null;


    String message = null;
    boolean isStatus=false;

    public UpgradeMembershipMainModel(){}

    public UpgradeMembershipModel getUpgradeMembershipModel() {
        return upgradeMembershipModel;
    }

    public void setUpgradeMembershipModel(UpgradeMembershipModel upgradeMembershipModel) {
        this.upgradeMembershipModel = upgradeMembershipModel;
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

    @SuppressLint("LongLogTag")
    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);
            if(json.has(MESSAGE)){this.message = json.getString(MESSAGE);}
            if(json.has(STATUS)){this.isStatus = json.getBoolean(STATUS);}


            if(json.has(RESPONSE)){
                UpgradeMembershipModel membershipModel1 = new UpgradeMembershipModel();
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1 = json.getJSONObject(RESPONSE);
                if(jsonObject1 != null){membershipModel1.toObject(jsonObject1.toString());}
                upgradeMembershipModel = membershipModel1;
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
            jsonMain.put(MESSAGE, message);
            jsonMain.put(RESPONSE, upgradeMembershipModel != null ? new JSONObject(this.upgradeMembershipModel.toString()) : new JSONObject());

            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}
