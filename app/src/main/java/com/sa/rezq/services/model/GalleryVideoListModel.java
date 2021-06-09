package com.sa.rezq.services.model;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GalleryVideoListModel implements Serializable {

    private final String TAG = "GalleryVideoListModel";

    private  String RESPONSE = "gallery_video";

    List<GalleryVideoModel> galleryVideoList = new ArrayList<GalleryVideoModel>();

    public GalleryVideoListModel() {
    }

    public String getRESPONSE() {
        return RESPONSE;
    }

    public void setRESPONSE(String RESPONSE) {
        this.RESPONSE = RESPONSE;
    }

    public List<GalleryVideoModel> getGalleryVideoList() {
        return galleryVideoList;
    }

    public void setGalleryVideoList(List<GalleryVideoModel> galleryVideoList) {
        this.galleryVideoList = galleryVideoList;
    }

    public List<String> getVideo() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < this.galleryVideoList.size(); i++) {
            list.add(galleryVideoList.get(i).getLink());
        }
        return list;
    }
    public List<String> getVideoIds() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < this.galleryVideoList.size(); i++) {
            list.add(galleryVideoList.get(i).getId());
        }
        return list;
    }

    public List<Uri> getVideosUri() {
        List<Uri> list = new ArrayList<Uri>();
        for (int i = 0; i < this.galleryVideoList.size(); i++) {
            list.add(Uri.parse(galleryVideoList.get(i).getLink()));
        }
        return list;
    }
    public List<Uri> getVideosThumbnailUri() {
        List<Uri> list = new ArrayList<Uri>();
        for (int i = 0; i < this.galleryVideoList.size(); i++) {
            list.add(Uri.parse(galleryVideoList.get(i).getVideoThumbnail()));
        }
        return list;
    }

    public boolean toObject(String jsonObjectString) {
        try {
            JSONObject json = new JSONObject(jsonObjectString);
            JSONArray array = json.getJSONArray(RESPONSE);
            List<GalleryVideoModel> list = new ArrayList<GalleryVideoModel>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                GalleryVideoModel model = new GalleryVideoModel();
                model.toObject(jsonObject.toString());
                list.add(model);
            }
            this.galleryVideoList = list;
            return true;
        } catch (Exception ex) {
            Log.d(TAG, "Json Exception : " + ex);
        }
        return false;
    }

    public boolean toObject(JSONArray jsonArray) {
        try {
            List<GalleryVideoModel> list = new ArrayList<GalleryVideoModel>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                GalleryVideoModel model = new GalleryVideoModel();
                model.toObject(jsonObject.toString());
                list.add(model);
            }
            this.galleryVideoList = list;
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
            List<GalleryVideoModel> list = this.galleryVideoList;
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
            List<GalleryVideoModel> list = this.galleryVideoList;
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
