package com.vzone.tmdsdk.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesUtils {

	private SharedPreferences preferences;
	private Editor editor;
	private static PreferencesUtils instance;

	private PreferencesUtils(Context context) {
		preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		editor = preferences.edit();
	}

	public static PreferencesUtils getInstance(Context context) {
		if (instance == null)
			instance = new PreferencesUtils(context);
		return instance;
	}

    public void setUploadedNetwork(boolean finished) {
        putBoolean("network", finished);
    }

    /**
     * 是否已经完成了网络采集
     * @return
     */
    public boolean hasUploadedNetwork() {
        return getBoolean("network");
    }

    public void setUploadedHardware(boolean finished) {
        putBoolean("hardware", finished);
    }

    /**
     * 是否已经完成了硬件采集
     * @return
     */
    public boolean hasUploadedHardware() {
        return getBoolean("hardware");
    }

    public void setUploadedSoftware(boolean finished) {
        putBoolean("software", finished);
    }

    /**
     * 是否已经完成了软件采集
     * @return
     */
    public boolean hasUploadedSoftware() {
        return getBoolean("software");
    }

	/**
	 * 存放String类型键值对
	 * @param key 键
	 * @param value 值
	 */
	public void putString(String key, String value) {
		editor.putString(key, value);
		commit();
	}

	/**
	 * 获取某个key的String值
	 * @param key 键
	 * @return
	 */
	public String getString(String key) {
		return preferences.getString(key, null);
	}

	/**
	 * 存放boolean类型键值对
	 * @param key
	 * @param value
     */
	public void putBoolean(String key, boolean value) {
		editor.putBoolean(key, value);
		commit();
	}

	/**
	 * 获取某个key的布尔值
	 * @param key
     */
	public boolean getBoolean(String key) {
		return preferences.getBoolean(key, false);
	}

	private void commit() {
		editor.commit();
	}
}
