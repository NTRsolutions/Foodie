package com.belac.ines.foodie.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionManager {

    private static final String NAME_KEY = "SessionManager.NAME";
    private static final String ID_KEY = "SessionManager.ID";
    private static final String SURNAME_KEY = "SessionManager.SURNAME";
    private static final String EMAIL_KEY = "SessionManager.EMAIL";
    private static final String TELEPHONE_KEY = "SessionManager.TELEPHONE";
    private static final String ADDRESS_KEY = "SessionManager.ADDRESS";
    private static final String TYPE_KEY = "SessionManager.TYPE";

    private static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private static void setName(Context context, String name) {
        getPreferences(context).edit().putString(NAME_KEY, name).apply();
    }

    private static void setId(Context context, String id) {
        getPreferences(context).edit().putString(ID_KEY, id).apply();
    }

    private static void setSurname(Context context, String surname) {
        getPreferences(context).edit().putString(SURNAME_KEY, surname).apply();
    }

    private static void setEmail(Context context, String email) {
        getPreferences(context).edit().putString(EMAIL_KEY, email).apply();
    }

    private static void setTelephone(Context context, String telephone) {
        getPreferences(context).edit().putString(TELEPHONE_KEY, telephone).apply();
    }

    private static void setAddress(Context context, String address) {
        getPreferences(context).edit().putString(ADDRESS_KEY, address).apply();
    }

    private static void setType(Context context, int type) {
        getPreferences(context).edit().putInt(TYPE_KEY, type).apply();
    }

    public static String getName(Context context) {
        return getPreferences(context).getString(NAME_KEY, null);
    }

    public static String getSurname(Context context) {
        return getPreferences(context).getString(SURNAME_KEY, null);
    }

    public static String getEmail(Context context) {
        return getPreferences(context).getString(EMAIL_KEY, null);
    }

    public static String getTelephone(Context context) {
        return getPreferences(context).getString(TELEPHONE_KEY, null);
    }

    public static String getAddress(Context context) {
        return getPreferences(context).getString(ADDRESS_KEY, null);
    }

    public static String getId(Context context) {
        return getPreferences(context).getString(ID_KEY, null);
    }

    public static int getType(Context context) {
        return getPreferences(context).getInt(TYPE_KEY, -1);
    }

    public static void setLoggIn(Context context, String name, String surname, String email, String telephone,
                                 String address, int type, String id) {
        setName(context, name);
        setSurname(context, surname);
        setEmail(context, email);
        setTelephone(context, telephone);
        setAddress(context, address);
        setType(context, type);
        setId(context, id);
    }

    public static boolean isUserLoggedIn(Context context) {
        SharedPreferences prefs = getPreferences(context);
        return prefs.getString(NAME_KEY, null) != null && prefs.getString(SURNAME_KEY, null) != null
                && prefs.getString(EMAIL_KEY, null) != null && prefs.getString(ID_KEY, null) != null;
    }

    public static void logout(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().remove(NAME_KEY).apply();
        preferences.edit().remove(SURNAME_KEY).apply();
        preferences.edit().remove(EMAIL_KEY).apply();
        preferences.edit().remove(TELEPHONE_KEY).apply();
        preferences.edit().remove(ADDRESS_KEY).apply();
        preferences.edit().remove(TYPE_KEY).apply();
        preferences.edit().remove(ID_KEY).apply();
    }

}
