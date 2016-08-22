package com.example.phonehelper.framework.tool;

import com.example.phonehelper.HomePageActivity;
import com.example.phonehelper.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.RemoteViews;

public class NotificationTool {
	private static NotificationManager manager;
	private static Notification notification;

	public static final int NOTIFI_APPICON_ID = 1;
	/**
	 * 是否选择了开机启动
	 * */
	public static boolean isOpenBoot(Context context){
		SharedPreferences preferences = context.getSharedPreferences("bootstart", Context.MODE_PRIVATE);
		return preferences.getBoolean("bootopen", false);
	}
	/**
	 * 把开机启动的选择状态存入数据库
	 * */
	public static void setOpenBoot(Context context, boolean open){
		SharedPreferences preferences = context.getSharedPreferences("bootstart", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean("bootopen", open);
		editor.commit();
	}
	/**
	 * 是否选择了消息通知
	 * */
	public static boolean isOpenNotification(Context context) {
		SharedPreferences preferences = context.getSharedPreferences("notifi", Context.MODE_PRIVATE);
		return preferences.getBoolean("open", false);
	}
/**
 * 把通知栏的选择状态存入数据库
 * */
	public static void setOpenNotification(Context context, boolean open) {
		SharedPreferences preferences = context.getSharedPreferences("notifi", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean("open", open);
		editor.commit();
	}

	public static void cancelAppIconNotification(Context context) {
		if (manager == null) {
			manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		}
		manager.cancel(NOTIFI_APPICON_ID);
	}

	public static void showAppIconNotification(Context context) {
		if (notification == null) {
			notification = new Notification();
		}
		notification.flags = Notification.FLAG_NO_CLEAR;
		notification.icon = R.drawable.ic_launcher;
		notification.tickerText = "通知";
		notification.when = System.currentTimeMillis();
		RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.layout_notification);
		notification.contentView = contentView;
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.setClass(context,HomePageActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		Context contex = context.getApplicationContext();
		PendingIntent contentIntent = PendingIntent.getActivity(contex, 0, intent, 0);
		notification.contentIntent = contentIntent;
		if (manager == null) {
			manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		}
		manager.notify(NOTIFI_APPICON_ID, notification);
	}
}
