package com.example.phonehelper.bean;

import android.graphics.drawable.Drawable;

public class AppInfo {
	/**
	 * 应用程序包名
	 * */
	private String packgeName;
	/**
	 * 应用程序图标
	 * */
	private Drawable icon;
	/**
	 * 应用程序id
	 * */
	private int pid;
	/**
	 * 应用程序名字
	 * */
	private String lableName;
	/**
	 * 应用程序占用内存大小
	 * */
	private long size;
	/**
	 * 应用程序是否系统应用
	 * */
	private boolean isSys;
	/**
	 * 应用程序要不要别清理
	 * */
	private boolean isClean;

	public AppInfo(String lableName, Drawable icon, long size, int pid) {
		this.icon = icon;
		this.pid = pid;
		this.lableName = lableName;
		this.size = size;
		this.isSys = false;
		this.isClean = false;
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getLableName() {
		return lableName;
	}

	public void setLableName(String lableName) {
		this.lableName = lableName;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public boolean isSys() {
		return isSys;
	}

	public void setSys(boolean isSys) {
		this.isSys = isSys;
	}

	public boolean isClean() {
		return isClean;
	}

	public void setClean(boolean isClean) {
		this.isClean = isClean;
	}

	public String getPackgeName() {
		return packgeName;
	}

	public void setPackgeName(String packgeName) {
		this.packgeName = packgeName;
	}

}
