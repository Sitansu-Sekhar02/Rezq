package com.sa.rezq.services.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class VideoTokenModel implements Serializable {

    private final String TAG = "VideoTokenModel";

    private final String
            ID                 = "id",
            USER_ID            = "user_id",
            CHANNEL_NAME       = "channel_name",
            UID                = "uid",
            UID_TOKEN          = "uid_token",
            TOKEN              = "token";

    String
            id               = null,
            userId           = null,
            channelName      = null,
            uid              = null,
            uidToken         = null,
            token            = null;

    public VideoTokenModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUidToken() {
        return uidToken;
    }

    public void setUidToken(String uidToken) {
        this.uidToken = uidToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean toObject(String jsonObject) {
        try {
            JSONObject json = new JSONObject(jsonObject);

            if (json.has(ID)) id = json.getString(ID);
            if (json.has(USER_ID)) userId = json.getString(USER_ID);
            if (json.has(CHANNEL_NAME)) channelName = json.getString(CHANNEL_NAME);
            if (json.has(UID)) uid = json.getString(UID);
            if (json.has(UID_TOKEN)) uidToken = json.getString(UID_TOKEN);
            if (json.has(TOKEN)) token = json.getString(TOKEN);

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
            jsonMain.put(USER_ID, userId);
            jsonMain.put(CHANNEL_NAME, channelName);
            jsonMain.put(UID, uid);
            jsonMain.put(UID_TOKEN, uidToken);
            jsonMain.put(TOKEN, token);

            returnString = jsonMain.toString();
        } catch (Exception ex) {
            Log.d(TAG, " To String Exception : " + ex);
        }
        return returnString;
    }
}
