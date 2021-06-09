package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GalleryAllListModel implements Serializable {

    private final String TAG = "GalleryAllListModel";

    private final String RESPONSE = "all_gallery";

    List<GalleryAllModel> allGalleryList = new ArrayList<GalleryAllModel>();

    public GalleryAllListModel() {
    }

    public List<GalleryAllModel> getAllGalleryList() {
        return allGalleryList;
    }

    public void setAllGalleryList(List<GalleryAllModel> allGalleryList) {
        this.allGalleryList = allGalleryList;
    }

    public List<String> getImages() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < this.allGalleryList.size(); i++) {
            list.add(allGalleryList.get(i).getLink());
        }
        return list;
    }

    public boolean toObject(String jsonObjectString) {
        try {
            JSONObject json = new JSONObject(jsonObjectString);
            JSONArray array = json.getJSONArray(RESPONSE);
            List<GalleryAllModel> list = new ArrayList<GalleryAllModel>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                GalleryAllModel model = new GalleryAllModel();
                model.toObject(jsonObject.toString());
                list.add(model);
            }
            this.allGalleryList = list;
            return true;
        } catch (Exception ex) {
            Log.d(TAG, "Json Exception : " + ex);
        }
        return false;
    }

    public boolean toObject(JSONArray jsonArray) {
        try {
            List<GalleryAllModel> list = new ArrayList<GalleryAllModel>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                GalleryAllModel model = new GalleryAllModel();
                model.toObject(jsonObject.toString());
                list.add(model);
            }
            this.allGalleryList = list;
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
            List<GalleryAllModel> list = this.allGalleryList;
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
            List<GalleryAllModel> list = this.allGalleryList;
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
