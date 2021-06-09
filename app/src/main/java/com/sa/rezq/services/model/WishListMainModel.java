package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class WishListMainModel implements Serializable {
    private final String TAG = "WishListMainModel";

    private final String
            STATUS                     = "status",
            RESPONSE                   = "response";

    private String
            status                    = null;

    WishListSubMainModel
            wishListSubMainModel   = null;

    public WishListMainModel(){}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public WishListSubMainModel getWishListSubMainModel() {
        return wishListSubMainModel;
    }

    public void setWishListSubMainModel(WishListSubMainModel wishListSubMainModel) {
        this.wishListSubMainModel = wishListSubMainModel;
    }

    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);

            if(json.has(STATUS)){this.status = json.getString(STATUS);}


            if(json.has(RESPONSE)){
                WishListSubMainModel statusModel = new WishListSubMainModel();
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1 = json.getJSONObject(RESPONSE);
                if(jsonObject1 != null){statusModel.toObject(jsonObject1.toString());}
                wishListSubMainModel = statusModel;
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
            jsonMain.put(STATUS, status);
            jsonMain.put(RESPONSE, wishListSubMainModel != null ? new JSONObject(this.wishListSubMainModel.toString()) : new JSONObject());
            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}


