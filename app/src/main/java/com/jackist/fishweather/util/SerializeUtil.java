package com.jackist.fishweather.util;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.UnsupportedEncodingException;

/**
 * 序列化工具类
 * 
 * @author jackist
 */
public class SerializeUtil {
	private final static String TAG = "序列化工具类";

	public static String serialize(Object object) {
		ByteArrayOutputStream byteStream = null;
		ObjectOutputStream stream = null;
		try {
			byteStream = new ByteArrayOutputStream();
			stream = new ObjectOutputStream(byteStream);
			stream.writeObject(object);
			return new String(byteStream.toByteArray(), "ISO-8859-1");
		} catch (IOException e) {
			LogUtil.e(TAG, "序列化失败:" + e.getMessage());
		} finally {
			try {
				stream.close();
				byteStream.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Object antiSerialize(String string) {
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			bis = new ByteArrayInputStream(string.getBytes("ISO-8859-1"));
			ois = new ObjectInputStream(bis);
			return ois.readObject();
		} catch (UnsupportedEncodingException e) {
			LogUtil.e(TAG, "反序列化失败：" + e.getMessage());

		} catch (OptionalDataException e) {
			LogUtil.e(TAG, "反序列化失败：" + e.getMessage());

		} catch (ClassNotFoundException e) {
			LogUtil.e(TAG, "反序列化失败：" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
				bis.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	

	/*
	 * 反序列化
	 */
	public static Object StringToObject(String objectStr) {

		Object object = null;

		ByteArrayInputStream byteArrayInputStream = null;
		ObjectInputStream objectInputStream = null;
		try {
			String redStr = java.net.URLDecoder.decode(objectStr, "UTF-8");
			byteArrayInputStream = new ByteArrayInputStream(
					redStr.getBytes("ISO-8859-1"));
			objectInputStream = new ObjectInputStream(byteArrayInputStream);
			object = objectInputStream.readObject();
		} catch (Exception ex) {
			Log.e("SerializeUtil", "反序列化出错");
		} finally {
			try {
				objectInputStream.close();
				byteArrayInputStream.close();
			} catch (IOException e) {
				Log.e("SerializeUtil", "反序列化流关闭出错");
			} catch (Exception e) {
				Log.e("SerializeUtil", "反序列化流关闭出错");
			}
		}
		return object;
	}
	
	
	/*
	 * 对象序列化->String
	 */
	public static String ObjectToString(Object object) {
		String serStr = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = null;
		;
		try {
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(object);
			serStr = byteArrayOutputStream.toString("ISO-8859-1");
			serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
		} catch (Exception ex) {
			Log.e("SerializeUtil", "序列化出错");
		} finally {
			try {
				objectOutputStream.close();
				byteArrayOutputStream.close();
			} catch (IOException e) {
				Log.e("SerializeUtil", "序列化流关闭出错");
			} catch (Exception e) {
				Log.e("SerializeUtil", "序列化流关闭出错");
			}
		}

		return serStr;
	}
}
