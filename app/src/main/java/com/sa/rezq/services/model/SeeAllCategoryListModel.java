package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeeAllCategoryListModel implements Serializable {
    private final String TAG = "SeeAllCategoryListModel";

    private final String RESPONSE = "response";

    List<SeeAllCategoryModel> allCategoryList = new ArrayList<SeeAllCategoryModel>();

    public SeeAllCategoryListModel(){}


    public List<SeeAllCategoryModel> getAllCategoryList() {
        return allCategoryList;
    }

    public void setAllCategoryList(List<SeeAllCategoryModel> allCategoryList) {
        this.allCategoryList = allCategoryList;
    }

    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);
            JSONArray array = json.getJSONArray(RESPONSE);
            return this.toObject(array);
        }catch(Exception ex){
            Log.d(TAG, "Json Exception : " + ex);}
        return false;
    }

    public boolean toObject(JSONArray jsonArray){
        try{
            List<SeeAllCategoryModel> list = new ArrayList<SeeAllCategoryModel>();
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                SeeAllCategoryModel model = new SeeAllCategoryModel();
                model.toObject(jsonObject.toString());
                list.add(model);
            }
            this.allCategoryList = list;
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
            List<SeeAllCategoryModel> list = this.allCategoryList;
            for(int i=0;i<list.size();i++){
                jsonArray.put(new JSONObject(list.get(i).toString()));
            }
            jsonMain.put(RESPONSE, jsonArray);
            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

    public String toString(boolean isArray){
        String returnString = null;
        try{
            JSONObject jsonMain = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            List<SeeAllCategoryModel> list = this.allCategoryList;
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
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}


