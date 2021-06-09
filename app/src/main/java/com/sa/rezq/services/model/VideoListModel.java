package com.sa.rezq.services.model;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VideoListModel implements Serializable {

    private final String TAG = "VideoListModel";

    private final String RESPONSE = "videos";

    List<MyVideoModel> videoList = new ArrayList<MyVideoModel>();

    public VideoListModel() {
    }

    public List<MyVideoModel> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<MyVideoModel> videoList) {
        this.videoList = videoList;
    }

    public List<String> getVideos() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < this.videoList.size(); i++) {
            list.add(videoList.get(i).getVideo());
        }
        return list;
    }

    public List<String> getVideoIds() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < this.videoList.size(); i++) {
            list.add(videoList.get(i).getId());
        }
        return list;
    }

    public List<Uri> getVideosUri() {
        List<Uri> list = new ArrayList<Uri>();
        for (int i = 0; i < this.videoList.size(); i++) {
            list.add(Uri.parse(videoList.get(i).getVideo()));
        }
        return list;
    }
    public List<Uri> getVideosThumbnailUri() {
        List<Uri> list = new ArrayList<Uri>();
        for (int i = 0; i < this.videoList.size(); i++) {
            list.add(Uri.parse(videoList.get(i).getVideoThumbnail()));
        }
        return list;
    }

    public boolean toObject(String jsonObjectString) {
        try {
            JSONObject json = new JSONObject(jsonObjectString);
            JSONArray array = json.getJSONArray(RESPONSE);
            List<MyVideoModel> list = new ArrayList<MyVideoModel>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
               MyVideoModel model = new MyVideoModel();
                model.toObject(jsonObject.toString());
                list.add(model);
            }
            this.videoList = list;
            return true;
        } catch (Exception ex) {
            Log.d(TAG, "Json Exception : " + ex);
        }
        return false;
    }

    public boolean toObject(JSONArray jsonArray) {
        try {
            List<MyVideoModel> list = new ArrayList<MyVideoModel>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                MyVideoModel model = new MyVideoModel();
                model.toObject(jsonObject.toString());
                list.add(model);
            }
            this.videoList = list;
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
            List<MyVideoModel> list = this.videoList;
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
            List<MyVideoModel> list = this.videoList;
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
