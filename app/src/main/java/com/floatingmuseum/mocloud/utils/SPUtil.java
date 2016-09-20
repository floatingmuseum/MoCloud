package com.floatingmuseum.mocloud.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.floatingmuseum.mocloud.MoCloud;
import com.floatingmuseum.mocloud.data.entity.TraktToken;
import com.floatingmuseum.mocloud.data.entity.UserSettings;

/**
 * Created by Floatingmuseum on 2016/8/4
 */
public class SPUtil {

    public static final String SP_USER_SETTINGS = "UserSettings";

    public static String getString(String spFileName, String key, String defaultValue) {
        return MoCloud.context.getSharedPreferences(spFileName, Context.MODE_PRIVATE)
                .getString(key, defaultValue);
    }

    public static String getString(String key, String defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(MoCloud.context)
                .getString(key, defaultValue);
    }

    public static void editString(String spFileName, String key, String value) {
        MoCloud.context.getSharedPreferences(spFileName, Context.MODE_PRIVATE)
                .edit()
                .putString(key, value)
                .apply();
    }

    public static void editString(String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(MoCloud.context)
                .edit()
                .putString(key, value)
                .apply();
    }

    public static boolean getBoolean(String spFileName, String key, boolean defaultValue) {
        return MoCloud.context.getSharedPreferences(spFileName, Context.MODE_PRIVATE)
                .getBoolean(key, defaultValue);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(MoCloud.context)
                .getBoolean(key, defaultValue);
    }

    public static void editBoolean(String spFileName, String key, boolean value) {
        MoCloud.context.getSharedPreferences(spFileName, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(key, value)
                .apply();
    }

    public static void editBoolean(String key, boolean value) {
        PreferenceManager.getDefaultSharedPreferences(MoCloud.context)
                .edit()
                .putBoolean(key, value)
                .apply();
    }

    public static int getInt(String spFileName, String key, int defaultValue) {
        return MoCloud.context.getSharedPreferences(spFileName, Context.MODE_PRIVATE)
                .getInt(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(MoCloud.context)
                .getInt(key, defaultValue);
    }

    public static void editInt(String spFileName, String key, int value) {
        MoCloud.context.getSharedPreferences(spFileName, Context.MODE_PRIVATE)
                .edit()
                .putInt(key, value)
                .apply();
    }

    public static void editInt(String key, int value) {
        PreferenceManager.getDefaultSharedPreferences(MoCloud.context)
                .edit()
                .putInt(key, value)
                .apply();
    }

    public static long getLong(String key, long defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(MoCloud.context)
                .getLong(key, defaultValue);
    }

    public static long getLong(String spFileName, String key, long defaultValue) {
        return MoCloud.context.getSharedPreferences(spFileName, Context.MODE_PRIVATE)
                .getLong(key, defaultValue);
    }

    public static void editLong(String spFileName, String key, long value) {
        MoCloud.context.getSharedPreferences(spFileName, Context.MODE_PRIVATE)
                .edit()
                .putLong(key, value)
                .apply();
    }

    public static void editLong(String key, long value) {
        PreferenceManager.getDefaultSharedPreferences(MoCloud.context)
                .edit()
                .putLong(key, value)
                .apply();
    }

    public static float getFloat(String key, float defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(MoCloud.context)
                .getFloat(key, defaultValue);
    }

    public static float getFloat(String spFileName, String key, float defaultValue) {
        return MoCloud.context.getSharedPreferences(spFileName, Context.MODE_PRIVATE)
                .getFloat(key, defaultValue);
    }

    public static void editFloat(String key, float value) {
        PreferenceManager.getDefaultSharedPreferences(MoCloud.context)
                .edit()
                .putFloat(key, value)
                .apply();
    }

    public static void editFloat(String spFileName, String key, float value) {
        MoCloud.context.getSharedPreferences(spFileName, Context.MODE_PRIVATE)
                .edit()
                .putFloat(key, value)
                .apply();
    }

    public static void saveToken(TraktToken traktToken) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MoCloud.context);
        if (!TextUtils.isEmpty(traktToken.getAccess_token()) && !TextUtils.isEmpty(traktToken.getRefresh_token())) {
            preferences.edit()
                    .putString("access_token", traktToken.getAccess_token())
                    .putString("refresh_token", traktToken.getRefresh_token())
                    .putBoolean("isLogin", true)
                    .apply();
        }
    }

    public static void saveUserSettings(UserSettings userSettings) {
        SharedPreferences preferences = MoCloud.context.getSharedPreferences(SP_USER_SETTINGS, Context.MODE_PRIVATE);
        preferences.edit()
                .putString("username", userSettings.getUsername())
                .putBoolean("private", userSettings.isPrivateX())
                .putString("name", userSettings.getName())
                .putBoolean("vip", userSettings.isVip())
                .putBoolean("vip_ep", userSettings.isVip_ep())
                .putString("slug", userSettings.getIds().getSlug())
                .putString("joined_at", userSettings.getJoined_at())
                .putString("location", userSettings.getLocation())
                .putString("about", userSettings.getAbout())
                .putString("gender", userSettings.getGender())
                .putInt("age", userSettings.getAge())
                .putString("avatar", userSettings.getImages().getAvatar().getFull())
                .putString("timezone", userSettings.getAccount().getTimezone())
                .putBoolean("time_24hr", userSettings.getAccount().isTime_24hr())
                .putString("cover_image", userSettings.getAccount().getCover_image())
                .putBoolean("facebook", userSettings.getConnections().isFacebook())
                .putBoolean("twitter", userSettings.getConnections().isTwitter())
                .putBoolean("google", userSettings.getConnections().isGoogle())
                .putBoolean("tumblr", userSettings.getConnections().isTumblr())
                .putBoolean("medium", userSettings.getConnections().isMedium())
                .putBoolean("slack", userSettings.getConnections().isSlack())
                .putString("watching", userSettings.getSharing_text().getWatching())
                .putString("watched", userSettings.getSharing_text().getWatched())
                .apply();
    }

    public static void removeUserSettings() {
        SharedPreferences preferences = MoCloud.context.getSharedPreferences(SP_USER_SETTINGS, Context.MODE_PRIVATE);
        preferences.edit()
                .clear()
                .apply();
    }

    public static boolean isLogin() {
        return PreferenceManager.getDefaultSharedPreferences(MoCloud.context)
                .getBoolean("isLogin", false);
    }

    public static String getAccessToken() {
        return PreferenceManager.getDefaultSharedPreferences(MoCloud.context)
                .getString("access_token", null);
    }

    public static String getRefreshToken() {
        return PreferenceManager.getDefaultSharedPreferences(MoCloud.context)
                .getString("refresh_token", null);
    }
}
