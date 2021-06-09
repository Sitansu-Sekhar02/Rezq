package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class CityListMainModel implements Serializable {
    private final String TAG = "CityListMainModel";

    private final String
            STATUS               = "status",
            RESPONSE             = "response";

    private String
            status            = null;

   MyCityListModel
            cityListModel             = null;

    public CityListMainModel(){}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MyCityListModel getCityListModel() {
        return cityListModel;
    }

    public void setCityListModel(MyCityListModel cityListModel) {
        this.cityListModel = cityListModel;
    }

    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);

            if(json.has(STATUS)){this.status = json.getString(STATUS);}

            if(json.has(RESPONSE)) {
                JSONArray array = json.getJSONArray(RESPONSE);
                MyCityListModel listModelLocal = new MyCityListModel();
                if(listModelLocal.toObject(array)){this.cityListModel = listModelLocal;}
                else{this.cityListModel = null;}
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
            jsonMain.put(RESPONSE, cityListModel!=null?new JSONArray(cityListModel.toString(true)):null);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}


