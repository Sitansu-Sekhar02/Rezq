package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

public class ProductDetailMainModel {
    private final String TAG = "ProductDetailMainModel";

    private final String
            MESSAGE                    = "status",
            STATUS                     = "status_bool",
            RESPONSE                   = "response";

    private String
            message                    = null;
    private boolean
            isStatus                  = false;

   ProductDetailModel
            productDetailModel            = null;

    public ProductDetailMainModel(){}

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

    public ProductDetailModel getProductDetailModel() {
        return productDetailModel;
    }

    public void setProductDetailModel(ProductDetailModel productDetailModel) {
        this.productDetailModel = productDetailModel;
    }

    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);
            if(json.has(MESSAGE)){this.message = json.getString(MESSAGE);}
            if(json.has(STATUS)){this.isStatus = json.getBoolean(STATUS);}
            if(json.has(RESPONSE)){
                ProductDetailModel statusModel = new ProductDetailModel();
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1 = json.getJSONObject(RESPONSE);
                if(jsonObject1 != null){statusModel.toObject(jsonObject1.toString());}
                productDetailModel = statusModel;
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
            jsonMain.put(RESPONSE, productDetailModel != null ? new JSONObject(this.productDetailModel.toString()) : new JSONObject());

            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}


