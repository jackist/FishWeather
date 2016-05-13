package com.jackist.fishweather.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * SharedPreferences 工具类</br>
 * 
 * 修改自：{@link http://blog.csdn.net/lmj623565791/article/details/38965311 }
 * 
 * @author jackist
 */
public class SPUtil {

	/**
	 * 保存在手机里面的默认文件名
	 */
	public static final String FILE_NAME = "chanbo";

	/**
	 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void put(Context context, String key, Object value) {

		put(context, FILE_NAME, key, value);
	}

	/**
	 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
	 * 
	 * @param context
	 * @param fileName
	 * @param key
	 * @param value
	 */
	public static void put(Context context, String fileName, String key,
			Object value) {

		SharedPreferences sp = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();

		if (value instanceof String) {
			editor.putString(key, (String) value);
		} else if (value instanceof Integer) {
			editor.putInt(key, (Integer) value);
		} else if (value instanceof Boolean) {
			editor.putBoolean(key, (Boolean) value);
		} else if (value instanceof Float) {
			editor.putFloat(key, (Float) value);
		} else if (value instanceof Long) {
			editor.putLong(key, (Long) value);
		} else {
			editor.putString(key, value.toString());
		}

		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
	 * 
	 * @param context
	 * @param key
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public static Object get(Context context, String key, Object defaultValue) {
		return get(context, FILE_NAME, key, defaultValue);
	}

	/**
	 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
	 * 
	 * @param context
	 * @param fileName
	 *            表名
	 * @param key
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public static Object get(Context context, String fileName, String key,
			Object defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);

		if (defaultValue instanceof String) {
			return sp.getString(key, (String) defaultValue);
		} else if (defaultValue instanceof Integer) {
			return sp.getInt(key, (Integer) defaultValue);
		} else if (defaultValue instanceof Boolean) {
			return sp.getBoolean(key, (Boolean) defaultValue);
		} else if (defaultValue instanceof Float) {
			return sp.getFloat(key, (Float) defaultValue);
		} else if (defaultValue instanceof Long) {
			return sp.getLong(key, (Long) defaultValue);
		}

		return null;
	}

	/**
	 * 移除某个key值已经对应的值
	 * 
	 * @param context
	 * @param key
	 */
	public static void remove(Context context, String key) {
		remove(context, FILE_NAME, key);
	}

	/**
	 * 移除某个文件中某个key值已经对应的值
	 * 
	 * @param fileName
	 * @param context
	 * @param key
	 */
	public static void remove(Context context, String fileName, String key) {
		SharedPreferences sp = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.remove(key);
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 清除所有数据
	 * 
	 * @param context
	 */
	public static void clear(Context context) {
		clear(context, FILE_NAME);
	}

	/**
	 * 清除某个文件中的所有数据
	 * 
	 * @param fileName
	 * @param context
	 */
	public static void clear(Context context, String fileName) {
		SharedPreferences sp = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 查询默认表名中某个key是否已经存在
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean contains(Context context, String key) {

		return contains(context, FILE_NAME, key);
	}

	/**
	 * 查询某个表中的某个key是否已经存在
	 * 
	 * @param context
	 * @param fileName
	 * @param key
	 * @return
	 */
	public static boolean contains(Context context, String fileName, String key) {
		SharedPreferences sp = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		return sp.contains(key);
	}

	/**
	 * 返回所有的键值对
	 * 
	 * @param context
	 * @return
	 */
	public static Map<String, ?> getAll(Context context) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		return sp.getAll();
	}

	/**
	 * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
	 * 
	 * 此类所有的commit操作使用了SharedPreferencesCompat.apply进行了替代，目的是尽可能的使用apply代替commit
	 * 。 因为commit方法是同步的，并且我们很多时候的commit操作都是UI线程中
	 * ，毕竟是IO操作，尽可能异步；所以我们使用apply进行替代，apply异步的进行写入；
	 * 
	 * @author zhy
	 * 
	 */
	private static class SharedPreferencesCompat {
		private static final Method sApplyMethod = findApplyMethod();

		/**
		 * 反射查找apply的方法
		 * 
		 * @return
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private static Method findApplyMethod() {
			try {
				Class clz = SharedPreferences.Editor.class;
				return clz.getMethod("apply");
			} catch (NoSuchMethodException e) {
			}

			return null;
		}

		/**
		 * 如果找到则使用apply执行，否则使用commit
		 * 
		 * @param editor
		 */
		public static void apply(SharedPreferences.Editor editor) {
			try {
				if (sApplyMethod != null) {
					sApplyMethod.invoke(editor);
					return;
				}
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
			editor.commit();
		}
	}

}
