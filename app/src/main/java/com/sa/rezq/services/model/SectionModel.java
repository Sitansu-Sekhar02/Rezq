package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class SectionModel implements Serializable {
    private final String TAG = "SectionModel";
    private final String
            CATEGORY_TITLE       = "category_title",
            SELECTED             = "selected",
            DETAIL               = "detail";

    private String
            categoryTitle       = null;
    boolean
            selected = false;

    DetailSectionListModel
            detailSectionList = null;

    public SectionModel(){}

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public DetailSectionListModel getDetailSectionList() {
        return detailSectionList;
    }

    public void setDetailSectionList(DetailSectionListModel detailSectionList) {
        this.detailSectionList = detailSectionList;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean toObject(String jsonObject){
        try{

            JSONObject json = new JSONObject(jsonObject);
            if (json.has(CATEGORY_TITLE)) categoryTitle = json.getString(CATEGORY_TITLE);
            if(json.has(SELECTED))this.selected = json.getBoolean(SELECTED);

            if(json.has(DETAIL)) {
                JSONArray array = json.getJSONArray(DETAIL);
                DetailSectionListModel listModelLocal = new DetailSectionListModel();
                if(listModelLocal.toObject(array)){this.detailSectionList = listModelLocal;}
                else{this.detailSectionList = null;}
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
            jsonMain.put(CATEGORY_TITLE, categoryTitle);
            jsonMain.put(SELECTED, selected);
            jsonMain.put(DETAIL, detailSectionList!=null?new JSONArray(detailSectionList.toString(true)):null);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}


