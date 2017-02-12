package com.floatingmuseum.mocloud.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.floatingmuseum.mocloud.MoCloud;
import com.floatingmuseum.mocloud.data.entity.Ids;
import com.floatingmuseum.mocloud.data.entity.Image;
import com.floatingmuseum.mocloud.data.entity.LastActivities;
import com.floatingmuseum.mocloud.data.entity.TraktToken;
import com.floatingmuseum.mocloud.data.entity.User;
import com.floatingmuseum.mocloud.data.entity.UserSettings;

/**
 * Created by Floatingmuseum on 2016/8/4
 */
public class SPUtil {

    public static final String SP_USER_SETTINGS = "userSettings";
    public static final String SP_USER_LASTACTIVITIES = "userLastActivities";

    /**
     * 不可以修改Key
     */
    public static final String KEY_ACCESS_TOKEN = "access_token";
    public static final String KEY_REFRESH_TOKEN = "refresh_token";
    public static final String KEY_IS_LOGIN = "isLogin";
    public static final String KEY_COMMENTS_SORT_CONDITION = "comments_sort_condition";

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

    public static void saveUserLastActivities(LastActivities lastActivities){
        SharedPreferences preferences = MoCloud.context.getSharedPreferences(SP_USER_LASTACTIVITIES, Context.MODE_PRIVATE);
        preferences.edit()
                .putString("all",lastActivities.getAll())
                .putString("movies_watched_at",lastActivities.getMovies().getWatched_at())
                .putString("movies_collected_at",lastActivities.getMovies().getCollected_at())
                .putString("movies_rated_at",lastActivities.getMovies().getRated_at())
                .putString("movies_watchlisted_at",lastActivities.getMovies().getWatchlisted_at())
                .putString("movies_commented_at",lastActivities.getMovies().getCommented_at())
                .putString("movies_paused_at",lastActivities.getMovies().getPaused_at())
                .putString("movies_hidden_at",lastActivities.getMovies().getHidden_at())
                .putString("episodes_watched_at",lastActivities.getEpisodes().getWatched_at())
                .putString("episodes_collected_at",lastActivities.getEpisodes().getCollected_at())
                .putString("episodes_rated_at",lastActivities.getEpisodes().getRated_at())
                .putString("episodes_watchlisted_at",lastActivities.getEpisodes().getWatchlisted_at())
                .putString("episodes_commented_at",lastActivities.getEpisodes().getCommented_at())
                .putString("episodes_paused_at",lastActivities.getEpisodes().getPaused_at())
                .putString("shows_rated_at",lastActivities.getShows().getRated_at())
                .putString("shows_watchlisted_at",lastActivities.getShows().getWatchlisted_at())
                .putString("shows_commented_at",lastActivities.getShows().getCommented_at())
                .putString("shows_hidden_at",lastActivities.getShows().getHidden_at())
                .putString("seasons_rated_at",lastActivities.getSeasons().getRated_at())
                .putString("seasons_watchlisted_at",lastActivities.getSeasons().getWatchlisted_at())
                .putString("seasons_commented_at",lastActivities.getSeasons().getCommented_at())
                .putString("seasons_hidden_at",lastActivities.getSeasons().getHidden_at())
                .putString("comments_liked_at",lastActivities.getComments().getLiked_at())
                .putString("lists_liked_at",lastActivities.getLists().getLiked_at())
                .putString("lists_updated_at",lastActivities.getLists().getUpdated_at())
                .putString("lists_commented_at",lastActivities.getLists().getCommented_at())
                .apply();
    }

    public static void saveToken(TraktToken traktToken) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MoCloud.context);
        if (!TextUtils.isEmpty(traktToken.getAccess_token()) && !TextUtils.isEmpty(traktToken.getRefresh_token())) {
            preferences.edit()
                    .putString(KEY_ACCESS_TOKEN, traktToken.getAccess_token())
                    .putString(KEY_REFRESH_TOKEN, traktToken.getRefresh_token())
                    .putBoolean(KEY_IS_LOGIN, true)
                    .apply();
        }
    }

    public static User loadUserFromSp() {
        User user = new User();
        user.setName(getString(SP_USER_SETTINGS, "username", ""));
        user.setPrivateX(getBoolean(SP_USER_SETTINGS, "private", false));
        Ids ids = new Ids();
        ids.setSlug(getString(SP_USER_SETTINGS, "slug", ""));
        user.setIds(ids);
        Image.Avatar avatar = new Image.Avatar();
        avatar.setFull(getString(SP_USER_SETTINGS, "avatar", ""));
        Image image = new Image();
        image.setAvatar(avatar);
        user.setImages(image);
        return user;
    }

    public static void saveUserSettings(UserSettings userSettings) {
        SharedPreferences preferences = MoCloud.context.getSharedPreferences(SP_USER_SETTINGS, Context.MODE_PRIVATE);
        User user = userSettings.getUser();
        preferences.edit()
                .putString("username", user.getUsername())
                .putBoolean("private", user.isPrivateX())
                .putString("name", user.getName())
                .putBoolean("vip", user.isVip())
                .putBoolean("vip_ep", user.isVip_ep())
                .putString("slug", user.getIds().getSlug())
                .putString("joined_at", user.getJoined_at())
                .putString("location", user.getLocation())
                .putString("about", user.getAbout())
                .putString("gender", user.getGender())
                .putInt("age", user.getAge())
                .putString("avatar", user.getImages().getAvatar().getFull())
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
                .getBoolean(KEY_IS_LOGIN, false);
    }

    public static String getAccessToken() {
        return PreferenceManager.getDefaultSharedPreferences(MoCloud.context)
                .getString(KEY_ACCESS_TOKEN, null);
    }

    public static String getRefreshToken() {
        return PreferenceManager.getDefaultSharedPreferences(MoCloud.context)
                .getString(KEY_REFRESH_TOKEN, null);
    }
}
