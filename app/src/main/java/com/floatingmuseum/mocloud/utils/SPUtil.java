package com.floatingmuseum.mocloud.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.floatingmuseum.mocloud.MoCloud;
import com.floatingmuseum.mocloud.data.entity.TraktToken;

/**
 * Created by Floatingmuseum on 2016/8/4
 */
public class SPUtil {

	public static String getString(String key,
			String defaultValue) {
		return MoCloud.context.getSharedPreferences("config", Context.MODE_PRIVATE)
				.getString(key, defaultValue);
	}

	public static void editString(String key, String value) {
		Editor edit = MoCloud.context.getSharedPreferences("config",
				Context.MODE_PRIVATE).edit();
		edit.putString(key, value);
		edit.apply();
	}

	public static boolean getBoolean(String key,
			boolean defaultValue) {
		return MoCloud.context.getSharedPreferences("config", Context.MODE_PRIVATE)
				.getBoolean(key, defaultValue);
	}

	public static void editBoolean(String key, boolean value) {
		Editor edit = MoCloud.context.getSharedPreferences("config",
				Context.MODE_PRIVATE).edit();
		edit.putBoolean(key, value);
		edit.apply();
	}

	public static int getInt(String key,int defaultValue){
		return MoCloud.context.getSharedPreferences("config", Context.MODE_PRIVATE)
				.getInt(key, defaultValue);
	}

	public static void editInt(String key, int value) {
		Editor edit = MoCloud.context.getSharedPreferences("config",
				Context.MODE_PRIVATE).edit();
		edit.putInt(key, value);
		edit.apply();
	}

	public static void editLong(String key,long value){
		Editor edit = MoCloud.context.getSharedPreferences("config",
				Context.MODE_PRIVATE).edit();
		edit.putLong(key, value);
		edit.apply();
	}

	public static long getLong(String key){
		return MoCloud.context.getSharedPreferences("config", Context.MODE_PRIVATE)
				.getLong(key, 0);
	}

	public static void saveToken(TraktToken traktToken){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MoCloud.context);
		preferences.edit().putString("access_token",traktToken.getAccess_token()).apply();
		preferences.edit().putString("refresh_token",traktToken.getRefresh_token()).apply();
	}

	public static String getAccessToken(){
		return PreferenceManager.getDefaultSharedPreferences(MoCloud.context)
				.getString("access_token",null);
	}

	public static String getRefreshToken(){
		return PreferenceManager.getDefaultSharedPreferences(MoCloud.context)
				.getString("refresh_token",null);
	}
}
