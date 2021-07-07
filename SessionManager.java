package com.example.testre;

import android.content.SharedPreferences;

import android.content.Context;


public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedPreferences =
                context.getSharedPreferences("user_ID", 0);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    //------------------- config ------------------------------
    public String getIpconfig() {

        return "192.168.100.169:8080/apptest/";
        // <uses-library android:name="org.apache.http.legacy" android:required="false" />

    }
}
