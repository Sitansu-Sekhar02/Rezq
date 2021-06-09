package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RelatedProductsListModel implements Serializable {

    private final String TAG = "RelatedProductsList";

    private  String RESPONSE = "response";

    List<RelatedProductModel> relatedProductList = new ArrayList<RelatedProductModel>();

    public RelatedProductsListModel(){}

    public List<RelatedProductModel> getRelatedProductList() {
        return relatedProductList;
    }

    public void setRelatedProductList(List<RelatedProductModel> relatedProductList) {
        this.relatedProductList = relatedProductList;
    }

    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);
            JSONArray array = json.getJSONArray(RESPONSE);
            List<RelatedProductModel> list = new ArrayList<RelatedProductModel>();
            for (int i=0;i<array.length();i++){
                JSONObject jsonObject = array.getJSONObject(i);
              RelatedProductModel keyValueModel = new RelatedProductModel();
                keyValueModel.toObject(jsonObject.toString());
                list.add(keyValueModel);
            }
            this.relatedProductList = list;
            return true;
        }catch(Exception ex){
            Log.d(TAG, "Json Exception : " + ex);}
        return false;
    }

    public boolean toObject(JSONArray jsonArray){
        try{
            List<RelatedProductModel> list = new ArrayList<RelatedProductModel>();
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
               RelatedProductModel keyValueModel = new RelatedProductModel();
                keyValueModel.toObject(jsonObject.toString());
                list.add(keyValueModel);
            }
            this.relatedProductList = list;
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
            List<RelatedProductModel> list = this.relatedProductList;
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
            List<RelatedProductModel> list = this.relatedProductList;
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
