package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class VendorStoreMainModel {
    private final String TAG = "VendorDetailsMainModel";

    private final String
            RESPONSE            = "response",
            STATUS              = "status",
            MESSAGE             = "message";

    VendorRTModel
          vendorModel=null;


    String message = null;
    boolean isStatus=false;

    public VendorStoreMainModel(){}

    public VendorRTModel getVendorModel() {
        return vendorModel;
    }

    public void setVendorModel(VendorRTModel vendorModel) {
        this.vendorModel = vendorModel;
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

            if(json.has(RESPONSE)) {
                JSONArray array = json.getJSONArray(RESPONSE);
                VendorRTModel listModelLocal = new VendorRTModel();
                if(listModelLocal.toObject(array)){this.vendorModel = listModelLocal;}
                else{this.vendorModel = null;}
            }

         /*   if(json.has(RESPONSE)){
                VendorRTModel statusModel = new VendorRTModel();
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1 = json.getJSONObject(RESPONSE);
                if(jsonObject1 != null){statusModel.toObject(jsonObject1.toString());}
                vendorModel = statusModel;
            }
*/
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

            jsonMain.put(RESPONSE, vendorModel!=null?new JSONArray(vendorModel.toString(true)):null);

         //   jsonMain.put(RESPONSE, vendorModel != null ? new JSONObject(this.vendorModel.toString()) : new JSONObject());

            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}


