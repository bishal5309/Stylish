package com.example.stylish;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginHelper {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    // Dummy Data
    public static final String DUMMY_EMAIL = "admin@mail.com";
    public static final String DUMMY_PASS = "123456";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public LoginHelper(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    // Dummy login check function
    public boolean validateDummyLogin(String email, String pass) {
        return email.equals(DUMMY_EMAIL) && pass.equals(DUMMY_PASS);
    }

    // Session save kora (Login thakar jonno)
    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }

    // User ki login obosthay ache?
    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Log out logic
    public void logout() {
        editor.clear();
        editor.commit();
    }

    private static final String KEY_IS_FIRST_TIME = "isFirstTime";

    // Onboarding shesh hole eta call korbe
    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(KEY_IS_FIRST_TIME, isFirstTime);
        editor.commit();
    }

    // Check korbe eta ki prothom bar launch? Default true thakbe.
    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(KEY_IS_FIRST_TIME, true);
    }
}