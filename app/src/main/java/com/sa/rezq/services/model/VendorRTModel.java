package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VendorRTModel implements Serializable {

    private final String TAG = "VendorRTModel";

    private final String RESPONSE = "response";


    List<VendorStoreListModel> vendorListModels = new ArrayList<VendorStoreListModel>();

    public VendorRTModel(){}

    public List<VendorStoreListModel> getVendorListModels() {
        return vendorListModels;
    }

    public void setVendorListModels(List<VendorStoreListModel> vendorListModels) {
        this.vendorListModels = vendorListModels;
    }

    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);
            JSONArray array = json.getJSONArray(RESPONSE);
            List<VendorStoreListModel> list = new ArrayList<VendorStoreListModel>();
            for (int i=0;i<array.length();i++){
                JSONObject jsonObject = array.getJSONObject(i);
                VendorStoreListModel model = new VendorStoreListModel();
                model.toObject(jsonObject.toString());
                list.add(model);
            }
            this.vendorListModels = list;
            return true;
        }catch(Exception ex){
            Log.d(TAG, "Json Exception : " + ex);}
        return false;
    }

    public boolean toObject(JSONArray jsonArray){
        try{
            List<VendorStoreListModel> list = new ArrayList<VendorStoreListModel>();
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                VendorStoreListModel model = new VendorStoreListModel();
                model.toObject(jsonObject.toString());
                list.add(model);
            }
            this.vendorListModels = list;
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
            JSONArray jsonArray = new JSONArray();
            List<VendorStoreListModel> list = this.vendorListModels;
            for(int i=0;i<list.size();i++){
                jsonArray.put(new JSONObject(list.get(i).toString()));
            }
            jsonMain.put(RESPONSE, jsonArray);
            returnString = jsonMain.toString();
        }
        catch (Exception ex){Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

    public String toString(boolean isArray){
        String returnString = null;
        try{
            JSONObject jsonMain = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            List<VendorStoreListModel> list = this.vendorListModels;
            for(int i=0;i<list.size();i++){
                jsonArray.put(new JSONObject(list.get(i).toString()));
            }
            if(!isArray){
                jsonMain.put(RESPONSE, jsonArray);
                returnString = jsonMain.toString();
            }else{
                returnString = jsonArray.toString();
            }

        }
        catch (Exception ex){Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}
