package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VendorStoreListModel implements Serializable {

    private final String TAG = "VendorStoreListModel";

    private final String
            ID               = "store_id",
            NAME               = "name",
            LOGO               = "logo",
            ADDRESS            = "address",
            IMAGE                = "image",
            DISTANCE            = "distance";


    String
            id              = null,
            name           = null,
            logo            = null,
            address          = null,
             distance        = null,
            image            = null;


    public VendorStoreListModel() {
    }

    List<VendorStoreListModel> vendorStoreListModels = new ArrayList<VendorStoreListModel>();

    public List<VendorStoreListModel> getVendorStoreListModels() {
        return vendorStoreListModels;
    }

    public void setVendorStoreListModels(List<VendorStoreListModel> vendorStoreListModels) {
        this.vendorStoreListModels = vendorStoreListModels;
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


    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
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
            jsonMain.put(DISTANCE, distance);


            returnString = jsonMain.toString();
        } catch (Exception ex) {
            Log.d(TAG, " To String Exception : " + ex);
        }
        return returnString;
    }
}
