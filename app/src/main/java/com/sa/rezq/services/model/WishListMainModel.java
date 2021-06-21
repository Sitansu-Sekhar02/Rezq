package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class WishListMainModel implements Serializable {
    private final String TAG = "WishListMainModel";

    private final String
            STATUS                     = "status",
            MESSAGE                     = "message",
            RESPONSE                    = "response";

    private String
            status                    = null,
            message                    = null;

    WishListSubMainModel
            wishListSubMainModel   = null;
    WishListModel
            wishListModel             = null;


    public WishListMainModel(){}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public WishListSubMainModel getWishListSubMainModel() {
        return wishListSubMainModel;
    }

    public void setWishListSubMainModel(WishListSubMainModel wishListSubMainModel) {
        this.wishListSubMainModel = wishListSubMainModel;
    }

    public WishListModel getWishListModel() {
        return wishListModel;
    }

    public void setWishListModel(WishListModel wishListModel) {
        this.wishListModel = wishListModel;
    }

    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);

            if(json.has(STATUS)){this.status = json.getString(STATUS);}
            if(json.has(MESSAGE)){this.message = json.getString(MESSAGE);}


            if(json.has(RESPONSE)){
                WishListSubMainModel statusModel = new WishListSubMainModel();
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1 = json.getJSONObject(RESPONSE);
                if(jsonObject1 != null){statusModel.toObject(jsonObject1.toString());}
                wishListSubMainModel = statusModel;

             /*   JSONArray array = json.getJSONArray(RESPONSE);
                WishListModel listModelLocal = new WishListModel();
                if(listModelLocal.toObject(array)){this.wishListModel = listModelLocal;}
                else{this.wishListModel = null;}*/
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
            jsonMain.put(MESSAGE, message);
           // jsonMain.put(RESPONSE, wishListModel!=null?new JSONArray(wishListModel.toString(true)):null);

            jsonMain.put(RESPONSE, wishListSubMainModel != null ? new JSONObject(this.wishListSubMainModel.toString()) : new JSONObject());
            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}


