package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class SubCategoryModel implements Serializable {

    private final String TAG = "SubCategoryModel";

    private final String
            ID           = "id",
            NAME         = "name",
            TITLE        = "title",
            IMAGE        = "image",
            BRAND        = "brand",
            SUBCAT       = "subcategory",
            PRICE        = "price",
            SELECTED     = "isSelected",
            EXTRA        = "description";

    String
            id          = null,
            name        = null,
            title       = null,
            image       = null,
            brand       = null,
            subCat      = null,
            price       = null,
            extra       = null;

    boolean isSelected=false;

    public SubCategoryModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSubCat() {
        return subCat;
    }

    public void setSubCat(String subCat) {
        this.subCat = subCat;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean toObject(String jsonObject) {
        try {
            JSONObject json = new JSONObject(jsonObject);
            id = json.getString(ID);
            if (json.has(TITLE)) title = json.getString(TITLE);
            if (json.has(NAME)) name = json.getString(NAME);
            if (json.has(EXTRA)) extra = json.getString(EXTRA);
            if (json.has(IMAGE)) image = json.getString(IMAGE);
            if (json.has(BRAND)) brand = json.getString(BRAND);
            if (json.has(SUBCAT)) subCat = json.getString(SUBCAT);
            if (json.has(PRICE)) price = json.getString(PRICE);
            if (json.has(SELECTED)) isSelected = json.getBoolean(SELECTED);

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
            jsonMain.put(ID, id);
            jsonMain.put(NAME, name);
            jsonMain.put(TITLE, title);
            jsonMain.put(EXTRA, extra);
            jsonMain.put(IMAGE, image);
            jsonMain.put(BRAND, brand);
            jsonMain.put(SUBCAT, subCat);
            jsonMain.put(PRICE, price);
            jsonMain.put(SELECTED, isSelected);

            returnString = jsonMain.toString();
        } catch (Exception ex) {
            Log.d(TAG, " To String Exception : " + ex);
        }
        return returnString;
    }
}
