package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class VendorModel implements Serializable {

    private final String TAG = "VendorModel";

    private final String
            ID               = "store_id",
            NAME               = "name",
            LOGO               = "logo",
            ADDRESS            = "address",
            AVERAGE_RATING      = "avg_rating",
            RATING_COUNT        = "rating_count",
            WISHLIST            = "wishlist",
            LATITUDE            = "latitude",
            LONGITUDE            = "longitude",
            IMAGE                = "image",
            OFFER_LIST            = "offer_list",
            DISTANCE            = "distance",
            REVIEW_LIST            = "review_list";


    String
            id               = null,
            name           = null,
            logo            = null,
            address            = null,
            avg_rating            = null,
            rating_count            = null,
            wishlist            = null,
            latitude            = null,
            longitude            = null,
            distance            = null,
            image            = null;

    OfferListModel
            offerListModel=null;
    ReviewListModel
             reviewListModel=null;

    public VendorModel() {
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

    public String getWishlist() {
        return wishlist;
    }

    public void setWishlist(String wishlist) {
        this.wishlist = wishlist;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public OfferListModel getOfferListModel() {
        return offerListModel;
    }

    public void setOfferListModel(OfferListModel offerListModel) {
        this.offerListModel = offerListModel;
    }

    public ReviewListModel getReviewListModel() {
        return reviewListModel;
    }

    public void setReviewListModel(ReviewListModel reviewListModel) {
        this.reviewListModel = reviewListModel;
    }

    public boolean toObject(String jsonObject) {
        try {
            JSONObject json = new JSONObject(jsonObject);
            if (json.has(ID)) id = json.getString(ID);
            if (json.has(IMAGE)) image = json.getString(IMAGE);
            if (json.has(NAME)) name = json.getString(NAME);
            if (json.has(LOGO)) logo = json.getString(LOGO);
            if (json.has(ADDRESS)) address = json.getString(ADDRESS);
            if (json.has(AVERAGE_RATING)) avg_rating = json.getString(AVERAGE_RATING);
            if (json.has(RATING_COUNT)) rating_count = json.getString(RATING_COUNT);
            if (json.has(LATITUDE)) latitude = json.getString(LATITUDE);
            if (json.has(LONGITUDE)) longitude = json.getString(LONGITUDE);
            if (json.has(WISHLIST)) wishlist = json.getString(WISHLIST);
            if (json.has(DISTANCE)) distance = json.getString(DISTANCE);


            if(json.has(OFFER_LIST)) {
                JSONArray array = json.getJSONArray(OFFER_LIST);
                OfferListModel listModelLocal = new OfferListModel();
                if(listModelLocal.toObject(array)){this.offerListModel = listModelLocal;}
                else{this.offerListModel = null;}
            }
            if(json.has(REVIEW_LIST)) {
                JSONArray array = json.getJSONArray(REVIEW_LIST);
                ReviewListModel reviewListModel = new ReviewListModel();
                if(reviewListModel.toObject(array)){this.reviewListModel = reviewListModel;}
                else{this.reviewListModel = null;}
            }

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
            jsonMain.put(IMAGE, image);
            jsonMain.put(NAME, name);
            jsonMain.put(LOGO, logo);
            jsonMain.put(ADDRESS, address);
            jsonMain.put(AVERAGE_RATING, avg_rating);
            jsonMain.put(RATING_COUNT, rating_count);
            jsonMain.put(LATITUDE, latitude);
            jsonMain.put(LONGITUDE, longitude);
            jsonMain.put(WISHLIST, wishlist);
            jsonMain.put(DISTANCE, distance);
            jsonMain.put(OFFER_LIST, offerListModel!=null?new JSONArray(offerListModel.toString(true)):null);
            jsonMain.put(REVIEW_LIST, reviewListModel!=null?new JSONArray(reviewListModel.toString(true)):null);


            returnString = jsonMain.toString();
        } catch (Exception ex) {
            Log.d(TAG, " To String Exception : " + ex);
        }
        return returnString;
    }
}
