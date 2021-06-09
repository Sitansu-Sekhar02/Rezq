package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GalleryListModel implements Serializable {

    private final String TAG = "VehicleListModel";

    private final String RESPONSE = "response";

    List<GalleryModel> galleryList = new ArrayList<GalleryModel>();

    public GalleryListModel(){}

    public List<GalleryModel> getGalleryList() {
        return galleryList;
    }

    public void setGalleryList(List<GalleryModel> galleryList) {
        this.galleryList = galleryList;
    }

    public List<String> getImages(){
        List<String> list = new ArrayList<String>();
        for(int i =0 ;i<this.galleryList.size(); i++){
            list.add(galleryList.get(i).getImage());
        }
        return list;
    }

    public List<String> getIDs(){
        List<String> list = new ArrayList<String>();
        for(int i =0 ;i<this.galleryList.size(); i++){
            list.add(galleryList.get(i).getId());
        }
        return list;
    }


    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);
            JSONArray array = json.getJSONArray(RESPONSE);
            List<GalleryModel> list = new ArrayList<GalleryModel>();
            for (int i=0;i<array.length();i++){
                JSONObject jsonObject = array.getJSONObject(i);
                GalleryModel model = new GalleryModel();
                model.toObject(jsonObject.toString());
                list.add(model);
            }
            this.galleryList = list;
            return true;
        }catch(Exception ex){
            Log.d(TAG, "Json Exception : " + ex);}
        return false;
    }

    public boolean toObject(JSONArray jsonArray){
        try{
            List<GalleryModel> list = new ArrayList<GalleryModel>();
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                GalleryModel model = new GalleryModel();
                model.toObject(jsonObject.toString());
                list.add(model);
            }
            this.galleryList = list;
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
            List<GalleryModel> list = this.galleryList;
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
            List<GalleryModel> list = this.galleryList;
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
