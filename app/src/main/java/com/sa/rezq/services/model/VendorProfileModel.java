package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class VendorProfileModel implements Serializable {
    private final String TAG = "VendorProfileModel";

    private final String
            ID                    = "id",
            CUSTOMER_ID           = "customer_id",
            USER_ID               = "user_id",
            FIRST_NAME            = "first_name",
            LAST_NAME             = "last_name",
            COMPANY_NAME          = "company_name",
            COMPANY_REG           = "company_registration",
            RATING                = "rating",
            COUNTRY_NAME          = "country_name",
            COUNTRY_ID            = "country_id",
            CART                  = "cart",
            TOKEN                 = "token",
            EMAIL                 = "email_id",
            GENDER                = "gender",
            DATE_OF_BIRTH         = "date_of_birth",
            COUNTRY_CODE          = "country_code",
            MOBILE_NUMBER         = "mobile_number",
            FULL_NAME             = "vendor_public_name",
            PROFILE_IMAGE         = "profile_image",
            BANNER_IMAGE          = "banner_image",
            DESCRIPTION           = "description",
            ADDRESS               = "head_office_address",
            LATITUDE              = "latitude",
            LONGITUDE             = "longitude",
            CITY_ID               = "city_id",
            CITY_NAME             = "city_name",
            FOLLOWER              = "follower",
            VERIFIED              = "is_premium";

    String
            id                    = null,
            customerId            = null,
            userId                = null,
            firstName             = null,
            lastName              = null,
            rating                = null,
            companyName           = null,
            companyReg            = null,
            countryName           = null,
            countryId             = null,
            cart                  = null,
            token                 = null,
            emailId               = null,
            gender                = null,
            dateOfBirth           = null,
            countryCode           = null,
            mobileNumber          = null,
            fullName              = null,
            profileImage          = null,
            bannerImage           = null,
            description           = null,
            address               = null,
            latitude              = null,
            longitude             = null,
            cityId                = null,
            cityName              = null,
            follower              = null,
            verified              = null;

    public VendorProfileModel(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCompanyReg() {
        return companyReg;
    }

    public void setCompanyReg(String companyReg) {
        this.companyReg = companyReg;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public boolean toObject(String jsonObject){
        try{
            JSONObject json = new JSONObject(jsonObject);
            if(json.has(ID)){id = json.getString(ID);}
            if(json.has(CUSTOMER_ID)){customerId = json.getString(CUSTOMER_ID);}
            if(json.has(USER_ID)){userId = json.getString(USER_ID);}
            if(json.has(FIRST_NAME)){this.firstName = json.getString(FIRST_NAME);}
            if(json.has(LAST_NAME)){this.lastName = json.getString(LAST_NAME);}
            if(json.has(COUNTRY_NAME)){countryName = json.getString(COUNTRY_NAME);}
            if(json.has(COUNTRY_ID)){this.countryId = json.getString(COUNTRY_ID);}
            if(json.has(RATING)){this.rating = json.getString(RATING);}
            if(json.has(CART)){this.cart = json.getString(CART);}
            if(json.has(TOKEN)){this.token = json.getString(TOKEN);}
            if(json.has(EMAIL)){this.emailId = json.getString(EMAIL);}
            if(json.has(GENDER)){this.gender = json.getString(GENDER);}
            if(json.has(DATE_OF_BIRTH)){this.dateOfBirth = json.getString(DATE_OF_BIRTH);}
            if(json.has(COUNTRY_CODE)){this.countryCode = json.getString(COUNTRY_CODE);}
            if(json.has(MOBILE_NUMBER)){this.mobileNumber = json.getString(MOBILE_NUMBER);}
            if(json.has(FULL_NAME)){this.fullName = json.getString(FULL_NAME);}
            if(json.has(PROFILE_IMAGE)){this.profileImage = json.getString(PROFILE_IMAGE);}
            if(json.has(BANNER_IMAGE)){this.bannerImage = json.getString(BANNER_IMAGE);}
            if(json.has(DESCRIPTION)){this.description = json.getString(DESCRIPTION);}
            if(json.has(LATITUDE)){this.latitude = json.getString(LATITUDE);}
            if(json.has(LONGITUDE)){this.longitude = json.getString(LONGITUDE);}
            if(json.has(CITY_ID)){this.cityId = json.getString(CITY_ID);}
            if(json.has(CITY_NAME)){this.cityName = json.getString(CITY_NAME);}
            if(json.has(FOLLOWER)){this.follower = json.getString(FOLLOWER);}
            if(json.has(VERIFIED)){this.verified = json.getString(VERIFIED);}
            if(json.has(COMPANY_NAME)){this.companyName = json.getString(COMPANY_NAME);}
            if(json.has(COMPANY_REG)){this.companyReg = json.getString(COMPANY_REG);}
            if(json.has(ADDRESS)){this.address = json.getString(ADDRESS);}

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
            jsonMain.put(CUSTOMER_ID, customerId);
            jsonMain.put(USER_ID, userId);
            jsonMain.put(FIRST_NAME, firstName);
            jsonMain.put(LAST_NAME, lastName);
            jsonMain.put(COUNTRY_NAME, countryName);
            jsonMain.put(COMPANY_NAME, companyName);
            jsonMain.put(COUNTRY_ID, countryId);
            jsonMain.put(CART, cart);
            jsonMain.put(RATING, rating);
            jsonMain.put(TOKEN, token);
            jsonMain.put(EMAIL, emailId);
            jsonMain.put(GENDER, gender);
            jsonMain.put(DATE_OF_BIRTH, dateOfBirth);
            jsonMain.put(COUNTRY_CODE, countryCode);
            jsonMain.put(MOBILE_NUMBER, mobileNumber);
            jsonMain.put(FULL_NAME, fullName);
            jsonMain.put(PROFILE_IMAGE, profileImage);
            jsonMain.put(BANNER_IMAGE, bannerImage);
            jsonMain.put(DESCRIPTION, description);
            jsonMain.put(LATITUDE, latitude);
            jsonMain.put(LONGITUDE, longitude);
            jsonMain.put(CITY_ID, cityId);
            jsonMain.put(CITY_NAME, cityName);
            jsonMain.put(FOLLOWER, follower);
            jsonMain.put(VERIFIED, verified);
            jsonMain.put(ADDRESS, address);
            jsonMain.put(COMPANY_REG, companyReg);

            returnString = jsonMain.toString();
        }
        catch (Exception ex){Log.d(TAG," To String Exception : "+ex);}
        return returnString;
    }
}
