package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class CommentListMainModel implements Serializable {
    private final String TAG = "CommentListMainModel";

    private final String
            STATUS               = "status",
            RESPONSE             = "response";

    private String
            status            = null;

    CommentSubMainModel
                commentSubMainModel  = null;

    public CommentListMainModel(){}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CommentSubMainModel getCommentSubMainModel() {
        return commentSubMainModel;
    }

    public void setCommentSubMainModel(CommentSubMainModel commentSubMainModel) {
        this.commentSubMainModel = commentSubMainModel;
    }

    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);

            if(json.has(STATUS)){this.status = json.getString(STATUS);}

            if(json.has(RESPONSE)){
                CommentSubMainModel statusModel = new CommentSubMainModel();
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1 = json.getJSONObject(RESPONSE);
                if(jsonObject1 != null){statusModel.toObject(jsonObject1.toString());}
                commentSubMainModel = statusModel;
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
            jsonMain.put(STATUS, status);
            jsonMain.put(RESPONSE, commentSubMainModel != null ? new JSONObject(this.commentSubMainModel.toString()) : new JSONObject());

            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}


