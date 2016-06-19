package com.floatingmuseum.mocloud.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;

import com.floatingmuseum.mocloud.MoCloud;

/**
 * ����config���sp�ļ�
 * @author Administrator
 *
 */
public class SPUtil {
	/**
	 * ��ȡconfig�ļ���String���Ե�ֵ
	 * @param context
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getString(Context context, String key,
			String defaultValue) {
		return context.getSharedPreferences("config", Context.MODE_PRIVATE)
				.getString(key, defaultValue);
	}

	/**
	 * ���һ��String���Ե�ֵ��config�ļ���
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void editString(Context context, String key, String value) {
		Editor edit = context.getSharedPreferences("config",
				Context.MODE_PRIVATE).edit();
		edit.putString(key, value);
		edit.commit();
	}

	/**
	 * ��ȡconfig�ļ���boolean���Ե�ֵ
	 * @param context
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBoolean(Context context, String key,
			boolean defaultValue) {
		return context.getSharedPreferences("config", Context.MODE_PRIVATE)
				.getBoolean(key, defaultValue);
	}
	/**
	 * ���һ��boolean���Ե�ֵ��config�ļ���
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void editBoolean(Context context, String key, boolean value) {
		Editor edit = context.getSharedPreferences("config",
				Context.MODE_PRIVATE).edit();
		edit.putBoolean(key, value);
		edit.commit();
	}
	
	/**
	 * ��ȡconfig�ļ���int���Ե�ֵ
	 * @param context
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getInt(Context context,String key,int defaultValue){
		return context.getSharedPreferences("config", Context.MODE_PRIVATE)
				.getInt(key, defaultValue);
	}
	
	/**
	 * ���һ��int���Ե�ֵ��config�ļ���
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void editInt(Context context, String key, int value) {
		Editor edit = context.getSharedPreferences("config",
				Context.MODE_PRIVATE).edit();
		edit.putInt(key, value);
		edit.commit();
	}

	public static void editLong(String key,long value){
		Editor edit = MoCloud.context.getSharedPreferences("config",
				Context.MODE_PRIVATE).edit();
		edit.putLong(key, value);
		edit.commit();
	}

	public static long getLong(String key){
		return MoCloud.context.getSharedPreferences("config", Context.MODE_PRIVATE)
				.getLong(key, 0);
	}
}
