package com.jackist.fishweather.util;

import android.util.Log;


public class LogUtil {

	public static boolean isDubug = true;

	public static void setDebug(boolean isDebug) {
		LogUtil.isDubug = isDebug;
	}

	public static void d(String tag, String message) {
		if (isDubug&&null!=message) {
			Log.d(tag, message);
		}
	}

	public static void e(String tag, String message) {
		if (isDubug&&null!=message) {
			Log.e(tag, message);
		}
	}

	public static void i(String tag, String message) {
		if (isDubug&&null!=message) {
			Log.i(tag, message);
		}
	}

	public static void w(String tag, String message) {
		if (isDubug&&null!=message) {
			Log.w(tag, message);
		}
	}

	public static void v(String tag, String message) {
		if (isDubug&&null!=message) {
			Log.v(tag, message);
		}
	}

}
