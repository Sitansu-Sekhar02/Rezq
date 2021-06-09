package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class TicketsDetailModel implements Serializable {
    private final String TAG = "TicketsDetailModel";

    private final String
            ID                        = "id",
            TICKET_NUMBER             = "ticket_number",
            DATE                      = "date",
            STATUS_TITLE              = "status_title",
            LAST_REPLY                = "last_rply",
            TICKET_CONVERSATION       = "ticket_conversation";

    private String
            id                      = null,
            ticketNumber            = null,
            date                    = null,
            statusTitle             = null,
            lastReply               = null;

    public TicketsDetailModel(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatusTitle() {
        return statusTitle;
    }

    public void setStatusTitle(String statusTitle) {
        this.statusTitle = statusTitle;
    }

    public String getLastReply() {
        return lastReply;
    }

    public void setLastReply(String lastReply) {
        this.lastReply = lastReply;
    }

    public boolean toObject(String jsonObject){
        try{

            JSONObject json = new JSONObject(jsonObject);

            if(json.has(ID)){this.id = json.getString(ID);}
            if(json.has(TICKET_NUMBER)){this.ticketNumber = json.getString(TICKET_NUMBER);}
            if(json.has(DATE)){this.date = json.getString(DATE);}
            if(json.has(STATUS_TITLE)){this.statusTitle = json.getString(STATUS_TITLE);}
            if(json.has(LAST_REPLY)){this.lastReply = json.getString(LAST_REPLY);}

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
            jsonMain.put(TICKET_NUMBER, ticketNumber);
            jsonMain.put(DATE, date);
            jsonMain.put(STATUS_TITLE, statusTitle);
            jsonMain.put(LAST_REPLY, lastReply);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}


