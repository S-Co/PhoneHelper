package com.example.phonehelper.framework.tool;

import java.io.File;
import java.util.ArrayList;

import com.example.phonehelper.bean.FileInfo;

public class FileManager {
	/**
	 * 各类型的列表以及大小
	 * */
	private ArrayList<FileInfo> allFileList = new ArrayList<FileInfo>();
	private long allFileSize;
	private ArrayList<FileInfo> txtFileList = new ArrayList<FileInfo>();
	private long txtFileSize;
	private ArrayList<FileInfo> videoFileList = new ArrayList<FileInfo>();
	private long videoFileSize;
	private ArrayList<FileInfo> audioFileList = new ArrayList<FileInfo>();
	private long audioFileSize;
	private ArrayList<FileInfo> imageFileList = new ArrayList<FileInfo>();
	private long imageFileSize;
	private ArrayList<FileInfo> zipFileList = new ArrayList<FileInfo>();
	private long zipFileSize;
	private ArrayList<FileInfo> appFileList = new ArrayList<FileInfo>();
	private long appFileSize;
	/** 是否停止查找 */
	private boolean isStop;
	/** 内置存储卡文件夹 */
	private static File inSDCardFile;
	/** 外置存储卡文件夹 */
	private static File outSDCardFile;
	static {
		if (MemoryManager.getOutSDCardPath() != null) {
			outSDCardFile = null;
			outSDCardFile = new File(MemoryManager.getOutSDCardPath());
		}
		if (MemoryManager.getInSDCardPath() != null) {
			inSDCardFile = null;
			inSDCardFile = new File(MemoryManager.getInSDCardPath());
		}
	}

	/** 开始查找文件(PS:注意权限) */
	public void searchFiles() {
		if (allFileList == null || allFileSize == 0) {
			init();
			searchFile(inSDCardFile, false);
			searchFile(outSDCardFile, true);
		} else {
			callBackEnd(true);
		}
	}

	public ArrayList<FileInfo> getAllFileInfo() {
		return allFileList;
	}

	public void setAllFileInfo(ArrayList<FileInfo> allFileInfo) {
		this.allFileList = allFileInfo;
	}

	public long getAllFileSize() {
		return allFileSize;
	}

	public void setAllFileSize(long allFileSize) {
		this.allFileSize = allFileSize;
	}

	public ArrayList<FileInfo> getTxtFileInfo() {
		return txtFileList;
	}

	public void setTxtFileInfo(ArrayList<FileInfo> txtFileInfo) {
		this.txtFileList = txtFileInfo;
	}

	public long getTxtFileSize() {
		return txtFileSize;
	}

	public void setTxtFileSize(long txtFileSize) {
		this.txtFileSize = txtFileSize;
	}

	public ArrayList<FileInfo> getVideoFileInfo() {
		return videoFileList;
	}

	public void setVideoFileInfo(ArrayList<FileInfo> videoFileInfo) {
		this.videoFileList = videoFileInfo;
	}

	public long getVideoFileSize() {
		return videoFileSize;
	}

	public void setVideoFileSize(long videoFileSize) {
		this.videoFileSize = videoFileSize;
	}

	public ArrayList<FileInfo> getAudioFileInfo() {
		return audioFileList;
	}

	public void setAudioFileInfo(ArrayList<FileInfo> audioFileInfo) {
		this.audioFileList = audioFileInfo;
	}

	public long getAudioFileSize() {
		return audioFileSize;
	}

	public void setAudioFileSize(long audioFileSize) {
		this.audioFileSize = audioFileSize;
	}

	public ArrayList<FileInfo> getImageFileInfo() {
		return imageFileList;
	}

	public void setImageFileInfo(ArrayList<FileInfo> imageFileInfo) {
		this.imageFileList = imageFileInfo;
	}

	public long getImageFileSize() {
		return imageFileSize;
	}

	public void setImageFileSize(long imageFileSize) {
		this.imageFileSize = imageFileSize;
	}

	public ArrayList<FileInfo> getZipFileInfo() {
		return zipFileList;
	}

	public void setZipFileInfo(ArrayList<FileInfo> zipFileInfo) {
		this.zipFileList = zipFileInfo;
	}

	public long getZipFileSize() {
		return zipFileSize;
	}

	public void setZipFileSize(long zipFileSize) {
		this.zipFileSize = zipFileSize;
	}

	public ArrayList<FileInfo> getAppFileInfo() {
		return appFileList;
	}

	public void setAppFileInfo(ArrayList<FileInfo> appFileInfo) {
		this.appFileList = appFileInfo;
	}

	public long getAppFileSize() {
		return appFileSize;
	}

	public void setAppFileSize(long appFileSize) {
		this.appFileSize = appFileSize;
	}

	/**
	 * 初始化数据的方法
	 * */
	public void init() {
		allFileList.clear();
		appFileList.clear();
		audioFileList.clear();
		imageFileList.clear();
		zipFileList.clear();
		videoFileList.clear();
		txtFileList.clear();

		allFileSize = 0;
		appFileSize = 0;
		audioFileSize = 0;
		imageFileSize = 0;
		zipFileSize = 0;
		videoFileSize = 0;
		txtFileSize = 0;
		isStop = false;
	}

	// 单例
	private static FileManager instance;

	private FileManager() {
	}

	public static FileManager getInstance() {
		if (instance == null) {
			instance = new FileManager();
		}
		return instance;
	}

	/**
	 * 查找的方法
	 * 
	 * @param file
	 *            文件
	 * @param isFirst
	 *            是否第一次查找
	 * */
	public void searchFile(File file, boolean isFirst) {
		// 如果停止查找 结束方法
		if (isStop) {
			if (isFirst) {
				callBackEnd(true);
				return;
			}
		}
		// 去除无效文件
		if (file == null || !file.exists() || !file.canRead()) {
			if (isFirst) {
				callBackEnd(true);
			}
			return;
		}
		// 如果不是文件夹
		if (!file.isDirectory()) {
			if (file.length() <= 0) {
				return;
			}
			if (file.getName().lastIndexOf('.') == -1) {
				return;
			}
			String[] iconTypeName = FileTypeTool.getFileIconAndTypeName(file);
			/** 图标名称 */
			String iconName = iconTypeName[0];
			/** 文件类型名 */
			String typeName = iconTypeName[1];
			FileInfo info = new FileInfo(file, iconName, typeName);
			long length = file.length();
			allFileList.add(info);
			allFileSize += length;
			if (typeName.equals(FileTypeTool.TYPE_APK)) {
				appFileList.add(info);
				appFileSize += length;
			} else if (typeName.equals(FileTypeTool.TYPE_AUDIO)) {
				audioFileList.add(info);
				audioFileSize += length;
			} else if (typeName.equals(FileTypeTool.TYPE_IMAGE)) {
				imageFileList.add(info);
				imageFileSize += length;
			} else if (typeName.equals(FileTypeTool.TYPE_TXT)) {
				txtFileList.add(info);
				txtFileSize += length;
			} else if (typeName.equals(FileTypeTool.TYPE_VIDEO)) {
				videoFileList.add(info);
				videoFileSize += length;
			} else if (typeName.equals(FileTypeTool.TYPE_ZIP)) {
				zipFileList.add(info);
				zipFileSize += length;
			}
			/** 回调提醒 */
			callBackSearch(typeName);
			return;
		}
		// 如果是文件夹 进行递归查找
		File[] files = file.listFiles();
		if (files == null || files.length <= 0) {
			return;
		}
		for (int i = 0; i < files.length; i++) {
			File temp = files[i];
			searchFile(temp, false);
		}
		if (isStop) {
			callBackEnd(false);
		}
	}

	/**
	 * 返回文件大小
	 * */
	public static long getFileSize(File file) {
		long size = 0;
		if (!file.isDirectory()) {
			// 如果不是文件,则返回文件大小
			return file.length();
		}
		File files[] = file.listFiles(); // 如果是文件夹,则继续进行遍历.
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				size = size + getFileSize(files[i]);
			} else {
				size = size + files[i].length();
			}
		}
		return size;
	}

	/**
	 * 删除文件的方法
	 * */
	public static void deleteFile(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files != null && files.length > 0) {
				for (int i = 0; i < files.length; ++i) {
					deleteFile(files[i]);
				}
			}
		}
		file.delete();
	}

	/**
	 * 回掉接口 查找到文件以后的回调接口
	 * */
	public interface FileSearchListener {
		/** 当查找到某个文件的时候 */
		void searching(String typeName);

		/** 是否因为异常原因结束查找 */
		void end(boolean isException);
	}

	private FileSearchListener listener;

	public void setFileSearchListener(FileSearchListener listener) {
		this.listener = listener;
	}

	private void callBackSearch(String typeName) {
		if (listener != null) {
			listener.searching(typeName);
		}
	}

	private void callBackEnd(boolean isException) {
		if (listener != null) {
			listener.end(isException);
		}
	}

	public boolean isStop() {
		return isStop;
	}

	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}
}
