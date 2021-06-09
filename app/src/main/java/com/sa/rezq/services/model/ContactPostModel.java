package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class ContactPostModel implements Serializable {
    private final String TAG = "ContactPostModel";

    private final String
            SUBJECT             = "subject",
            TEXT                = "text";

    String
            subject             = null,
            text                = null;

    public ContactPostModel(){}

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean toObject(String jsonObject){
        try{
            JSONObject json = new JSONObject(jsonObject);
            if(json.has(SUBJECT)){this.subject = json.getString(SUBJECT);}
            if(json.has(TEXT)){this.text = json.getString(TEXT);}

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
            jsonMain.put(SUBJECT, this.subject);
            jsonMain.put(TEXT, this.text);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}
