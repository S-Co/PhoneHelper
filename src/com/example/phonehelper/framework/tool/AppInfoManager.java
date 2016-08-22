package com.example.phonehelper.framework.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.phonehelper.bean.AppInfo;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Service;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Debug;

public class AppInfoManager {
	private static AppInfoManager instance;
	private ActivityManager activityManager;
	private PackageManager packageManager;
	// 状态常量:显示用户进程
	public static final int SHOW_USER_PROCESS = 0;
	// 状态常量:显示系统进程
	public static final int SHOW_SYSTEM_PROCESS = 1;

	private AppInfoManager(Context context) {
		activityManager = (ActivityManager) context
				.getSystemService(Service.ACTIVITY_SERVICE);
		packageManager = context.getPackageManager();
	}

	public static AppInfoManager getInstance(Context context) {
		if (instance == null) {
			instance = new AppInfoManager(context);
		}
		return instance;
	}

	/**
	 * 杀死进程
	 * */
	public void killAllProgress() {
		// 列出当前进程并加入到list中
		List<RunningAppProcessInfo> appProcessInfos = activityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo processInfo : appProcessInfos) {
			// 判断当前进程重要性是否低于系统必须服务
			// importance 该进程的重要程度 分为几个级别，数值越低就越重要。
			if (processInfo.importance >= RunningAppProcessInfo.IMPORTANCE_SERVICE) {
				String packageName = processInfo.processName;
				try {
					// 通过包管理者获取应用的信息 参数一应用的包名 参数二是flags
					ApplicationInfo applicationInfo = packageManager
							.getApplicationInfo(
									packageName,
									PackageManager.GET_META_DATA
											| PackageManager.GET_SHARED_LIBRARY_FILES
											| PackageManager.GET_UNINSTALLED_PACKAGES);
					// 判断这个应用是否属于系统级应用
					if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
						// 杀死后台程序
						activityManager.killBackgroundProcesses(packageName);
					}
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 返回当前运行的应用集合
	 * */
	@SuppressLint("UseSparseArrays")
	public HashMap<Integer, ArrayList<AppInfo>> getRunningAppInfos() {
		HashMap<Integer, ArrayList<AppInfo>> map = new HashMap<Integer, ArrayList<AppInfo>>();
		ArrayList<AppInfo> user = new ArrayList<AppInfo>();
		ArrayList<AppInfo> sys = new ArrayList<AppInfo>();
		List<RunningAppProcessInfo> appProcessInfos = activityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo processInfo : appProcessInfos) {
			// 应用名
			String packageName = processInfo.processName;
			// 应用的pid
			int pid = processInfo.pid;
			// 应用的进程级别
			int importance = processInfo.importance;
			// 表示该进程是用户应用
			if (importance >= RunningAppProcessInfo.IMPORTANCE_SERVICE) {
				String lableName;
				Drawable icon;
				long size;
				// 通过pid得到应用占用内存
				Debug.MemoryInfo[] memoryInfos = activityManager
						.getProcessMemoryInfo(new int[] { pid });
				size = (memoryInfos[0].getTotalPrivateDirty()) * 1024;
				try {
					icon = packageManager.getApplicationIcon(packageName);
					ApplicationInfo applicationInfo = packageManager
							.getApplicationInfo(
									packageName,
									PackageManager.GET_META_DATA
											| PackageManager.GET_SHARED_LIBRARY_FILES
											| PackageManager.GET_UNINSTALLED_PACKAGES);
					lableName = packageManager.getApplicationLabel(
							applicationInfo).toString();
					AppInfo info = new AppInfo(lableName, icon, size, pid);
					info.setPackgeName(packageName);
					// 系统进程
					if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
						info.setSys(true);
						info.setClean(false);
						sys.add(info);
					}
					// 用户进程(默认选中)
					else {
						info.setSys(false);
						info.setClean(true);
						user.add(info);
					}
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		map.put(SHOW_USER_PROCESS, user);
		map.put(SHOW_SYSTEM_PROCESS, sys);
		return map;
	}

	/**
	 * 杀死指定进程
	 * */
	public void killProgress(ArrayList<AppInfo> info) {
		for (int i = 0; i < info.size(); i++) {
			// 如果当前应用已经被标注为应该清理
			if (info.get(i).isClean()) {
				// 杀死后台进程
				activityManager.killBackgroundProcesses(info.get(i)
						.getPackgeName());
				// 从列表中移除数据
				info.remove(info.get(i));
				i--;
			}
		}
	}

	/**
	 * 返回所有已经安装的应用的集合
	 * */
	public ArrayList<AppInfo> getAllApp() {
		ArrayList<AppInfo> list = new ArrayList<AppInfo>();
		List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
		String lableName;
		Drawable icon;
		String packageName;
		int pid;
		try {
			for (PackageInfo packageInfo : packageInfos) {
				packageName = packageInfo.packageName;
				icon = packageManager.getApplicationIcon(packageName);
				ApplicationInfo applicationInfo = packageManager
						.getApplicationInfo(
								packageName,
								PackageManager.GET_META_DATA
										| PackageManager.GET_SHARED_LIBRARY_FILES
										| PackageManager.GET_UNINSTALLED_PACKAGES);
				lableName = packageManager.getApplicationLabel(applicationInfo)
						.toString();
				pid = packageInfo.versionCode;
				AppInfo info = new AppInfo(lableName, icon, 0, pid);
				info.setPackgeName(packageName);
				list.add(info);
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 返回系统软件的集合
	 * */
	public ArrayList<AppInfo> getSystemApp() {
		ArrayList<AppInfo> list = new ArrayList<AppInfo>();
		List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
		String lableName;
		Drawable icon;
		String packageName;
		int pid;
		try {
			for (PackageInfo packageInfo : packageInfos) {
				packageName = packageInfo.packageName;
				icon = packageManager.getApplicationIcon(packageName);
				ApplicationInfo applicationInfo = packageManager
						.getApplicationInfo(
								packageName,
								PackageManager.GET_META_DATA
										| PackageManager.GET_SHARED_LIBRARY_FILES
										| PackageManager.GET_UNINSTALLED_PACKAGES);
				lableName = packageManager.getApplicationLabel(applicationInfo)
						.toString();
				pid = packageInfo.versionCode;
				AppInfo info = new AppInfo(lableName, icon, 0, pid);
				info.setPackgeName(packageName);
				// 判断是否系统软件
				if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
					list.add(info);
				}
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 返回用户软件的集合
	 * */
	public ArrayList<AppInfo> getUserApp() {
		ArrayList<AppInfo> list = new ArrayList<AppInfo>();
		List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
		String lableName;
		Drawable icon;
		String packageName;
		int pid;
		try {
			for (PackageInfo packageInfo : packageInfos) {
				packageName = packageInfo.packageName;
				icon = packageManager.getApplicationIcon(packageName);
				ApplicationInfo applicationInfo = packageManager
						.getApplicationInfo(
								packageName,
								PackageManager.GET_META_DATA
										| PackageManager.GET_SHARED_LIBRARY_FILES
										| PackageManager.GET_UNINSTALLED_PACKAGES);
				lableName = packageManager.getApplicationLabel(applicationInfo)
						.toString();
				pid = packageInfo.versionCode;
				AppInfo info = new AppInfo(lableName, icon, 0, pid);
				info.setPackgeName(packageName);
				if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
					list.add(info);
				}
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}
}
