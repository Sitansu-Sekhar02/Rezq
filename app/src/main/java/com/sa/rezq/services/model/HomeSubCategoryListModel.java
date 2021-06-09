package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeSubCategoryListModel {
    private final String TAG = "HomeCategoryListModel";

    private final String RESPONSE = "response";

    List<HomeSubCategoryModel> subCategoryList = new ArrayList<HomeSubCategoryModel>();

    public HomeSubCategoryListModel(){}

    public List<HomeSubCategoryModel> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<HomeSubCategoryModel> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

    public List<String> getNames(){
        List<String> list = new ArrayList<String>();
        for(int i =0 ;i<this.subCategoryList.size(); i++){
            list.add(subCategoryList.get(i).getName());
        }
        return list;
    }

    public List<String> getImages(){
        List<String> list = new ArrayList<String>();
        for(int i =0 ;i<this.subCategoryList.size(); i++){
            list.add(subCategoryList.get(i).getImage());
        }
        return list;
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
            List<HomeSubCategoryModel> list = new ArrayList<HomeSubCategoryModel>();
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
               HomeSubCategoryModel model = new HomeSubCategoryModel();
                model.toObject(jsonObject.toString());
                list.add(model);
            }
            this.subCategoryList = list;
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
            List<HomeSubCategoryModel> list = this.subCategoryList;
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
            List<HomeSubCategoryModel> list = this.subCategoryList;
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


