package com.example.phonehelper;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.phonehelper.bean.AppInfo;
import com.example.phonehelper.framework.BaseActivity;
import com.example.phonehelper.framework.adapter.SoftManagerAdapter;
import com.example.phonehelper.framework.tool.AppInfoManager;

public class DetailSoftMangerActivtity extends BaseActivity implements
		OnCheckedChangeListener {
	/**
	 * 加载软件的listView
	 * */
	private ListView layout_softmanager_listView;
	private SoftManagerAdapter adapter;
	private ArrayList<AppInfo> list;
	/**
	 * 应用信息管理类
	 * */
	private AppInfoManager appInfoManager;
	/**
	 * 显示状态
	 * */
	private int state;
	/**
	 * 全部勾选的checkBox勾选框
	 * */
	private CheckBox layout_softmanager_checkbox;
	/**
	 * 卸载button
	 * */
	private Button layout_softmanager_uninstall;
	/**
	 * 加载的progressBar
	 * */
	private ProgressBar layout_softmanager_progressbar;
	private UnistallSoftReceiver broadCastReceiver;

	@Override
	protected void setFullScreen(boolean fullScreen) {

		super.setFullScreen(true);
	}

	@Override
	protected void setLayout() {
		setContentView(R.layout.layout_softmanager);
		Bundle bundle = getIntent().getExtras();
		String title = bundle.getString("title");
		state = bundle.getInt("state");
		setActionBar(title, R.drawable.btn_homeasup_default, 0, false);
		appInfoManager = AppInfoManager.getInstance(this);
	}

	@Override
	public void beforeInit() {
		adapter = new SoftManagerAdapter(this);
		broadCastReceiver = new UnistallSoftReceiver();
	}

	@Override
	protected void init() {
		layout_softmanager_listView = (ListView) findViewById(R.id.layout_softmanager_listView);
		layout_softmanager_checkbox = (CheckBox) findViewById(R.id.layout_softmanager_checkbox);
		layout_softmanager_uninstall = (Button) findViewById(R.id.layout_softmanager_uninstall);
		layout_softmanager_progressbar = (ProgressBar) findViewById(R.id.layout_softmanager_progress);
		layout_softmanager_listView.setAdapter(adapter);
	}

	@Override
	protected void afterInit() {
		layout_softmanager_checkbox.setOnCheckedChangeListener(this);
		layout_softmanager_uninstall.setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		initProgress();
		IntentFilter intentFilter = new IntentFilter(
				Intent.ACTION_PACKAGE_REMOVED);
		intentFilter.addDataScheme("package");
		registerReceiver(broadCastReceiver, intentFilter);
	}

	private void initProgress() {
		if (list != null) {
			list.clear();
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (state == 0) {
					list = appInfoManager.getAllApp();
				} else if (state == 1) {
					list = appInfoManager.getSystemApp();
				} else {
					list = appInfoManager.getUserApp();
				}
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						adapter.setList(list);
						layout_softmanager_progressbar.setVisibility(View.GONE);
						layout_softmanager_listView.setVisibility(View.VISIBLE);
					}
				});
			}
		}).start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在销毁页面的时候解除注册
		unregisterReceiver(broadCastReceiver);
	}

	@Override
	protected void clickEvent(View v) {
		switch (v.getId()) {
		case R.id.action_bar_icon_left:
			finish();   
			break;
		case R.id.layout_softmanager_uninstall:
			// 按下卸载按钮 将所选的应用卸载
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).isClean()) {
					
					Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);
					intent.setData(Uri.parse("package:"
							+ list.get(i).getPackgeName()));
					startActivity(intent);
				}
			}
			break;
		}
	}

	/**
	 * 监听checkBox的点击情况并为所有item设置Checked状态
	 * */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		for (AppInfo info : list) {
			info.setClean(isChecked);
			adapter.notifyDataSetChanged();
		}
	}

	private class UnistallSoftReceiver extends BroadcastReceiver {
		public static final String ACTION_APPDEL = "com.example.phonehelper.del";

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Intent.ACTION_PACKAGE_REMOVED)
					|| action.equals(ACTION_APPDEL)) {
				initProgress();
			}
		}

	}
}
