package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;


public class GalleryVideoModel implements Serializable {
    private final String TAG = "GalleryVideoModel";
    private final String
            ID                 = "id",
            TYPE               = "type",
            VIDEO_THUMBNAIUL   = "video_thumbnail",
            LINK       = "link";

    private String
            id         = null,
            link       = null,
            type                   = null,
            videoThumbnail         = null;

    public GalleryVideoModel(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVideoThumbnail() {
        return videoThumbnail;
    }

    public void setVideoThumbnail(String videoThumbnail) {
        this.videoThumbnail = videoThumbnail;
    }

    public boolean toObject(String jsonObject){
        try{

            JSONObject json = new JSONObject(jsonObject);

            if (json.has(ID)) id = json.getString(ID);
            if (json.has(LINK)) link = json.getString(LINK);
            if (json.has(TYPE)) type = json.getString(TYPE);
            if (json.has(VIDEO_THUMBNAIUL)) videoThumbnail = json.getString(VIDEO_THUMBNAIUL);

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

            jsonMain.put(ID, id);
            jsonMain.put(LINK, link);
            jsonMain.put(TYPE, type);
            jsonMain.put(VIDEO_THUMBNAIUL, videoThumbnail);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}


