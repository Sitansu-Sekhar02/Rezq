package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class TechAddressModel implements Serializable {
    private final String TAG = "TechAddressModel";
    private final String

            T_LATITUDE              = "tech_lat",
            T_LONGITUDE             = "tech_long";

    String

            tlatitude              = null,
            tlongitude             = null;


    public String getTlatitude() {
        return tlatitude;
    }

    public void setTlatitude(String tlatitude) {
        this.tlatitude = tlatitude;
    }

    public String getTlongitude() {
        return tlongitude;
    }

    public void setTlongitude(String tlongitude) {
        this.tlongitude = tlongitude;
    }

    public boolean toObject(String jsonObject){
        try{
            JSONObject json = new JSONObject(jsonObject);

            if(json.has(T_LATITUDE))this.tlatitude = json.getString(T_LATITUDE);
            if(json.has(T_LONGITUDE))this.tlongitude = json.getString(T_LONGITUDE);

            return true;
        }catch(Exception ex){
            Log.d(TAG, "Json Exception : " + ex);
            return false;}
    }

    @Override
    public String toString(){
        String returnString = null;
        try{
            JSONObject jsonMain = new JSONObject();
            jsonMain.put(T_LATITUDE, tlatitude);
            jsonMain.put(T_LONGITUDE, tlongitude);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}
