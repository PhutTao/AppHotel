package com.example.apphotel.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ROLE = "role";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Save user data
    public void saveUser(int id, String username, String email, int role) {
        editor.putInt(KEY_ID, id);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putInt(KEY_ROLE, role);
        editor.apply();
    }

    // Retrieve user ID
    public int getUserId() {
        return sharedPreferences.getInt(KEY_ID, -1);
    }

    // Retrieve username
    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    // Retrieve email
    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, null);
    }
    public int getRole() {
        return sharedPreferences.getInt(KEY_ROLE, 1);
    }

    // Clear session
    public void clearSession() {
        editor.clear();
        editor.apply();
    }
}
