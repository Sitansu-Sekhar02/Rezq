package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class TicketModel implements Serializable {
    private final String TAG = "TicketModel";

    private final String
            ID                          = "id",
            TICKET_NUMBER               = "ticket_number",
            STATUS                      = "status",
            STATUS_TITLE                = "status_title",
            LAST_REPLY                  = "last_rply",
            DATE                        = "date";

    private String
            id                      = null,
            ticketNumber            = null,
            status                  = null,
            statusTitle             = null,
            lastReply               = null,
            date                    = null;

    public TicketModel(){}

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean toObject(String jsonObject){
        try{

            JSONObject json = new JSONObject(jsonObject);

            if(json.has(ID)){this.id = json.getString(ID);}
            if(json.has(TICKET_NUMBER)){this.ticketNumber = json.getString(TICKET_NUMBER);}
            if(json.has(STATUS)){this.status = json.getString(STATUS);}
            if(json.has(STATUS_TITLE)){this.statusTitle = json.getString(STATUS_TITLE);}
            if(json.has(LAST_REPLY)){this.lastReply = json.getString(LAST_REPLY);}
            if(json.has(DATE)){this.date = json.getString(DATE);}

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
            jsonMain.put(STATUS, status);
            jsonMain.put(STATUS_TITLE, statusTitle);
            jsonMain.put(LAST_REPLY, lastReply);
            jsonMain.put(DATE, date);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}


