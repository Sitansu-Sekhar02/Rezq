package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

public class SubCatMainModel {
    private final String TAG = "SubCatMainModel";

    private final String
            MESSAGE                    = "status",
            STATUS                     = "status_bool",
            RESPONSE                   = "response";

    private String
            message                    = null;
    private boolean
            isStatus                  = false;

   SubCatSubModel
            subCatSubModel            = null;

    public SubCatMainModel(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return isStatus;
    }

    public void setStatus(boolean status) {
        isStatus = status;
    }

    public SubCatSubModel getSubCatSubModel() {
        return subCatSubModel;
    }

    public void setSubCatSubModel(SubCatSubModel subCatSubModel) {
        this.subCatSubModel = subCatSubModel;
    }

    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);
            if(json.has(MESSAGE)){this.message = json.getString(MESSAGE);}
            if(json.has(STATUS)){this.isStatus = json.getBoolean(STATUS);}
            if(json.has(RESPONSE)){
                SubCatSubModel statusModel = new SubCatSubModel();
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1 = json.getJSONObject(RESPONSE);
                if(jsonObject1 != null){statusModel.toObject(jsonObject1.toString());}
                subCatSubModel = statusModel;
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
            jsonMain.put(RESPONSE, subCatSubModel != null ? new JSONObject(this.subCatSubModel.toString()) : new JSONObject());

            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}


