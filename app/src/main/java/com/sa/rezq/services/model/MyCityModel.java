package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class MyCityModel implements Serializable {
    private final String TAG = "MyCityModel";

    private final String
            ID                  = "id",
            TITLE               = "title",
            NAME                = "name",
            EXTRA               = "extra";

    String
            id                  = null,
            title               = null,
            name                = null,
            extra               = null;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean toObject(String jsonObject){
        try{
            JSONObject json = new JSONObject(jsonObject);
            if(json.has(ID)){this.id = json.getString(ID);}
            if(json.has(TITLE)){this.title = json.getString(TITLE);}
            if(json.has(EXTRA)){this.extra = json.getString(EXTRA);}
            if(json.has(NAME)){this.name = json.getString(NAME);}

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
            jsonMain.put(ID, this.id);
            jsonMain.put(TITLE, this.title);
            jsonMain.put(EXTRA, this.extra);
            jsonMain.put(NAME, this.name);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}
