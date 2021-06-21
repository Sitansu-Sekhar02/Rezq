package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class InsertRecentCouponModel implements Serializable {

    private final String TAG = "InsertRecentCouponModel";

    private final String
                    ID                        = "id",
                    STORE_ID                  ="store_id",
                    OFFER_ID                 = "offers_id";



    private String
             id                  = null,
            store_id           =null,
            offers_id          =null;




    List<InsertRecentCouponModel> orderModelList = new ArrayList<InsertRecentCouponModel>();


    public InsertRecentCouponModel(){}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getOffers_id() {
        return offers_id;
    }

    public void setOffers_id(String offers_id) {
        this.offers_id = offers_id;
    }

    public List<InsertRecentCouponModel> getOrderModelList() {
        return orderModelList;
    }

    public void setOrderModelList(List<InsertRecentCouponModel> orderModelList) {
        this.orderModelList = orderModelList;
    }

    public boolean toObject(String jsonObject){
        try{
            JSONObject json = new JSONObject(jsonObject);

            if (json.has(ID)) {
                id= json.getString(ID);
            }
            if (json.has(STORE_ID)) {
                store_id = json.getString(STORE_ID);

            }if (json.has(OFFER_ID)) {
                offers_id = json.getString(OFFER_ID);
            }


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
            jsonMain.put(STORE_ID,store_id);

            jsonMain.put(OFFER_ID, offers_id);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}
