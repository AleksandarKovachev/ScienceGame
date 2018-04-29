package com.tu.sofia.sciencegame.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.tu.sofia.sciencegame.constant.SharedPreferencesConstants;

/**
 * Created by Aleksandar Kovachev on 29.04.2018 г..
 */
public class SharedPreferencesManager {

    private SharedPreferences sharedPreferences;

    public SharedPreferencesManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SharedPreferencesConstants.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void setLoginState(boolean loginState, String username, String password, Long userType) {
        sharedPreferences.edit()
                .putBoolean(SharedPreferencesConstants.IS_LOGIN, loginState)
                .putString(SharedPreferencesConstants.USERNAME, username)
                .putString(SharedPreferencesConstants.PASSWORD, password)
                .putLong(SharedPreferencesConstants.USER_TYPE, userType)
                .apply();
    }

    public void put(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public boolean getLoginState() {
        return sharedPreferences.getBoolean(SharedPreferencesConstants.IS_LOGIN, false);
    }

    public void remove(String key) {
        sharedPreferences.edit()
                .remove(key)
                .apply();
    }

    public void clearLoginState() {
        sharedPreferences.edit()
                .remove(SharedPreferencesConstants.IS_LOGIN)
                .remove(SharedPreferencesConstants.USERNAME)
                .remove(SharedPreferencesConstants.PASSWORD)
                .remove(SharedPreferencesConstants.USER_TYPE)
                .apply();
    }

}
