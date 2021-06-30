package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class WishModel implements Serializable {
    private final String TAG = "WishModel";

    private final String
            ID               = "store_id",
            NAME             = "name",
            LOGO             = "logo",
            ADDRESS            = "address",
            OFFERS            = "offers",
            FACILITIES        = "facilities",
            AVG_RATING        = "avg_rating",
            RATING_COUNT       = "rating_count",
            CREATED_ON            = "created_on";


    String
            id               = null,
            name               = null,
            logo               = null,
            address         = null,
            offers    = null,
            facilities      = null,
            avg_rating           = null,
            rating_count           = null,
            created_on            = null;


    public WishModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOffers() {
        return offers;
    }

    public void setOffers(String offers) {
        this.offers = offers;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public String getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(String avg_rating) {
        this.avg_rating = avg_rating;
    }

    public String getRating_count() {
        return rating_count;
    }

    public void setRating_count(String rating_count) {
        this.rating_count = rating_count;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public boolean toObject(String jsonObject) {
        try {
            JSONObject json = new JSONObject(jsonObject);
            id = json.getString(ID);
            if (json.has(NAME)) name = json.getString(NAME);
            if (json.has(LOGO)) logo = json.getString(LOGO);
            if (json.has(ADDRESS)) address = json.getString(ADDRESS);
            if (json.has(OFFERS)) offers = json.getString(OFFERS);
            if (json.has(FACILITIES)) facilities = json.getString(FACILITIES);
            if (json.has(AVG_RATING)) avg_rating = json.getString(AVG_RATING);
            if (json.has(RATING_COUNT)) rating_count = json.getString(RATING_COUNT);
            if (json.has(CREATED_ON)) created_on = json.getString(CREATED_ON);


            return true;
        } catch (Exception ex) {
            Log.d(TAG, "Json Exception : " + ex);
        }
        return false;
    }

    @Override
    public String toString() {
        String returnString = null;
        try {
            JSONObject jsonMain = new JSONObject();
            jsonMain.put(ID, id);
            jsonMain.put(NAME, name);
            jsonMain.put(LOGO, logo);
            jsonMain.put(ADDRESS, address);
            jsonMain.put(OFFERS, offers);
            jsonMain.put(FACILITIES, facilities);
            jsonMain.put(AVG_RATING, avg_rating);
            jsonMain.put(RATING_COUNT, rating_count);
            jsonMain.put(CREATED_ON, created_on);


            returnString = jsonMain.toString();
        } catch (Exception ex) {
            Log.d(TAG, " To String Exception : " + ex);
        }
        return returnString;
    }
}


