package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class TicketSubmitModel implements Serializable {
    private final String TAG = "TicketSubmitModel";

    private final String
            TICKET_ID                   = "ticket_id",
            MESSAGE                     = "message",
            IMAGE                       = "image";

    private String
            ticketId                = null,
            message                 = null;

    ImageSubmitListModel imageListModel = null;

    public TicketSubmitModel(){}

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ImageSubmitListModel getImageListModel() {
        return imageListModel;
    }

    public void setImageListModel(ImageSubmitListModel imageListModel) {
        this.imageListModel = imageListModel;
    }

    public boolean toObject(String jsonObject){
        try{

            JSONObject json = new JSONObject(jsonObject);

            if(json.has(TICKET_ID)){this.ticketId = json.getString(TICKET_ID);}
            if(json.has(MESSAGE)){this.message = json.getString(MESSAGE);}

            if(json.has(IMAGE)) {
                JSONArray array = json.getJSONArray(IMAGE);
                ImageSubmitListModel listModelLocal = new ImageSubmitListModel();
                if(listModelLocal.toObject(array)){this.imageListModel = listModelLocal;}
                else{this.imageListModel = null;}
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
            jsonMain.put(TICKET_ID, ticketId);
            jsonMain.put(MESSAGE, message);
            jsonMain.put(IMAGE, imageListModel!=null?new JSONArray(imageListModel.toString(true)):null);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}


