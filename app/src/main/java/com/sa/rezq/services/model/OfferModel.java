package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class OfferModel implements Serializable {
    private final String TAG = "OfferModel";
    private final String
            ID                       = "offer_store_id",
            TITLE                    = "title",
            ALLOW                    = "allow",
            MEMBERSHIP_TITLE          = "membership_title",
            OFFER_APPLICABLE          = "offer_applicable",
            OFFER_DISCOUNT           = "offer_discount",
            OFFER_IMAGE              = "offer_image";

    private String
            id          = null,
            title       = null,
            allow       = null,
            membership_title         = null,
            offer_applicable    = null,
            offer_image    = null,
            offer_discount        = null;

    public OfferModel(){}

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

    public String getAllow() {
        return allow;
    }

    public void setAllow(String allow) {
        this.allow = allow;
    }

    public String getMembership_title() {
        return membership_title;
    }

    public void setMembership_title(String membership_title) {
        this.membership_title = membership_title;
    }

    public String getOffer_applicable() {
        return offer_applicable;
    }

    public void setOffer_applicable(String offer_applicable) {
        this.offer_applicable = offer_applicable;
    }

    public String getOffer_image() {
        return offer_image;
    }

    public void setOffer_image(String offer_image) {
        this.offer_image = offer_image;
    }

    public String getOffer_discount() {
        return offer_discount;
    }

    public void setOffer_discount(String offer_discount) {
        this.offer_discount = offer_discount;
    }

    public boolean toObject(String jsonObject){
        try{

            JSONObject json = new JSONObject(jsonObject);
            if (json.has(ID)) id = json.getString(ID);
            if (json.has(TITLE)) title = json.getString(TITLE);
            if (json.has(ALLOW)) allow = json.getString(ALLOW);
            if (json.has(MEMBERSHIP_TITLE)) membership_title = json.getString(MEMBERSHIP_TITLE);
            if (json.has(OFFER_APPLICABLE)) offer_applicable = json.getString(OFFER_APPLICABLE);
            if (json.has(OFFER_DISCOUNT)) offer_discount = json.getString(OFFER_DISCOUNT);
            if (json.has(OFFER_IMAGE)) offer_image = json.getString(OFFER_IMAGE);

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
            jsonMain.put(ALLOW, allow);
            jsonMain.put(MEMBERSHIP_TITLE, membership_title);
            jsonMain.put(OFFER_APPLICABLE, offer_applicable);
            jsonMain.put(OFFER_DISCOUNT, offer_discount);
            jsonMain.put(OFFER_IMAGE, offer_image);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}


