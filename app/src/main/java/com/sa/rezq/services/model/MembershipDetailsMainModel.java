package com.sa.rezq.services.model;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class MembershipDetailsMainModel implements Serializable {
    private final String TAG = "MembershipDetailsMainModel";
    private final String
            RESPONSE            = "response",
            STATUS              = "status",
            MESSAGE             = "message";

    MembershipDetailsModel
            membershipDetailsModel      = null;


    String message = null;
    boolean isStatus=false;

    public MembershipDetailsMainModel(){}

    public MembershipDetailsModel getMembershipDetailsModel() {
        return membershipDetailsModel;
    }

    public void setMembershipDetailsModel(MembershipDetailsModel membershipDetailsModel) {
        this.membershipDetailsModel = membershipDetailsModel;
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
                MembershipDetailsModel accountListModel = new MembershipDetailsModel();
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1 = json.getJSONObject(RESPONSE);
                if(jsonObject1 != null){accountListModel.toObject(jsonObject1.toString());}
                membershipDetailsModel = accountListModel;
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
            jsonMain.put(RESPONSE, membershipDetailsModel != null ? new JSONObject(this.membershipDetailsModel.toString()) : new JSONObject());

           // jsonMain.put(RESPONSE, this.membershipDetailsModel != null? new JSONArray(this.membershipDetailsModel.toString(true)) : new JSONArray());

            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}
