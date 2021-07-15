package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class RecentCouponModel implements Serializable {

    private final String TAG = "RecentCouponModel";

    private final String
                    ID                        = "store_id",
                    MEMBERSHIP_ID             ="membership_id",
                    FIRST_NAME                 = "first_name",
                    LAST_NAME                   = "last_name",
                    VENDOR_NAME                = "vendor_name",
                    STORE_NAME                 =  "store_name",
                    AVERAGE_RATING              =  "avg_rating",
                    OFFER_TITLE               = "offer_title",
                    OFFER_DISCOUNT             = "offer_discount",
                    CREATED_ON                = "created_on",
                    LOGO                       = "logo",
                    OFFER_ID                    = "offer_id",
                    PROFILE_IMAGE             = "profile_image";


    private String
             id                  = null,
            membership_id       =null,
            first_name          =null,
            last_name           = null,
            vendor_name       = null,
            store_name       = null,
            logo            = null,
            offer_title       = null,
            avg_rating       = null,
            offer_discount       = null,
            created_on       = null,
            offer_id         = null,
            profile_image       = null;



    List<RecentCouponModel> orderModelList = new ArrayList<RecentCouponModel>();


    public RecentCouponModel(){}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMembership_id() {
        return membership_id;
    }

    public void setMembership_id(String membership_id) {
        this.membership_id = membership_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public String getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(String avg_rating) {
        this.avg_rating = avg_rating;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getOffer_title() {
        return offer_title;
    }

    public void setOffer_title(String offer_title) {
        this.offer_title = offer_title;
    }

    public String getOffer_discount() {
        return offer_discount;
    }

    public void setOffer_discount(String offer_discount) {
        this.offer_discount = offer_discount;
    }

    public String getCreated_on() {
        return created_on;
    }
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public List<RecentCouponModel> getOrderModelList() {
        return orderModelList;
    }

    public void setOrderModelList(List<RecentCouponModel> orderModelList) {
        this.orderModelList = orderModelList;
    }

    public boolean toObject(String jsonObject){
        try{
            JSONObject json = new JSONObject(jsonObject);

            if (json.has(ID)) {
                id= json.getString(ID);
            }
            if (json.has(MEMBERSHIP_ID)) {
                membership_id = json.getString(MEMBERSHIP_ID);

            }if (json.has(FIRST_NAME)) {
                first_name = json.getString(FIRST_NAME);
            }if (json.has(LAST_NAME)) {
                last_name = json.getString(LAST_NAME);
            }if (json.has(PROFILE_IMAGE)) {
                profile_image = json.getString(PROFILE_IMAGE);
            }
            if (json.has(LOGO)) logo = json.getString(LOGO);

            if (json.has(VENDOR_NAME)) vendor_name = json.getString(VENDOR_NAME);
            if (json.has(STORE_NAME)) store_name = json.getString(STORE_NAME);
            if (json.has(OFFER_DISCOUNT)) offer_discount = json.getString(OFFER_DISCOUNT);
            if (json.has(OFFER_TITLE)) offer_title = json.getString(OFFER_TITLE);
            if (json.has(AVERAGE_RATING)) avg_rating = json.getString(AVERAGE_RATING);
            if (json.has(CREATED_ON)) created_on = json.getString(CREATED_ON);
            if (json.has(OFFER_ID)) offer_id = json.getString(OFFER_ID);


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
            jsonMain.put(ID, id);
            jsonMain.put(MEMBERSHIP_ID,membership_id);
            jsonMain.put(LOGO, logo);

            jsonMain.put(FIRST_NAME, first_name);
            jsonMain.put(LAST_NAME, last_name);
            jsonMain.put(PROFILE_IMAGE, profile_image);
            jsonMain.put(VENDOR_NAME, vendor_name);
            jsonMain.put(STORE_NAME, store_name);
            jsonMain.put(OFFER_DISCOUNT, offer_discount);
            jsonMain.put(AVERAGE_RATING, avg_rating);
            jsonMain.put(OFFER_TITLE, offer_title);
            jsonMain.put(CREATED_ON, created_on);
            jsonMain.put(OFFER_ID, offer_id);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}
