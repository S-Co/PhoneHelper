package com.example.phonehelper.framework.tool;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Build;
import android.text.format.Formatter;
import android.util.DisplayMetrics;

public class PhoneInfoManager {
	/**
	 * 返回手机型号
	 * */
	public static String getPhoneName() {
		return Build.MODEL;
	}

	/**
	 * 返回手机的品牌名字
	 * */
	public static String getPhonebrand() {
		return Build.BRAND;
	}

	/**
	 * 返回系统版本号
	 * */
	public static String getPhoneVersion() {
		return Build.VERSION.RELEASE;
	}

	/**
	 * 返回总内存大小
	 * */
	public static String getMemoryTotal(Context context) {
		long total = MemoryManager.getTotalMemory();
		return Formatter.formatFileSize(context, total);
	}

	/**
	 * 返回已用内存大小
	 * */
	public static String getUsedMemory(Context context) {
		long used = MemoryManager.getTotalMemory()
				- MemoryManager.availableMemory(context);
		return Formatter.formatFileSize(context, used);
	}

	/**
	 * 返回可用内存大小
	 * */
	public static String getMemoryAvalible(Context context) {
		long avalible = MemoryManager.availableMemory(context);
		return Formatter.formatFileSize(context, avalible);
	}

	/**
	 * 获取屏幕分辨率
	 * */
	public static String getScreenPixels(Activity activity) {
		// 显示器的测量参数
		DisplayMetrics metrics = new DisplayMetrics();
		// 通过activity得到窗口管理再得到默认的显示参数
		// 再得到测量参数
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		return width + "*" + height;
	}

	/**
	 * 得到相机的最大分辨率
	 * */
	public static String getCamerPixels() {
		Camera camera = Camera.open();
		Parameters parameters = camera.getParameters();
		List<Size> list = parameters.getSupportedPictureSizes();
		Size size = null;
		for (Size s : list) {
			if (size == null) {
				size = s;
			} else if (size.height * size.width < s.height * s.width) {
				size = s;
			}
		}
		// 打开了相机要关闭
		camera.release();
		camera = null;
		return size.width + "*" + size.height;
	}

	/**
	 * 获取CPU名称
	 * */
	public static String getCPUName2() {
		return Build.HARDWARE;
	}

	/**
	 * 获取手机核心数
	 * */
	public static String getCPUNumber() {
		return "CPU数量：" + Runtime.getRuntime().availableProcessors();
	}

	/**
	 * 获取手机基带版本
	 * */
	public static String getBaseband_Ver() {
		String version = "";
		try {
			@SuppressWarnings("rawtypes")
			Class c1 = Class.forName("android.os.SystemProperties");
			Object invoker = c1.newInstance();
			@SuppressWarnings("unchecked")
			Method m = c1.getMethod("get", new Class[] { String.class,
					String.class });
			Object result = m.invoke(invoker, new Object[] {
					"gsm.version.baseband", "no message" });
			version = (String) result;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return version;
	}

	/**
	 * 判断手机是否ROOT
	 * */
	public static String isRoot() {
		File file = new File("/system/bin/su");
		File file2 = new File("/system/xbin/su");
		try {
			if (!file.exists() && !file2.exists()) {
				return "否";
			}
		} catch (Exception e) {
		}
		return "是";
	}
}
