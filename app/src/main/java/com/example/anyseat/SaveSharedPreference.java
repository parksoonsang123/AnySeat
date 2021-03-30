package com.example.anyseat;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.Serializable;

public class SaveSharedPreference implements Serializable{

    static final String PREF_USER_EMAIL = "useremail";
    static final String PREF_USER_PASSWORD = "userpassword";
    static final String PREF_USER_KEEP_LOGIN = "checked";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String Email, String Password, Boolean checked)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_EMAIL, Email);
        editor.putString(PREF_USER_PASSWORD, Password);
        editor.putBoolean(PREF_USER_KEEP_LOGIN, checked);
        editor.commit();
    }

    public static String getPrefUserEmail(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_EMAIL, "");
    }

    public static String getPrefUserPassword(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_PASSWORD, "");
    }

    public static Boolean getPrefUserKeepLogin(Context ctx)
    {
        return getSharedPreferences(ctx).getBoolean(PREF_USER_KEEP_LOGIN, false);
    }

    public static void clearUserName(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }

}

