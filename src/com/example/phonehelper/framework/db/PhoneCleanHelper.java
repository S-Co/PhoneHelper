package com.example.phonehelper.framework.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.example.phonehelper.R;
import com.example.phonehelper.bean.RubbishInfo;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Environment;

public class PhoneCleanHelper {
	/**
	 * 包名
	 * */
	private static final String PACKAGE_NAME = "com.example.phonehelper";
	/**
	 * 数据库文件名
	 * */
	private static final String FILE_NAME = "clearpath.db";
	/**
	 * 文件路径
	 * */
	private static final String FILE_PATH = "/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ PACKAGE_NAME;
	private static ArrayList<RubbishInfo> list;
	/**
	 * 将应用信息写入包中的方法
	 * */
	public static void saveDate(Context context) {
		try {
			File file = new File(FILE_PATH + "/" + FILE_NAME);
			if (file.exists()) {
				return;
			}
			// 利用resources获取assets文件夹下源文件的文件的流
			InputStream is = context.getResources().getAssets()
					.open("db/clearpath.db");
			FileOutputStream fos = new FileOutputStream(file);
			byte[] arr = new byte[1024];
			int len;
			while ((len = is.read(arr)) != -1) {
				fos.write(arr, 0, len);
				fos.flush();
			}
			fos.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取所有应用类型
	 * 
	 * @return 类型集合
	 * */
	public static ArrayList<RubbishInfo> readSoftdetailTable() {
		list = new ArrayList<RubbishInfo>();
		// 开启数据库
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(FILE_PATH
				+ "/" + FILE_NAME, null);
		String title = "select * from softdetail";
		// 获取游标
		Cursor cursor = database.rawQuery(title, null);
		// 当游标能往下读的时候
		while (cursor.moveToNext()) {
			// 从name字段中获取到名字
			String softChinesename = cursor.getString(cursor
					.getColumnIndex("softChinesename"));
			// 从index字段中获取到角标
			String softEnglishname = cursor.getString(cursor
					.getColumnIndex("softEnglishname"));

			String apkname = cursor.getString(cursor.getColumnIndex("apkname"));
			String filepath = Environment.getExternalStorageDirectory()
					.getPath()
					+ cursor.getString(cursor.getColumnIndex("filepath"));
			RubbishInfo info = new RubbishInfo(softChinesename,
					softEnglishname, apkname, filepath);
			// 向集合中添加name
			list.add(info);
		}
		return list;
	}

	/**
	 * 获得手机已经安装的和数据库中对应的App的信息集合
	 * */
	public static ArrayList<RubbishInfo> getPhoneRubbishfile(Context context) {
		if (list == null) {
			list = readSoftdetailTable();
		}
		ArrayList<RubbishInfo> phontSoftdetailInfos = new ArrayList<RubbishInfo>();
		for (RubbishInfo rubbishInfo : list) {
			File file = new File(rubbishInfo.getFilepath());
			if (file.exists()) {
				Drawable icon = null;
				try {
					icon = context.getPackageManager().getApplicationIcon(
							rubbishInfo.getApkname());
				} catch (NameNotFoundException e) {
					icon = context.getResources().getDrawable(
							R.drawable.ic_launcher);
				}
				rubbishInfo.setSoftIcon(icon);
				phontSoftdetailInfos.add(rubbishInfo);
			}
		}
		return phontSoftdetailInfos;
	}

}
