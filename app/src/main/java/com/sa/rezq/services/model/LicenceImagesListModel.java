package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LicenceImagesListModel implements Serializable {

    private final String TAG = "LicenceImagesListModel";

    private final String RESPONSE = "license_images";

    List<LicenceImageModel> licenceImageList = new ArrayList<LicenceImageModel>();

    public LicenceImagesListModel(){}

    public List<LicenceImageModel> getLicenceImageList() {
        return licenceImageList;
    }

    public void setLicenceImageList(List<LicenceImageModel> licenceImageList) {
        this.licenceImageList = licenceImageList;
    }

    public List<String> getImages(){
        List<String> list = new ArrayList<String>();
        for(int i =0 ;i<this.licenceImageList.size(); i++){
            list.add(licenceImageList.get(i).getImage());
        }
        return list;
    }

    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);
            JSONArray array = json.getJSONArray(RESPONSE);
            List<LicenceImageModel> list = new ArrayList<LicenceImageModel>();
            for (int i=0;i<array.length();i++){
                JSONObject jsonObject = array.getJSONObject(i);
                LicenceImageModel model = new LicenceImageModel();
                model.toObject(jsonObject.toString());
                list.add(model);
            }
            this.licenceImageList = list;
            return true;
        }catch(Exception ex){
            Log.d(TAG, "Json Exception : " + ex);}
        return false;
    }

    public boolean toObject(JSONArray jsonArray){
        try{
            List<LicenceImageModel> list = new ArrayList<LicenceImageModel>();
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                LicenceImageModel model = new LicenceImageModel();
                model.toObject(jsonObject.toString());
                list.add(model);
            }
            this.licenceImageList = list;
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
            List<LicenceImageModel> list = this.licenceImageList;
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
            List<LicenceImageModel> list = this.licenceImageList;
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
