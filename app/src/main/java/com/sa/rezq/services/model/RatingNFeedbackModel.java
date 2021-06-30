package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class RatingNFeedbackModel implements Serializable {

    private final String TAG = "RatingNFeedbackModel";

    private final String
                    STORE_ID                   = "store_id",
                    RATING                      ="rating",
                    COMMENT                      ="comment";


    private String
            store_id                  = null,
            rating                    =null,
            comment                    =null;



    List<RatingNFeedbackModel> ratingNFeedbackModels = new ArrayList<RatingNFeedbackModel>();


    public RatingNFeedbackModel(){}


    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<RatingNFeedbackModel> getRatingNFeedbackModels() {
        return ratingNFeedbackModels;
    }

    public void setRatingNFeedbackModels(List<RatingNFeedbackModel> ratingNFeedbackModels) {
        this.ratingNFeedbackModels = ratingNFeedbackModels;
    }

    public boolean toObject(String jsonObject){
        try{
            JSONObject json = new JSONObject(jsonObject);

            if (json.has(STORE_ID)) {
                store_id= json.getString(STORE_ID);
            }
            if (json.has(RATING)) {
                rating = json.getString(RATING);

            }if (json.has(COMMENT)) {
                comment = json.getString(COMMENT);
            }


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
            //jsonMain.put(ID, this.id);
            jsonMain.put(STORE_ID, store_id);
            jsonMain.put(RATING,rating);
            jsonMain.put(COMMENT, comment);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}
