package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class SearchResponseModel implements Serializable{
    private final String TAG = "SearchResponseModel";
    private final String
            ID                    = "id",
            TITLE                 = "title",
            VARIATION             = "id_variation",
            EXTRA                 = "type",
            CATEGORY_TITLE        = "category_title",
            CATEGORY_ID           = "category_id";

    String
            id                 = null,
            title              = null,
            variation          = null,
            extra              = null,
            categoryTitle      = null,
            categoryId         = null;

    public SearchResponseModel(){}

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

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }

    public boolean toObject(String jsonObject){
        try{
            JSONObject json = new JSONObject(jsonObject);
            if(json.has(ID))id = json.getString(ID);
            if(json.has(TITLE))title = json.getString(TITLE);
            if(json.has(EXTRA))extra = json.getString(EXTRA);
            if(json.has(VARIATION))variation = json.getString(VARIATION);
            if(json.has(CATEGORY_TITLE))categoryTitle = json.getString(CATEGORY_TITLE);
            if(json.has(CATEGORY_ID))categoryId = json.getString(CATEGORY_ID);

            return true;
        }catch(Exception ex){
            Log.d(TAG, "Json Exception : " + ex);
            return false;}
    }

    @Override
    public String toString(){
        String returnString = null;
        try{
            JSONObject jsonMain = new JSONObject();
            jsonMain.put(ID, this.getId());
            jsonMain.put(TITLE, this.getTitle());
            jsonMain.put(EXTRA, this.getExtra());
            jsonMain.put(VARIATION, this.getVariation());
            jsonMain.put(CATEGORY_TITLE, this.getCategoryTitle());
            jsonMain.put(CATEGORY_ID, this.getCategoryId());

            returnString = jsonMain.toString();
        }
        catch (Exception ex){Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}
