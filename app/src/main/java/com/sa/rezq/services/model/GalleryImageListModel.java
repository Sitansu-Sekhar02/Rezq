package com.sa.rezq.services.model;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GalleryImageListModel implements Serializable {

    private final String TAG = "GalleryImageListModel";

    private  String RESPONSE = "gallery_image";

    public String getRESPONSE() {
        return RESPONSE;
    }

    public void setRESPONSE(String RESPONSE) {
        this.RESPONSE = RESPONSE;
    }

    List<GalleryImageModel> galleryImageList = new ArrayList<GalleryImageModel>();

    public GalleryImageListModel() {
    }

    public List<GalleryImageModel> getGalleryImageList() {
        return galleryImageList;
    }

    public void setGalleryImageList(List<GalleryImageModel> galleryImageList) {
        this.galleryImageList = galleryImageList;
    }

    public List<String> getImages() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < this.galleryImageList.size(); i++) {
            list.add(galleryImageList.get(i).getLink());
        }
        return list;
    }

    public List<String> getImagesIds() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < this.galleryImageList.size(); i++) {
            list.add(galleryImageList.get(i).getId());
        }
        return list;
    }
    public List<Uri> getImagesUri() {
        List<Uri> list = new ArrayList<Uri>();
        for (int i = 0; i < this.galleryImageList.size(); i++) {
            list.add(Uri.parse(galleryImageList.get(i).getLink()));
        }
        return list;
    }


    public boolean toObject(String jsonObjectString) {
        try {
            JSONObject json = new JSONObject(jsonObjectString);
            JSONArray array = json.getJSONArray(RESPONSE);
            List<GalleryImageModel> list = new ArrayList<GalleryImageModel>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                GalleryImageModel model = new GalleryImageModel();
                model.toObject(jsonObject.toString());
                list.add(model);
            }
            this.galleryImageList = list;
            return true;
        } catch (Exception ex) {
            Log.d(TAG, "Json Exception : " + ex);
        }
        return false;
    }

    public boolean toObject(JSONArray jsonArray) {
        try {
            List<GalleryImageModel> list = new ArrayList<GalleryImageModel>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                GalleryImageModel model = new GalleryImageModel();
                model.toObject(jsonObject.toString());
                list.add(model);
            }
            this.galleryImageList = list;
            return true;
        } catch (Exception ex) {
            Log.d(TAG, "Json Exception : " + ex);
        }
        return false;
    }

    @Override
    public String toString() {
        String returnString = null;
        try {
            JSONObject jsonMain = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            List<GalleryImageModel> list = this.galleryImageList;
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(new JSONObject(list.get(i).toString()));
            }
            jsonMain.put(RESPONSE, jsonArray);
            returnString = jsonMain.toString();
        } catch (Exception ex) {
            Log.d(TAG, " To String Exception : " + ex);
        }
        return returnString;
    }

    public String toString(boolean isArray) {
        String returnString = null;
        try {
            JSONObject jsonMain = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            List<GalleryImageModel> list = this.galleryImageList;
            for (int i = 0; i < list.size(); i++) {
                jsonArray.put(new JSONObject(list.get(i).toString()));
            }
            if (!isArray) {
                jsonMain.put(RESPONSE, jsonArray);
                returnString = jsonMain.toString();
            } else {
                returnString = jsonArray.toString();
            }

        } catch (Exception ex) {
            Log.d(TAG, " To String Exception : " + ex);
        }
        return returnString;
    }
}
