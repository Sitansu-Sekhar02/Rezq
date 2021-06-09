package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class DeepLinkModel implements Serializable {
    private final String TAG = "DeepLinkModel";
    private final String
            ID              = "id",
            CATEGORY_ID     = "category_id";

    private String
            id           = null,
            categoryId   = null;

    public DeepLinkModel(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public boolean toObject(String jsonObject){
        try{

            JSONObject json = new JSONObject(jsonObject);

            if (json.has(ID)) id = json.getString(ID);
            if (json.has(CATEGORY_ID)) categoryId = json.getString(CATEGORY_ID);

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
            jsonMain.put(CATEGORY_ID, categoryId);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}


