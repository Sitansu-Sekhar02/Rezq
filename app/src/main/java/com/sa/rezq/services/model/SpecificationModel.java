package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class SpecificationModel implements Serializable {
    private final String TAG = "SpecificationModel";
    private final String
            ID          = "specification_id",
            TITLE       = "name",
            VARIATION   = "variation",
            SPEC_VAL_ID = "specification_value_id",
            VALUE       = "value",
            IMAGE       = "extra";

    private String
            id          = null,
            title       = null,
            variation   = null,
            specValId   = null,
            value       = null,
            image       = null;

    public SpecificationModel(){}


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

    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }

    public String getSpecValId() {
        return specValId;
    }

    public void setSpecValId(String specValId) {
        this.specValId = specValId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean toObject(String jsonObject){
        try{

            JSONObject json = new JSONObject(jsonObject);
            if (json.has(ID)) id = json.getString(ID);
            if (json.has(TITLE)) title = json.getString(TITLE);
            if (json.has(IMAGE)) image = json.getString(IMAGE);
            if (json.has(VARIATION)) variation = json.getString(VARIATION);
            if (json.has(SPEC_VAL_ID)) specValId = json.getString(SPEC_VAL_ID);
            if (json.has(VALUE)) value = json.getString(VALUE);

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
            jsonMain.put(VARIATION, variation);
            jsonMain.put(SPEC_VAL_ID, specValId);
            jsonMain.put(VALUE, value);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}


