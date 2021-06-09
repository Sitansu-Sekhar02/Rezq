package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class LocationUpdateModel implements Serializable {

    private final String TAG = "LocationUpdateModel";
    private final String
            LATITUDE           = "latitude",
            LONGITUDE          = "longitude";

    String
            latitude        = null,
            longitude       = null;

    public LocationUpdateModel(){}

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public boolean toObject(String jsonObject){
        try{
            JSONObject json = new JSONObject(jsonObject);

            if(json.has(LATITUDE)){this.latitude = json.getString(LATITUDE);}
            if(json.has(LONGITUDE)){this.longitude = json.getString(LONGITUDE);}

            return true;
        }catch(Exception ex){Log.d(TAG, "Json Exception : " + ex);}
        return false;
    }

    @Override
    public String toString(){
        String returnString = null;
        try{
            JSONObject jsonMain = new JSONObject();
            jsonMain.put(LATITUDE, latitude);
            jsonMain.put(LONGITUDE, longitude);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}

