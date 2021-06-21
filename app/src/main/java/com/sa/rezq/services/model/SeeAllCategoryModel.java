package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeeAllCategoryModel implements Serializable {

    private final String TAG = "SeeAllCategoryListModel";

    private final String
            CATEGORY_ID = "category_id",
            NAME = "name",
            ICON = "image",
            VENDOR_COUNT = "count",
            ACCOUNT = "account",
            SERVICES = "service";


    String
            categori_id = null,
            name = null,
            vendor_count = null,
            account = null,
            icon = null;

    ServicesListModel
            servicesListModel = null;

    List<SeeAllCategoryModel> categoryListModels = new ArrayList<SeeAllCategoryModel>();


    public SeeAllCategoryModel() {
    }

    public List<SeeAllCategoryModel> getCategoryListModels() {
        return categoryListModels;
    }

    public void setCategoryListModels(List<SeeAllCategoryModel> categoryListModels) {
        this.categoryListModels = categoryListModels;
    }

    public String getTAG() {
        return TAG;
    }

    public String getCategori_id() {
        return categori_id;
    }

    public void setCategori_id(String categori_id) {
        this.categori_id = categori_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVendor_count() {
        return vendor_count;
    }

    public void setVendor_count(String vendor_count) {
        this.vendor_count = vendor_count;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public ServicesListModel getServicesListModel() {
        return servicesListModel;
    }

    public void setServicesListModel(ServicesListModel servicesListModel) {
        this.servicesListModel = servicesListModel;
    }

    public int getImageResourceID() {
        /*if (this.id.equalsIgnoreCase(GlobalVariables.CATEGORY_VALUE_FOR_FACE)) {
            return R.drawable.face_bg;
        } else if (this.id.equalsIgnoreCase(GlobalVariables.CATEGORY_VALUE_FOR_HAIR)) {
            return R.drawable.hair_bg;
        } else if (this.id.equalsIgnoreCase(GlobalVariables.CATEGORY_VALUE_FOR_MASSAGE)) {
            return R.drawable.massage_bg;
        } else if (this.id.equalsIgnoreCase(GlobalVariables.CATEGORY_VALUE_FOR_NAIL)) {
            return R.drawable.nail_bg;
        } else if (this.id.equalsIgnoreCase(GlobalVariables.CATEGORY_VALUE_FOR_TAILOR)) {
            return R.drawable.tailor_bg;
        } else if (this.id.equalsIgnoreCase(GlobalVariables.CATEGORY_VALUE_FOR_FITNESS)) {
            return R.drawable.fitness_bg;
        } else if (this.id.equalsIgnoreCase(GlobalVariables.CATEGORY_VALUE_FOR_PACKAGING)) {
            return R.drawable.packaging_bg;
        }*/
        return 0;
    }

    public boolean toObject(String jsonObject) {
        try {
            JSONObject json = new JSONObject(jsonObject);
           // categori_id = json.getString(CATEGORY_ID);
            if (json.has(NAME)) name = json.getString(NAME);
            if (json.has(ICON)) icon = json.getString(ICON);
            if (json.has(CATEGORY_ID)) categori_id = json.getString(CATEGORY_ID);
            if (json.has(ACCOUNT)) account = json.getString(ACCOUNT);
            if (json.has(VENDOR_COUNT)) vendor_count = json.getString(VENDOR_COUNT);

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
            jsonMain.put(CATEGORY_ID, categori_id);
            jsonMain.put(NAME, name);
            jsonMain.put(ICON, icon);
           // jsonMain.put(CATEGORY_ID, categori_id);
            jsonMain.put(ACCOUNT, account);
            jsonMain.put(VENDOR_COUNT, vendor_count);

            //jsonMain.put(SERVICES, servicesListModel != null ? new JSONArray(servicesListModel.toString(true)) : new JSONArray());
            returnString = jsonMain.toString();
        } catch (Exception ex) {
            Log.d(TAG, " To String Exception : " + ex);
        }
        return returnString;
    }
}
