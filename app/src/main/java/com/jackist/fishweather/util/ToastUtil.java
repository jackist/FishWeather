package com.jackist.fishweather.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

	/**
	 * 长时间显示一个toast
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showLong(Context context, String msg) {
		show(context, msg, Toast.LENGTH_LONG);
	}

	/**
	 * 短时间显示一个toast
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showShort(Context context, String msg) {
		show(context, msg, Toast.LENGTH_SHORT);
	}

	/**
	 * 自定义事件显示一个toast
	 * 
	 * @param context
	 * @param msg
	 * @param time
	 */
	public static void show(Context context, String msg, int time) {
		if (context != null){
			Toast.makeText(context, msg, time).show();
		}
	}
}
