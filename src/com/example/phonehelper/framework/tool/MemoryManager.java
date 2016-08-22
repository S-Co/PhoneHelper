package com.example.phonehelper.framework.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.Service;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

public class MemoryManager {
	/**
	 * 内存管理的静态方法
	 * 
	 * @return 返回总内存大小 单位是B 如果返回0表示出现错误了
	 */
	public static long getTotalMemory() {
		try {
			File file = new File("/proc/meminfo");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String content = br.readLine();
			br.close();
			return Long.parseLong(content.split(":")[1].split("k")[0].trim()) << 10;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 内存管理的静态方法
	 * 
	 * @param context
	 *            上下文内容
	 * @return 返回系统可用内存空间 单位是B
	 */
	public static long availableMemory(Context context) {
		// 得到内存信息的对象
		MemoryInfo info = new MemoryInfo();
		// 获取系统级的服务
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Service.ACTIVITY_SERVICE);
		// 给内存信息赋值
		manager.getMemoryInfo(info);
		// 返回可用内存
		return info.availMem;
	}

	/**
	 * SDCARD是否存在
	 * */
	public static boolean externalMemoryAvailable() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取手机内部剩余内存
	 * */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static long getInAvailbleMemory() {
		File file = Environment.getExternalStorageDirectory();
		StatFs statFs = new StatFs(file.getPath());
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			return statFs.getAvailableBytes();
		} else {
			long bolcks = statFs.getAvailableBlocks();
			long size = statFs.getBlockSize();
			return bolcks * size;
		}

	}

	/**
	 * 获取手机内部总的存储空间
	 * */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static long getInTotalMemory() {
		File file = Environment.getExternalStorageDirectory();
		StatFs statFs = new StatFs(file.getPath());
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			return statFs.getTotalBytes();
		} else {
			long bolcks = statFs.getBlockCount();
			long size = statFs.getBlockSize();
			return bolcks * size;
		}
	}

	/**
	 * 获取SDCARD剩余存储空间
	 * */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static long getOutAvaliableMemory() {
		if (getOutSDCardPath() == null) {
			return 0;
		} else {
			StatFs stat = new StatFs(getOutSDCardPath());
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
				return stat.getAvailableBytes();
			} else {
				long bolcks = stat.getAvailableBlocks();
				long size = stat.getBlockSize();
				return bolcks * size;
			}
		}
	}

	/**
	 * 获取SDCARD总的存储空间
	 * */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static long getOutTotalMemory() {
		if (getOutSDCardPath() == null) {
			return 0;
		} else {
			StatFs stat = new StatFs(getOutSDCardPath());
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
				return stat.getTotalBytes();
			} else {
				long bolcks = stat.getBlockCount();
				long size = stat.getBlockSize();
				return bolcks * size;
			}
		}
	}

	/**
	 * 返回外置SD卡路径
	 * */
	public static String getOutSDCardPath() {
		Map<String, String> map = System.getenv();
		// 如果系统环境包含第二存储区域
		if (map.containsKey("SECONDARY_STORAGE")) {
			// 根据这个key获取到一个完整路径
			String paths = map.get("SECONDARY_STORAGE");
			String path[] = paths.split(":");
			if (path == null || path.length <= 0) {
				return null;
			}
			return path[0];
		}
		return null;
	}

	/**
	 * 返回内置SD卡路径
	 * */
	public static String getInSDCardPath() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		}
		return null;
	}
}
