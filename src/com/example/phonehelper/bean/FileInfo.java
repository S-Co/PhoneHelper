package com.example.phonehelper.bean;

import java.io.File;

public class FileInfo {
	private File file;
	private String iconName;
	private String typeName;
	private boolean isSelect;

	public FileInfo(File file, String iconname, String typename) {
		this.file = file;
		this.iconName = iconname;
		this.typeName = typename;
		isSelect = false;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getIconname() {
		return iconName;
	}

	public void setIconname(String iconname) {
		this.iconName = iconname;
	}

	public String getTypename() {
		return typeName;
	}

	public void setTypename(String typename) {
		this.typeName = typename;
	}

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

}
