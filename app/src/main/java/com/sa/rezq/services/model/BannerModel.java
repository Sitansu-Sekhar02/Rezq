package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class BannerModel implements Serializable {
    private final String TAG = "BannerModel";
    private final String
            ID          = "id",
            TITLE       = "title",
            VENDOR_ID   = "vendor_id",
            COUNT        = "count",
            MASTER_ID   = "master_id",
            IMAGE       = "image",
            URL         = "url",
            TYPE        = "type";

    private String
            id          = null,
            title       = null,
            image       = null,
            url         = null,
            vendor_id    = null,
            count       = null,

            type        = null;

    public BannerModel(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public boolean toObject(String jsonObject){
        try{

            JSONObject json = new JSONObject(jsonObject);
            if (json.has(ID)) id = json.getString(ID);
            if (json.has(TITLE)) title = json.getString(TITLE);
            if (json.has(IMAGE)) image = json.getString(IMAGE);
            if (json.has(VENDOR_ID)) vendor_id = json.getString(VENDOR_ID);
            if (json.has(COUNT)) count = json.getString(COUNT);
            if (json.has(URL)) url = json.getString(URL);
            if (json.has(TYPE)) type = json.getString(TYPE);

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
            jsonMain.put(TITLE, title);
            jsonMain.put(IMAGE, image);
            jsonMain.put(VENDOR_ID, vendor_id);
            jsonMain.put(COUNT, count);
            jsonMain.put(URL, url);
            jsonMain.put(TYPE, type);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}


