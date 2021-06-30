package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class HomePageModel {
    private final String TAG = "HomePageModel";

    private final String
            BANNERS                  = "banner",
            CATEGORY                 = "category",
            SUB_CATEGORY             = "sub_category",
            TRENDING                 = "trending",
            NEAR_BY                  = "near_by",
            LOCATION                 = "location",
            TOP_CATEGORY             = "top_category",
            SECTION1                 = "section1",
            SECTION2                 = "section2";

    BannerListModel
            bannerList            = null;
    NearbyListModel
            nearbyListModel       =null;
    TrendingListModel
            trendingListModel     =null;

    ProfileMembershipModel
            profileMembershipModel     =new ProfileMembershipModel();


    CategoryListModel
            categoryList           = null,
            subCategoryList        = null;

    SubCategoryListModel
            topCategory            = null,
            section1               = null,
            section2               = null;

    public HomePageModel(){}

    public BannerListModel getBannerList() {
        return bannerList;
    }

    public void setBannerList(BannerListModel bannerList) {
        this.bannerList = bannerList;
    }

    public CategoryListModel getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(CategoryListModel categoryList) {
        this.categoryList = categoryList;
    }

    public CategoryListModel getSubCategoryList() {
        return subCategoryList;
    }

    public NearbyListModel getNearbyListModel() {
        return nearbyListModel;
    }

    public void setNearbyListModel(NearbyListModel nearbyListModel) {
        this.nearbyListModel = nearbyListModel;
    }

    public ProfileMembershipModel getProfileMembershipModel() {
        return profileMembershipModel;
    }

    public void setProfileMembershipModel(ProfileMembershipModel profileMembershipModel) {
        this.profileMembershipModel = profileMembershipModel;
    }

    public TrendingListModel getTrendingListModel() {
        return trendingListModel;
    }

    public void setTrendingListModel(TrendingListModel trendingListModel) {
        this.trendingListModel = trendingListModel;
    }

    public void setSubCategoryList(CategoryListModel subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

    public SubCategoryListModel getSection1() {
        return section1;
    }

    public void setSection1(SubCategoryListModel section1) {
        this.section1 = section1;
    }

    public SubCategoryListModel getSection2() {
        return section2;
    }

    public void setSection2(SubCategoryListModel section2) {
        this.section2 = section2;
    }

    public SubCategoryListModel getTopCategory() {
        return topCategory;
    }

    public void setTopCategory(SubCategoryListModel topCategory) {
        this.topCategory = topCategory;
    }



    public boolean toObject(String jsonObjectString){
        try{
            JSONObject json = new JSONObject(jsonObjectString);

            if(json.has(BANNERS)) {
                JSONArray array = json.getJSONArray(BANNERS);
                BannerListModel listModelLocal = new BannerListModel();
                if(listModelLocal.toObject(array)){this.bannerList = listModelLocal;}
                else{this.bannerList = null;}
            }

            if(json.has(CATEGORY)) {
                JSONArray array = json.getJSONArray(CATEGORY);
                CategoryListModel listModelLocal = new CategoryListModel();
                listModelLocal.setRESPONSE(CATEGORY);
                if(listModelLocal.toObject(array)){this.categoryList = listModelLocal;}
                else{this.categoryList = null;}
            } if(json.has(TRENDING)) {
                JSONArray array = json.getJSONArray(TRENDING);
                TrendingListModel listModelLocal = new TrendingListModel();
                listModelLocal.setRESPONSE(TRENDING);
                if(listModelLocal.toObject(array)){this.trendingListModel = listModelLocal;}
                else{this.trendingListModel = null;}
            }if(json.has(NEAR_BY)) {
                JSONArray array = json.getJSONArray(NEAR_BY);
                NearbyListModel listModelLocal = new NearbyListModel();
                listModelLocal.setRESPONSE(NEAR_BY);
                if(listModelLocal.toObject(array)){this.nearbyListModel = listModelLocal;}
                else{this.nearbyListModel = null;}
            }

            if(json.has(LOCATION)){
                ProfileMembershipModel statusModel = new ProfileMembershipModel();
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1 = json.getJSONObject(LOCATION);
                if(jsonObject1 != null){statusModel.toObject(jsonObject1.toString());}
                profileMembershipModel = statusModel;

            }

            if(json.has(SUB_CATEGORY)) {
                JSONArray array = json.getJSONArray(SUB_CATEGORY);
                CategoryListModel listModelLocal = new CategoryListModel();
                listModelLocal.setRESPONSE(SUB_CATEGORY);
                if(listModelLocal.toObject(array)){this.subCategoryList = listModelLocal;}
                else{this.subCategoryList = null;}
            }

            if(json.has(SECTION1)) {
                JSONArray array = json.getJSONArray(SECTION1);
                SubCategoryListModel listModelLocal = new SubCategoryListModel();
                listModelLocal.setRESPONSE(SECTION1);
                if(listModelLocal.toObject(array)){this.section1 = listModelLocal;}
                else{this.section1 = null;}
            }

            if(json.has(SECTION2)) {
                JSONArray array = json.getJSONArray(SECTION2);
                SubCategoryListModel listModelLocal = new SubCategoryListModel();
                listModelLocal.setRESPONSE(SECTION2);
                if(listModelLocal.toObject(array)){this.section2 = listModelLocal;}
                else{this.section2 = null;}
            }

            if(json.has(TOP_CATEGORY)) {
                JSONArray array = json.getJSONArray(TOP_CATEGORY);
                SubCategoryListModel listModelLocal = new SubCategoryListModel();
                listModelLocal.setRESPONSE(TOP_CATEGORY);
                if(listModelLocal.toObject(array)){this.topCategory = listModelLocal;}
                else{this.topCategory = null;}
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
            jsonMain.put(CATEGORY, categoryList!=null?new JSONArray(categoryList.toString(true)):null);
            jsonMain.put(TRENDING, trendingListModel!=null?new JSONArray(trendingListModel.toString(true)):null);
            jsonMain.put(LOCATION, profileMembershipModel != null ? new JSONObject(profileMembershipModel.toString()) : new JSONObject());
            //jsonMain.put(PRICE, priceRangeModel!=null ? new JSONObject(priceRangeModel.toString()) : new JSONObject());

            jsonMain.put(SUB_CATEGORY, subCategoryList!=null?new JSONArray(subCategoryList.toString(true)):null);
            jsonMain.put(SECTION1, section1!=null?new JSONArray(section1.toString(true)):null);
            jsonMain.put(SECTION2, section2!=null?new JSONArray(section2.toString(true)):null);
            jsonMain.put(TOP_CATEGORY, topCategory!=null?new JSONArray(topCategory.toString(true)):null);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);
        }
        return returnString;
    }

}


