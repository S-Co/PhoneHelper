package com.example.phonehelper.framework.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.example.phonehelper.bean.ClassInfo;
import com.example.phonehelper.bean.TableInfo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class SQLiteHelper {
	/**
	 * 包名
	 * */
	private static final String PACKAGE_NAME = "com.example.phonehelper";
	/**
	 * 数据库文件名
	 * */
	private static final String FILE_NAME = "commonnum.db";
	/**
	 * 文件路径
	 * */
	private static final String FILE_PATH = "/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ PACKAGE_NAME;

	/**
	 * 将数据写入包中的方法
	 * */
	public static void saveDate(Context context) {
		try {
			File file = new File(FILE_PATH + "/" + FILE_NAME);
			if (file.exists()) {
				return;
			}
			// 利用resources获取assets文件夹下源文件的文件的流
			InputStream is = context.getResources().getAssets()
					.open("db/commonnum.db");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取电话簿类型
	 * 
	 * @return 类型集合
	 * */
	public static ArrayList<ClassInfo> getClassInfos() {
		ArrayList<ClassInfo> list = new ArrayList<ClassInfo>();
		// 开启数据库
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(FILE_PATH
				+ "/" + FILE_NAME, null);
		String title = "select * from classlist";
		// 获取游标
		Cursor cursor = database.rawQuery(title, null);
		// 当游标能往下读的时候
		while (cursor.moveToNext()) {
			// 从name字段中获取到名字
			String name = cursor.getString(cursor.getColumnIndex("name"));
			// 从index字段中获取到角标
			int index = cursor.getInt(cursor.getColumnIndex("idx"));
			ClassInfo info = new ClassInfo(name, index);
			// 向集合中添加name
			list.add(info);
		}
		return list;
	}

	/**
	 * 获取详细信息
	 * @return 类型集合
	 * */
	public static ArrayList<TableInfo> getTableInfos(int idx) {
		ArrayList<TableInfo> list = new ArrayList<TableInfo>();
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(FILE_PATH
				+ "/" + FILE_NAME, null);
		String selcet = "select * from table" + idx;
		Cursor cursor = database.rawQuery(selcet, null);
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("name"));
			long number = cursor.getLong(cursor.getColumnIndex("number"));
			list.add(new TableInfo(name, number));
		}
		return list;
	}
}
