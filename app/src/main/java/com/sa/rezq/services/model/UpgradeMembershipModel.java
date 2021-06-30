package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class UpgradeMembershipModel implements Serializable {

    private final String TAG = "UpgradeMembershipModel";

    private final String
                    ID                        = "id",
                    MEMBERSHIP_ID             ="membership_id",
                    NAME                       ="name",
                    NO_OF_DAYS                 ="no_of_days",
                    PRICE                      = "price",
                    PRIORITY                   = "priority",
                    DESCRIPTION                 = "description",
                    IMAGE                       = "image";


    private String
             id                  = null,
            membership_id       =null,
            name                =null,
            no_of_days          = null,
            price               = null,
            priority            = null,
            description         = null,
            image                 = null;



    List<UpgradeMembershipModel> orderModelList = new ArrayList<UpgradeMembershipModel>();


    public UpgradeMembershipModel(){}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMembership_id() {
        return membership_id;
    }

    public void setMembership_id(String membership_id) {
        this.membership_id = membership_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo_of_days() {
        return no_of_days;
    }

    public void setNo_of_days(String no_of_days) {
        this.no_of_days = no_of_days;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<UpgradeMembershipModel> getOrderModelList() {
        return orderModelList;
    }

    public void setOrderModelList(List<UpgradeMembershipModel> orderModelList) {
        this.orderModelList = orderModelList;
    }

    public boolean toObject(String jsonObject){
        try{
            JSONObject json = new JSONObject(jsonObject);

            if (json.has(ID)) {
                id= json.getString(ID);
            }
            if (json.has(MEMBERSHIP_ID)) {
                membership_id = json.getString(MEMBERSHIP_ID);

            }if (json.has(NAME)) {
                name = json.getString(NAME);
            }if (json.has(NO_OF_DAYS)) {
                no_of_days = json.getString(NO_OF_DAYS);
            }if (json.has(PRICE)) {
                price = json.getString(PRICE);
            }if (json.has(PRIORITY)) {
                priority = json.getString(PRIORITY);
            }if (json.has(IMAGE)) {
                image = json.getString(IMAGE);
            }if (json.has(DESCRIPTION)) {
                description = json.getString(DESCRIPTION);
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
            //jsonMain.put(ID, this.id);
            jsonMain.put(ID, id);
            jsonMain.put(MEMBERSHIP_ID,membership_id);

            jsonMain.put(NAME, name);
            jsonMain.put(NO_OF_DAYS, no_of_days);
            jsonMain.put(PRICE, price);
            jsonMain.put(PRIORITY, priority);
            jsonMain.put(IMAGE, image);
            jsonMain.put(DESCRIPTION, description);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){
            Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }

}
