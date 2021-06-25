package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class MembershipDetailsModel implements Serializable {
    private final String TAG = "MembershipDetailsModel";
    private final String
            ID                     = "id",
            MEMBERSHIP_NAME       = "membership_name",
            VALIDITY              = "validity",
            VALID_FROM            = "valid_from",
            VALID_TILL            = "valid_till",
            MEMBERSHIP_IMAGE      = "image",
            IS_PREMIUM            = "is_premium";


    String
            id                  = null,
            membership_name     = null,
            validity            =null,
            valid_from          =null,
            valid_till          =null,
            image               =null,
            is_premium          =null;



    public MembershipDetailsModel(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMembership_name() {
        return membership_name;
    }

    public void setMembership_name(String membership_name) {
        this.membership_name = membership_name;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getValid_from() {
        return valid_from;
    }

    public void setValid_from(String valid_from) {
        this.valid_from = valid_from;
    }

    public String getValid_till() {
        return valid_till;
    }

    public void setValid_till(String valid_till) {
        this.valid_till = valid_till;
    }

    public String getIs_premium() {
        return is_premium;
    }

    public void setIs_premium(String is_premium) {
        this.is_premium = is_premium;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean toObject(String jsonObject){
        try{
            JSONObject json = new JSONObject(jsonObject);
            if(json.has(ID))id = json.getString(ID);
            if(json.has(MEMBERSHIP_NAME)) membership_name = json.getString(MEMBERSHIP_NAME);
            if(json.has(VALIDITY))validity = json.getString(VALIDITY);
            if(json.has(VALID_FROM))valid_from = json.getString(VALID_FROM);
            if(json.has(VALID_TILL))valid_till = json.getString(VALID_TILL);
            if(json.has(IS_PREMIUM))is_premium = json.getString(IS_PREMIUM);
            if(json.has(MEMBERSHIP_IMAGE))image = json.getString(MEMBERSHIP_IMAGE);

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
            jsonMain.put(ID, id);
            jsonMain.put(MEMBERSHIP_NAME, membership_name);
            jsonMain.put(VALIDITY, validity);
            jsonMain.put(VALID_FROM, valid_from);
            jsonMain.put(VALID_TILL, valid_till);
            jsonMain.put(IS_PREMIUM, is_premium);
            jsonMain.put(MEMBERSHIP_NAME, image);
            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}
