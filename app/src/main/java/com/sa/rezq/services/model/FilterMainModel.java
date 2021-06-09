package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class FilterMainModel implements Serializable {
    private final String TAG = "FilterMainModel";

    private final String
            CAT_ID                         = "category_id",
            CAT_NAME                       = "category_name",
            SUBCAT                         = "sub_category",
            PRICE                          = "price_range",
            BRAND                          = "brand";

    String
            catId                      = null,
            catName                    = null;


   SubCategoryListModel
            subCategoryList          = null,
            brandList                = null;

   FilterModel
            priceRangeModel  =null;

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public SubCategoryListModel getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(SubCategoryListModel subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

    public SubCategoryListModel getBrandList() {
        return brandList;
    }

    public void setBrandList(SubCategoryListModel brandList) {
        this.brandList = brandList;
    }

    public FilterModel getPriceRangeModel() {
        return priceRangeModel;
    }

    public void setPriceRangeModel(FilterModel priceRangeModel) {
        this.priceRangeModel = priceRangeModel;
    }

    public boolean toObject(String jsonObject){
        try{
            JSONObject json = new JSONObject(jsonObject);
            if(json.has(CAT_ID)){this.catId = json.getString(CAT_ID);}
            if(json.has(CAT_NAME)){this.catName = json.getString(CAT_NAME);}

            if(json.has(SUBCAT)) {
                JSONArray array = json.getJSONArray(SUBCAT);
                SubCategoryListModel listModelLocal = new SubCategoryListModel();
                if(listModelLocal.toObject(array)){this.subCategoryList = listModelLocal;}
                else{this.subCategoryList = null;}
            }

            if(json.has(BRAND)) {
                JSONArray array = json.getJSONArray(BRAND);
                SubCategoryListModel listModelLocal = new SubCategoryListModel();
                if(listModelLocal.toObject(array)){this.brandList = listModelLocal;}
                else{this.brandList = null;}
            }

            if(json.has(PRICE)) {
               FilterModel tripModel = new FilterModel();
                JSONObject tripJSON = json.getJSONObject(PRICE);
                if(tripModel.toObject(tripJSON.toString())) {
                    priceRangeModel = tripModel;
                }
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
            jsonMain.put(CAT_ID, this.catId);
            jsonMain.put(CAT_NAME, this.catName);
            jsonMain.put(SUBCAT, subCategoryList!=null?new JSONArray(subCategoryList.toString(true)):null);
            jsonMain.put(BRAND, brandList!=null?new JSONArray(brandList.toString(true)):null);
            jsonMain.put(PRICE, priceRangeModel!=null ? new JSONObject(priceRangeModel.toString()) : new JSONObject());


            returnString = jsonMain.toString();
        }
        catch (Exception ex){Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}
