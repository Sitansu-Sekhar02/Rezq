package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RegistrationAddressListModel implements Serializable {

    private final String TAG = "RegAddressListModel";

    private final String RESPONSE = "addresses";

    List<RegistrationAddressModel> addressList = new ArrayList<RegistrationAddressModel>();

    public RegistrationAddressListModel(){}

    public List<RegistrationAddressModel> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<RegistrationAddressModel> addressList) {
        this.addressList = addressList;
    }

    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);
            JSONArray array = json.getJSONArray(RESPONSE);
            List<RegistrationAddressModel> list = new ArrayList<RegistrationAddressModel>();
            for (int i=0;i<array.length();i++){
                JSONObject jsonObject = array.getJSONObject(i);
                RegistrationAddressModel model = new RegistrationAddressModel();
                model.toObject(jsonObject.toString());
                list.add(model);
            }
            this.addressList = list;
            return true;
        }catch(Exception ex){
            Log.d(TAG, "Json Exception : " + ex);}
        return false;
    }

    public boolean toObject(JSONArray jsonArray){
        try{
            List<RegistrationAddressModel> list = new ArrayList<RegistrationAddressModel>();
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                RegistrationAddressModel model = new RegistrationAddressModel();
                model.toObject(jsonObject.toString());
                list.add(model);
            }
            this.addressList = list;
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
            List<RegistrationAddressModel> list = this.addressList;
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
            List<RegistrationAddressModel> list = this.addressList;
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
