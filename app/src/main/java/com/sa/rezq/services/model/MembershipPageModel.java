package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class MembershipPageModel {
    private final String TAG = "MembershipPageModel";

    private final String
            BANNERS                  = "response";


    MembershipListModel
            bannerList           = null;

    public MembershipPageModel(){}




    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);

            if(json.has(BANNERS)) {
                JSONArray array = json.getJSONArray(BANNERS);
                MembershipListModel listModelLocal = new MembershipListModel();
                if(listModelLocal.toObject(array)){this.bannerList = listModelLocal;}
                else{this.bannerList = null;}
            }



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
            jsonMain.put(BANNERS, bannerList!=null?new JSONArray(bannerList.toString(true)):null);


            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}


