package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class CommentSubMainModel implements Serializable {
    private final String TAG = "CommentSubMainModel";

    private final String
            RESPONSE                   = "ratings";

    CommentsListModel
                commentsListModel = null;

    public CommentSubMainModel(){}

    public CommentsListModel getCommentsListModel() {
        return commentsListModel;
    }

    public void setCommentsListModel(CommentsListModel commentsListModel) {
        this.commentsListModel = commentsListModel;
    }

    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);

            if(json.has(RESPONSE)) {
                JSONArray array = json.getJSONArray(RESPONSE);
                CommentsListModel listModelLocal = new CommentsListModel();
                if(listModelLocal.toObject(array)){this.commentsListModel = listModelLocal;}
                else{this.commentsListModel = null;}
            }

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
            jsonMain.put(RESPONSE, commentsListModel!=null?new JSONArray(commentsListModel.toString(true)):null);
            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}


