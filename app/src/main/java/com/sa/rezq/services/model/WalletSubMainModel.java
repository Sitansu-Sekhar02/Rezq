package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class WalletSubMainModel implements Serializable {
    private final String TAG = "WalletSubMainModel";

    private final String
            AMOUNT                     = "balance",
            RESPONSE                   = "history";

    private String
            amount                   = null;

    WalletListModel
            walletList             = null;

    public WalletSubMainModel(){}

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public WalletListModel getWalletList() {
        return walletList;
    }

    public void setWalletList(WalletListModel walletList) {
        this.walletList = walletList;
    }

    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);

            if(json.has(AMOUNT)){this.amount = json.getString(AMOUNT);}

            if(json.has(RESPONSE)) {
                JSONArray array = json.getJSONArray(RESPONSE);
                WalletListModel listModelLocal = new WalletListModel();
                if(listModelLocal.toObject(array)){this.walletList = listModelLocal;}
                else{this.walletList = null;}
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
            jsonMain.put(AMOUNT, amount);
            jsonMain.put(RESPONSE, walletList!=null?new JSONArray(walletList.toString(true)):null);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}


