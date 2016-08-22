package com.example.phonehelper.framework.sf;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 单例的小数据库助手
 */
public class SheredFreHelper {
	/**
	 * 助手对象
	 * */
	private static SheredFreHelper instance;
	/**
	 * 小数据库对象
	 * */
	private static SharedPreferences sharedPreferences;
	/**
	 * 小数据库的名字
	 * */
	private static final String SHARED_PRE_NAME = "shared_pre";

	private SheredFreHelper(Context context) {
		// 参数一是数据库名，参数二是模式
		sharedPreferences = context.getSharedPreferences(SHARED_PRE_NAME,
				Context.MODE_PRIVATE);
	}

	/**
	 * 获取到助手对象的静态方法
	 * 
	 * @param context
	 *            上下文内容
	 */
	public static SheredFreHelper getInstance(Context con) {
		if (instance == null) {
			instance = new SheredFreHelper(con);
		}
		return instance;
	}

	/**
	 * 填入已观看导航页面的数据
	 * */
	public void putDate() {
		// 从小数据库中获取到编辑器
		Editor editor = sharedPreferences.edit();
		// 向编辑器中填入数据并且提交
		editor.putBoolean("iswatched", true);
		editor.commit();
	}

	/**
	 * 从小数据库中获取是否已经观看过导航页面
	 * */
	public boolean getDate() {
		return sharedPreferences.getBoolean("iswatched", false);
	}
}
