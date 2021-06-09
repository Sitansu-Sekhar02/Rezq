package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class LocationModel implements Serializable {
    private final String TAG = "LocationModel";

    private final String
            ID          = "id",
            STREET      = "street",
            ADDRESS     = "address",
            STATE       = "state",
            CITY        = "city",
            COUNTRY     = "country",
            PRIORITY     = "priority",
            IS_PREMIUM     = "is_premium",
            LATITUDE    = "latitude",
            LONGITUDE   = "longitude";

    String
            id          = null,
            street      = null,
            address     = null,
            state       = null,
            city        = null,
            priority        = null,
            is_premium        = null,
            country     = null;

    double
            latitude    = 0.0,
            longitude   = 0.0;

    public LocationModel(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getIs_premium() {
        return is_premium;
    }

    public void setIs_premium(String is_premium) {
        this.is_premium = is_premium;
    }

    public boolean toObject(String jsonObject){
        try{
            JSONObject json = new JSONObject(jsonObject);
            id = json.getString(ID);
            street = json.getString(STREET);
            address = json.getString(ADDRESS);
            state = json.getString(STATE);
            priority = json.getString(PRIORITY);
            is_premium = json.getString(IS_PREMIUM);

            try{latitude = json.getDouble(LATITUDE);}
            catch(Exception exx){latitude = 0.0;}

            try{longitude = json.getDouble(LONGITUDE);}
            catch (Exception exx){longitude = 0.0;}

            city = json.getString(CITY);
            country = json.getString(COUNTRY);
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
            jsonMain.put(STREET, street);
            jsonMain.put(ADDRESS, address);
            jsonMain.put(STATE, state);
            jsonMain.put(LATITUDE, latitude);
            jsonMain.put(LONGITUDE, longitude);
            jsonMain.put(CITY, city);
            jsonMain.put(COUNTRY, country);
            jsonMain.put(PRIORITY, priority);
            jsonMain.put(IS_PREMIUM, is_premium);
            returnString = jsonMain.toString();
        }
        catch (Exception ex){Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}
