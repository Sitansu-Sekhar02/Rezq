package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class InsertGalleryModel {
    private final String TAG = "InsertGalleryModel";

    private final String
            IMAGES           = "images",
            VIDEOS           = "videos";

    AddImageListModel
            imageList           = null;

  AddVideoListModel
            videoList           = null;

    public InsertGalleryModel(){}

    public AddImageListModel getImageList() {
        return imageList;
    }

    public void setImageList(AddImageListModel imageList) {
        this.imageList = imageList;
    }

    public AddVideoListModel getVideoList() {
        return videoList;
    }

    public void setVideoList(AddVideoListModel videoList) {
        this.videoList = videoList;
    }

    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);

            if(json.has(IMAGES)) {
                JSONArray array = json.getJSONArray(IMAGES);
                AddImageListModel listModelLocal = new AddImageListModel();
                if(listModelLocal.toObject(array)){this.imageList = listModelLocal;}
                else{this.imageList = null;}
            }

            if(json.has(VIDEOS)){
                JSONArray array = json.getJSONArray(VIDEOS);
               AddVideoListModel listModelLocal = new AddVideoListModel();
                if(listModelLocal.toObject(array)){this.videoList = listModelLocal;}
                else{this.videoList = null;}
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
            jsonMain.put(IMAGES, imageList!=null?new JSONArray(imageList.toString(true)):null);
            jsonMain.put(VIDEOS, videoList!=null?new JSONArray(videoList.toString(true)):null);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}


