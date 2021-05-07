package com.sa.rezq.Models;

public class PopularPlacesModelClass {
    private  String category_id;
    private  int category_image;
    private  String category_place_name;
    private  String category_percent_off;

    public PopularPlacesModelClass() {
    }

    public PopularPlacesModelClass(String category_id, int category_image, String category_place_name, String category_percent_off) {
        this.category_id = category_id;
        this.category_image = category_image;
        this.category_place_name = category_place_name;
        this.category_percent_off = category_percent_off;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public int getCategory_image() {
        return category_image;
    }

    public void setCategory_image(int category_image) {
        this.category_image = category_image;
    }

    public String getCategory_place_name() {
        return category_place_name;
    }

    public void setCategory_place_name(String category_place_name) {
        this.category_place_name = category_place_name;
    }

    public String getCategory_percent_off() {
        return category_percent_off;
    }

    public void setCategory_percent_off(String category_percent_off) {
        this.category_percent_off = category_percent_off;
    }
}
