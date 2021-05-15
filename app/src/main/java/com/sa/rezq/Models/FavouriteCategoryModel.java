package com.sa.rezq.Models;

public class FavouriteCategoryModel {

    private  String fav_id;
    private  String fav_title;

    public FavouriteCategoryModel(String food) {
    }

    public FavouriteCategoryModel(String fav_id, String fav_title) {
        this.fav_id = fav_id;
        this.fav_title = fav_title;
    }


    public String getFav_id() {
        return fav_id;
    }

    public void setFav_id(String fav_id) {
        this.fav_id = fav_id;
    }

    public String getFav_title() {
        return fav_title;
    }

    public void setFav_title(String fav_title) {
        this.fav_title = fav_title;
    }
}
