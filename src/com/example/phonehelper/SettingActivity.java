package com.example.phonehelper;

import com.example.phonehelper.framework.BaseActivity;
import com.example.phonehelper.framework.tool.NotificationTool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

/**
 * 设置界面
 * */
public class SettingActivity extends BaseActivity {
	/**
	 * 通知图标
	 * */
	private ToggleButton setting_toggle_item2;
	/**
	 * 开机启动
	 * */
	private ToggleButton setting_toggle_item1;
	/**
	 * 开机启动广播接收器
	 * */
	private BootBroadCastReceiver receiver;

	@Override
	protected void setFullScreen(boolean fullScreen) {
		super.setFullScreen(true);
	}

	@Override
	protected void setLayout() {
		setContentView(R.layout.activity_setting);
		setActionBar("关于", R.drawable.btn_homeasup_default, 0, false);
	}

	@Override
	protected void beforeInit() {
	}

	@Override
	protected void init() {
		setting_toggle_item2 = (ToggleButton) findViewById(R.id.setting_toggle_item2);
		setting_toggle_item1 = (ToggleButton) findViewById(R.id.setting_toggle_item1);
		setting_toggle_item1.setChecked(NotificationTool.isOpenBoot(getApplicationContext()));
		setting_toggle_item2.setChecked(NotificationTool
				.isOpenNotification(getApplicationContext()));
		setting_toggle_item1
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							receiver = new BootBroadCastReceiver();
							// 意图过滤
							IntentFilter filter = new IntentFilter(Intent.ACTION_BOOT_COMPLETED);
							registerReceiver(receiver, filter);
						}else {
							unregisterReceiver(receiver);
						}
					}
				});
		setting_toggle_item2
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							NotificationTool
									.showAppIconNotification(getApplicationContext());
						} else {
							NotificationTool
									.cancelAppIconNotification(getApplicationContext());
						}
					}
				});
	}

	@Override
	protected void afterInit() {

	}

	@Override
	protected void clickEvent(View v) {
		switch (v.getId()) {
		case R.id.action_bar_icon_left:
			finish();
			break;
		case R.id.setting_item8:
			break;
		}
	}

	public void settingItemHit(View v) {

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		NotificationTool.setOpenNotification(getApplicationContext(),setting_toggle_item2.isChecked());
		NotificationTool.setOpenBoot(getApplicationContext(), setting_toggle_item1.isChecked());
	}

	private class BootBroadCastReceiver extends BroadcastReceiver {
		static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(ACTION_BOOT)) {
				Intent startIntent = new Intent(context, HomePageActivity.class);
				startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(startIntent);
			}
		}

	}
}
