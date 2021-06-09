package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpecialOffersListModel implements Serializable {

    private final String TAG = "OffersListModel";

    private  String RESPONSE = "section2";

    List<SpecialOfferModel> specialOffersList = new ArrayList<SpecialOfferModel>();

    public SpecialOffersListModel(){}

    public List<SpecialOfferModel> getSpecialOffersList() {
        return specialOffersList;
    }

    public void setSpecialOffersList(List<SpecialOfferModel> specialOffersList) {
        this.specialOffersList = specialOffersList;
    }

    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);
            JSONArray array = json.getJSONArray(RESPONSE);
            List<SpecialOfferModel> list = new ArrayList<SpecialOfferModel>();
            for (int i=0;i<array.length();i++){
                JSONObject jsonObject = array.getJSONObject(i);
               SpecialOfferModel keyValueModel = new SpecialOfferModel();
                keyValueModel.toObject(jsonObject.toString());
                list.add(keyValueModel);
            }
            this.specialOffersList = list;
            return true;
        }catch(Exception ex){
            Log.d(TAG, "Json Exception : " + ex);}
        return false;
    }

    public boolean toObject(JSONArray jsonArray){
        try{
            List<SpecialOfferModel> list = new ArrayList<SpecialOfferModel>();
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
              SpecialOfferModel keyValueModel = new SpecialOfferModel();
                keyValueModel.toObject(jsonObject.toString());
                list.add(keyValueModel);
            }
            this.specialOffersList = list;
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
            JSONArray jsonArray = new JSONArray();
            List<SpecialOfferModel> list = this.specialOffersList;
            for(int i=0;i<list.size();i++){
                jsonArray.put(new JSONObject(list.get(i).toString()));
            }
            jsonMain.put(RESPONSE, jsonArray);
            returnString = jsonMain.toString();
        }
        catch (Exception ex){Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

    public String toString(boolean isArray){
        String returnString = null;
        try{
            JSONObject jsonMain = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            List<SpecialOfferModel> list = this.specialOffersList;
            for(int i=0;i<list.size();i++){
                jsonArray.put(new JSONObject(list.get(i).toString()));
            }
            if(!isArray){
                jsonMain.put(RESPONSE, jsonArray);
                returnString = jsonMain.toString();
            }else{
                returnString = jsonArray.toString();
            }

        }
        catch (Exception ex){Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}
