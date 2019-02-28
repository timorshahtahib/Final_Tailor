package com.hmdapp.finaltailor.database;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by King on 3/25/2018.
 */

public class LocalData {
    private static final String APP_SHARED_PREFS = "RemindMePref";

    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;


    private static final String min = "min";


    Context context;

    public LocalData(Context context) {
        this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
        this.context = context;
    }

    // Settings Page Reminder Time (Hour)


    public void reset() {
        prefsEditor.remove("user");
        prefsEditor.commit();

    }

    public void set_customer_reply(boolean v) {
        prefsEditor.putBoolean("reply", v);
        prefsEditor.commit();

    }

    public boolean get_customer_confirm() {

        return appSharedPrefs.getBoolean("confirm", false);
    }

    public void set_customer_confirm(boolean v) {
        prefsEditor.putBoolean("confirm", v);
        prefsEditor.commit();

    }

    public boolean get_customer_reply() {

        return appSharedPrefs.getBoolean("reply", false);
    }

    public void set_number_set_reply(Set<String> set_reply) {

        prefsEditor.putStringSet("set_reply", set_reply);
        prefsEditor.commit();
    }

    public HashSet<String> get_number_set_reply() {
        return (HashSet<String>) appSharedPrefs.getStringSet("set_reply", new HashSet<String>());

    }


    /// start  reply for  customer
    public boolean get_rosahn_reply() {

        return appSharedPrefs.getBoolean("r_reply", false);
    }

    public void set_roshan_reply(boolean v) {
        prefsEditor.putBoolean("r_reply", v);
        prefsEditor.commit();
    }

    public boolean get_etisalat_reply() {

        return appSharedPrefs.getBoolean("et_reply", false);
    }

    public void set_etisalat_reply(boolean v) {
        prefsEditor.putBoolean("et_reply", v);
        prefsEditor.commit();
    }


    public boolean get_salam_reply() {

        return appSharedPrefs.getBoolean("sa_reply", false);
    }

    public void set_salam_reply(boolean v) {
        prefsEditor.putBoolean("sa_reply", v);
        prefsEditor.commit();
    }


    public boolean get_awc_reply() {

        return appSharedPrefs.getBoolean("awc_reply", false);
    }

    public void set_awc_reply(boolean v) {
        prefsEditor.putBoolean("awc_reply", v);
        prefsEditor.commit();
    }


    public boolean get_mtn_reply() {

        return appSharedPrefs.getBoolean("mtn_reply", false);
    }

    public void set_mtn_reply(boolean v) {
        prefsEditor.putBoolean("mtn_reply", v);
        prefsEditor.commit();
    }


/// end  reply for  customer


    /// start  end msg for  customer
    public boolean get_rosahn_end() {

        return appSharedPrefs.getBoolean("r_end", false);
    }

    public void set_roshan_end(boolean v) {
        prefsEditor.putBoolean("r_end", v);
        prefsEditor.commit();
    }

    public boolean get_etisalat_end() {

        return appSharedPrefs.getBoolean("et_end", false);
    }

    public void set_etisalat_end(boolean v) {
        prefsEditor.putBoolean("et_end", v);
        prefsEditor.commit();
    }


    public boolean get_salam_end() {

        return appSharedPrefs.getBoolean("sa_end", false);
    }

    public void set_salam_end(boolean v) {
        prefsEditor.putBoolean("sa_end", v);
        prefsEditor.commit();
    }


    public boolean get_awc_end() {

        return appSharedPrefs.getBoolean("awc_end", false);
    }

    public void set_awc_end(boolean v) {
        prefsEditor.putBoolean("awc_end", v);
        prefsEditor.commit();
    }


    public boolean get_mtn_end() {

        return appSharedPrefs.getBoolean("mtn_end", false);
    }

    public void set_mtn_end(boolean v) {
        prefsEditor.putBoolean("mtn_end", v);
        prefsEditor.commit();
    }

}
/// end  end msgfor  customer





