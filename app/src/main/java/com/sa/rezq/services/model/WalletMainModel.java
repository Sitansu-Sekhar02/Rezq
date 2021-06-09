package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class WalletMainModel implements Serializable {
    private final String TAG = "WalletMainModel";

    private final String
            STATUS                     = "status",
            RESPONSE                   = "response";

    private String
            status                    = null;

    WalletSubMainModel
            walletSubMainModel   = null;

    public WalletMainModel(){}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public WalletSubMainModel getWalletSubMainModel() {
        return walletSubMainModel;
    }

    public void setWalletSubMainModel(WalletSubMainModel walletSubMainModel) {
        this.walletSubMainModel = walletSubMainModel;
    }

    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);

            if(json.has(STATUS)){this.status = json.getString(STATUS);}

            if(json.has(RESPONSE)){
                WalletSubMainModel statusModel = new WalletSubMainModel();
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1 = json.getJSONObject(RESPONSE);
                if(jsonObject1 != null){statusModel.toObject(jsonObject1.toString());}
                walletSubMainModel = statusModel;
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
            jsonMain.put(RESPONSE, walletSubMainModel != null ? new JSONObject(this.walletSubMainModel.toString()) : new JSONObject());
            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}


