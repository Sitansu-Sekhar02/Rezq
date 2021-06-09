package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class FilterModel implements Serializable {

    private final String TAG = "FilterModel";

    private final String
            ID                   = "id",
            TITLE                = "title",
            IMAGE                = "image",
            MIN_PRICE            = "min_price",
            MAX_PRICE            = "max_price",
            LINK                 = "link",
            IS_SELECTED          ="is_selected";

    String
            id               = null,
            title            = null,
            image            = null,
            minPrice         = null,
            maxPrice         = null,
            link             = null;

    boolean isSelected=false;

    public FilterModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
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

                if (json.has(ID)) id = json.getString(ID);
                if (json.has(TITLE)) title = json.getString(TITLE);
                if (json.has(IMAGE)) image = json.getString(IMAGE);
                if (json.has(LINK)) link = json.getString(LINK);
            if(json.has(MIN_PRICE)){this.minPrice = json.getString(MIN_PRICE);}
            if(json.has(MAX_PRICE)){this.maxPrice = json.getString(MAX_PRICE);}
                if(json.has(IS_SELECTED))this.isSelected = json.getBoolean(IS_SELECTED);

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
            jsonMain.put(TITLE, title);
            jsonMain.put(IMAGE, image);
            jsonMain.put(LINK, link);
            jsonMain.put(MIN_PRICE, minPrice);
            jsonMain.put(MAX_PRICE, maxPrice);
            jsonMain.put(IS_SELECTED, this.isSelected);

            returnString = jsonMain.toString();
        } catch (Exception ex) {
            Log.d(TAG, " To String Exception : " + ex);
        }
        return returnString;
    }
}
