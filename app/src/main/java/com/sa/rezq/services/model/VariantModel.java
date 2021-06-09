package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class VariantModel implements Serializable {
    private final String TAG = "VariantModel";

    private final String
            ID               = "id_product",
            VARIATION_ID     = "id_variation",
            PRICE            = "unit_price",
            SPECIFICATION    = "specification",
            TITLE            = "name",
            SPEC_VAL_ID      = "specification_value_id",
            VALUE            = "value",
            SELECTED         = "selected",
            VARIANT2         = "variation2",
            VARIANT3         = "variation3";

    private String
            id            = null,
            varId         = null,
            price         = null,
            specification = null,
            specValId     = null,
            value         = null,
            title         = null,
            selected      = null;


    VariantListModel
            variant2ListModel   = null,
            variant3ListModel   = null;

    public VariantModel(){}


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

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getVarId() {
        return varId;
    }

    public void setVarId(String varId) {
        this.varId = varId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getSpecValId() {
        return specValId;
    }

    public void setSpecValId(String specValId) {
        this.specValId = specValId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public VariantListModel getVariant2ListModel() {
        return variant2ListModel;
    }

    public void setVariant2ListModel(VariantListModel variant2ListModel) {
        this.variant2ListModel = variant2ListModel;
    }

    public VariantListModel getVariant3ListModel() {
        return variant3ListModel;
    }

    public void setVariant3ListModel(VariantListModel variant3ListModel) {
        this.variant3ListModel = variant3ListModel;
    }

    public boolean toObject(String jsonObject){
        try{

            JSONObject json = new JSONObject(jsonObject);

            if(json.has(ID)){this.id = json.getString(ID);}
            if(json.has(TITLE)){this.title = json.getString(TITLE);}
            if(json.has(PRICE)){this.price = json.getString(PRICE);}
            if(json.has(SPECIFICATION)){this.specification = json.getString(SPECIFICATION);}
            if(json.has(SPEC_VAL_ID)){this.specValId = json.getString(SPEC_VAL_ID);}
            if(json.has(VALUE)){this.value = json.getString(VALUE);}
            if(json.has(SELECTED)){this.selected = json.getString(SELECTED);}
            if(json.has(VARIATION_ID)){this.varId = json.getString(VARIATION_ID);}

            if(json.has(VARIANT2)) {
                JSONArray array = json.getJSONArray(VARIANT2);
                VariantListModel listModelLocal = new VariantListModel();
                listModelLocal.setRESPONSE(VARIANT2);
                if(listModelLocal.toObject(array)){this.variant2ListModel = listModelLocal;}
                else{this.variant2ListModel = null;}
            }

            if(json.has(VARIANT3)) {
                JSONArray array = json.getJSONArray(VARIANT3);
                VariantListModel listModelLocal = new VariantListModel();
                listModelLocal.setRESPONSE(VARIANT3);
                if(listModelLocal.toObject(array)){this.variant3ListModel = listModelLocal;}
                else{this.variant3ListModel = null;}
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
            jsonMain.put(ID, id);
            jsonMain.put(TITLE, title);
            jsonMain.put(SPEC_VAL_ID, specValId);
            jsonMain.put(SPECIFICATION, specification);
            jsonMain.put(VALUE, value);
            jsonMain.put(VARIATION_ID, varId);
            jsonMain.put(SELECTED, selected);
            jsonMain.put(PRICE, price);
            jsonMain.put(VARIANT2, variant2ListModel!=null?new JSONArray(variant2ListModel.toString(true)):null);
            jsonMain.put(VARIANT3, variant3ListModel!=null?new JSONArray(variant3ListModel.toString(true)):null);
            returnString = jsonMain.toString();
        }
        catch (Exception ex){Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}


