package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class ProfileMembershipModel implements Serializable {
    private final String TAG = "MembershipProfileModel";
    private final String
            ID              = "id",
            FULL_NAME       = "full_name",
            IMAGE            = "image",
            MEMBERSHIP_NAME  = "membership_name",
            MEMBERSHIP_ID    = "membership_id",
            VALID_FROM       = "valid_from",
            PRIORITY          = "priority",
            UPGRADE_TITLE     = "upgrade_title",
            UPGRADE_ID        = "upgrade_id",
            SUBSCRIBER_NAME   = "subscriber_name",
            SUBSCRIBER_IMAGE   = "subscriber_image",
            IS_PREMIUM        = "is_premium",
            VALID_TILL      = "valid_till";


    String
            id                   = null,
            full_name            = null,
            image                =null,
            membership_name      =null,
            membership_id        =null,
            valid_from           =null,
            is_premium           =null,
            priority            =null,
            upgrade_title      =null,
            subscriber_name      =null,
            subscriber_image      =null,
            upgrade_id          =null,
            valid_till          = null;



    public ProfileMembershipModel(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMembership_name() {
        return membership_name;
    }

    public void setMembership_name(String membership_name) {
        this.membership_name = membership_name;
    }

    public String getMembership_id() {
        return membership_id;
    }

    public void setMembership_id(String membership_id) {
        this.membership_id = membership_id;
    }

    public String getValid_from() {
        return valid_from;
    }

    public void setValid_from(String valid_from) {
        this.valid_from = valid_from;
    }

    public String getIs_premium() {
        return is_premium;
    }

    public void setIs_premium(String is_premium) {
        this.is_premium = is_premium;
    }

    public String getValid_till() {
        return valid_till;
    }

    public void setValid_till(String valid_till) {
        this.valid_till = valid_till;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getUpgrade_title() {
        return upgrade_title;
    }

    public void setUpgrade_title(String upgrade_title) {
        this.upgrade_title = upgrade_title;
    }

    public String getSubscriber_name() {
        return subscriber_name;
    }

    public void setSubscriber_name(String subscriber_name) {
        this.subscriber_name = subscriber_name;
    }

    public String getSubscriber_image() {
        return subscriber_image;
    }

    public void setSubscriber_image(String subscriber_image) {
        this.subscriber_image = subscriber_image;
    }

    public String getUpgrade_id() {
        return upgrade_id;
    }

    public void setUpgrade_id(String upgrade_id) {
        this.upgrade_id = upgrade_id;
    }

    public boolean toObject(String jsonObject){
        try{
            JSONObject json = new JSONObject(jsonObject);
            if(json.has(ID))id = json.getString(ID);
            if(json.has(FULL_NAME)) full_name = json.getString(FULL_NAME);
            if(json.has(IMAGE))image = json.getString(IMAGE);
            if(json.has(MEMBERSHIP_NAME))membership_name = json.getString(MEMBERSHIP_NAME);
            if(json.has(MEMBERSHIP_ID))membership_id = json.getString(MEMBERSHIP_ID);
            if(json.has(VALID_FROM))valid_from = json.getString(VALID_FROM);
            if(json.has(IS_PREMIUM))is_premium = json.getString(IS_PREMIUM);
            if(json.has(PRIORITY))priority = json.getString(PRIORITY);
            if(json.has(UPGRADE_TITLE))upgrade_title = json.getString(UPGRADE_TITLE);
            if(json.has(SUBSCRIBER_NAME))subscriber_name = json.getString(SUBSCRIBER_NAME);
            if(json.has(SUBSCRIBER_IMAGE))subscriber_image = json.getString(SUBSCRIBER_IMAGE);
            if(json.has(UPGRADE_ID))upgrade_id = json.getString(UPGRADE_ID);
            if(json.has(VALID_TILL))valid_till = json.getString(VALID_TILL);

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
            jsonMain.put(FULL_NAME, full_name);
            jsonMain.put(IMAGE, image);
            jsonMain.put(MEMBERSHIP_NAME, membership_name);
            jsonMain.put(MEMBERSHIP_ID, membership_id);
            jsonMain.put(VALID_FROM, valid_from);
            jsonMain.put(IS_PREMIUM, is_premium);
            jsonMain.put(PRIORITY, priority);
            jsonMain.put(UPGRADE_TITLE, upgrade_title);
            jsonMain.put(SUBSCRIBER_NAME, subscriber_name);
            jsonMain.put(SUBSCRIBER_IMAGE, subscriber_image);
            jsonMain.put(UPGRADE_ID, upgrade_id);
            jsonMain.put(VALID_TILL, valid_till);
            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}
