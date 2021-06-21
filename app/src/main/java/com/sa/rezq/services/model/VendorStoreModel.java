package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VendorStoreModel implements Serializable {

    private final String TAG = "VendorStoreListModel";

    private final String
            ID               = "store_id",
            NAME               = "name",
            LOGO               = "logo",
            ADDRESS            = "address",
            IMAGE               = "image",
            OFFERS               = "offers",
            AVERAGE_RATING       = "avg_rating",
            RATING_COUNT         = "rating_count",
            FACILITIES         = "facilities",
            VENDOR_NAME         = "vendor_name",
            STORE_NAME         = "store_name",
            OFFER_TITLE         = "offer_title",
            OFFER_DISCOUNT         = "offer_discount",
            CREATED_ON         = "created_on",
            DISTANCE            = "distance";


    String
            id              = null,
            name            = null,
            logo            = null,
            address         = null,
             distance       = null,
            offers          = null,
            avg_rating       = null,
            rating_count     = null,
            facilities       = null,
            vendor_name       = null,
            store_name       = null,
            offer_title       = null,
            offer_discount       = null,
            created_on       = null,
            image            = null;


    public VendorStoreModel() {
    }

    List<VendorStoreModel> vendorStoreModels = new ArrayList<VendorStoreModel>();

    public List<VendorStoreModel> getVendorStoreModels() {
        return vendorStoreModels;
    }

    public void setVendorStoreModels(List<VendorStoreModel> vendorStoreModels) {
        this.vendorStoreModels = vendorStoreModels;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
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

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean toObject(String jsonObject) {
        try {
            JSONObject json = new JSONObject(jsonObject);
            if (json.has(ID)) id = json.getString(ID);
            if (json.has(IMAGE)) image = json.getString(IMAGE);
            if (json.has(NAME)) name = json.getString(NAME);
            if (json.has(LOGO)) logo = json.getString(LOGO);
            if (json.has(ADDRESS)) address = json.getString(ADDRESS);
            if (json.has(OFFERS)) offers = json.getString(OFFERS);
            if (json.has(AVERAGE_RATING)) avg_rating = json.getString(AVERAGE_RATING);
            if (json.has(RATING_COUNT)) rating_count = json.getString(RATING_COUNT);
            if (json.has(FACILITIES)) facilities = json.getString(FACILITIES);
            if (json.has(VENDOR_NAME)) vendor_name = json.getString(VENDOR_NAME);
            if (json.has(STORE_NAME)) store_name = json.getString(STORE_NAME);
            if (json.has(OFFER_DISCOUNT)) offer_discount = json.getString(OFFER_DISCOUNT);
            if (json.has(OFFER_TITLE)) offer_title = json.getString(OFFER_TITLE);
            if (json.has(CREATED_ON)) created_on = json.getString(CREATED_ON);
            if (json.has(DISTANCE)) distance = json.getString(DISTANCE);


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
            jsonMain.put(OFFERS, offers);
            jsonMain.put(AVERAGE_RATING, avg_rating);
            jsonMain.put(RATING_COUNT, rating_count);
            jsonMain.put(FACILITIES, facilities);
            jsonMain.put(VENDOR_NAME, vendor_name);
            jsonMain.put(STORE_NAME, store_name);
            jsonMain.put(OFFER_DISCOUNT, offer_discount);
            jsonMain.put(OFFER_TITLE, offer_title);
            jsonMain.put(CREATED_ON, created_on);
            jsonMain.put(DISTANCE, distance);


            returnString = jsonMain.toString();
        } catch (Exception ex) {
            Log.d(TAG, " To String Exception : " + ex);
        }
        return returnString;
    }
}
