package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class InsertPostModel implements Serializable {

    private final String TAG = "InsertPostModel";

    private final String
            ID                  = "id",
            TEXT                = "text",
            VIDEO               = "video",
            VIDEO_TYPE          = "video_type",
            LINK                = "link",
            TYPE                = "type",
            IMAGE               = "image",
            VIDEO_THUMBNAIL     = "video_thumbnail",
            IMAGES              = "images";

    String
            id              = null,
            text            = null,
            video           = null,
            videoType       = null,
            link            = null,
            image           = null,
            type            = null,
            videoThumbnail  = null;

   ImageListModel
            imagesList = null;

    public InsertPostModel() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public ImageListModel getImagesList() {
        return imagesList;
    }

    public void setImagesList(ImageListModel imagesList) {
        this.imagesList = imagesList;
    }

    public boolean toObject(String jsonObject) {
        try {
            JSONObject json = new JSONObject(jsonObject);
                if (json.has(ID)) id = json.getString(ID);
                if (json.has(TEXT)) text = json.getString(TEXT);
                if (json.has(VIDEO)) video = json.getString(VIDEO);
                if (json.has(VIDEO_TYPE)) videoType = json.getString(VIDEO_TYPE);
                if (json.has(LINK)) link = json.getString(LINK);
                if (json.has(IMAGE)) image = json.getString(IMAGE);
                if (json.has(TYPE)) type = json.getString(TYPE);
                if (json.has(VIDEO_THUMBNAIL)) videoThumbnail = json.getString(VIDEO_THUMBNAIL);

            if(json.has(IMAGES)) {
                JSONArray array = json.getJSONArray(IMAGES);
             ImageListModel listModelLocal = new ImageListModel();
                if(listModelLocal.toObject(array)){this.imagesList = listModelLocal;}
                else{this.imagesList = null;}
            }

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
            jsonMain.put(ID, id);
            jsonMain.put(TEXT, text);
            jsonMain.put(VIDEO, video);
            jsonMain.put(VIDEO_TYPE, videoType);
            jsonMain.put(LINK, link);
            jsonMain.put(IMAGE, image);
            jsonMain.put(TYPE, type);
            jsonMain.put(VIDEO_THUMBNAIL, videoThumbnail);
            jsonMain.put(IMAGES, imagesList!=null?new JSONArray(imagesList.toString(true)):null);

            returnString = jsonMain.toString();
        } catch (Exception ex) {
            Log.d(TAG, " To String Exception : " + ex);
        }
        return returnString;
    }
}
