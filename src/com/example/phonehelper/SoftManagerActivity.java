package com.example.phonehelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonehelper.framework.BaseActivity;
import com.example.phonehelper.framework.tool.MemoryManager;
import com.example.phonehelper.framework.view.SoftManagerArcView;

public class SoftManagerActivity extends BaseActivity {
	/**
	 * 绘制内存的自定义圆
	 * */
	private SoftManagerArcView softManagerArcView;
	/**
	 * 显示内置存储状况的progressBar
	 * */
	private ProgressBar softmanager_inside_progress;
	/**
	 * 显示外置存储状况的progressBar
	 * */
	private ProgressBar softmanager_outside_progress;
	/**
	 * 显示手机内置空间存储状况
	 * */
	private TextView inside_memory;

	/**
	 * 显示外置SD卡空间存储状况
	 * */
	private TextView outside_memory;
	/**
	 * 所有软件
	 * */
	private TextView soft_manager_all_soft;
	/**
	 * 系统软件
	 * */
	private TextView soft_manager_sys_soft;
	/**
	 * 用户软件
	 * */
	private TextView soft_manager_user_soft;
	/**
	 * SD卡总共内存
	 * */
	private long outTotal_memory;
	/**
	 * SD卡可用内存
	 * */
	private long outAvliable_memory;
	/**
	 * 手机内置总内存
	 * */
	private long inTotal_memory;
	/**
	 * 手机可用内存
	 * */
	private long inAvliable_memory;
	private int inper, outper, arcper;
	/**
	 * 状态常量，用于判断显示什么类型的软件
	 * */
	private static final int ALL = 0;
	private static final int SYS = 1;
	private static final int USUER = 2;
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Toast.makeText(SoftManagerActivity.this, "无存储卡",
						Toast.LENGTH_SHORT).show();
			}
		};
	};

	@Override
	protected void setFullScreen(boolean fullScreen) {
		super.setFullScreen(true);
	}

	@Override
	protected void setLayout() {
		setContentView(R.layout.activity_softmanager);
		setActionBar("软件管理", R.drawable.btn_homeasup_default, 0, false);
	}

	@Override
	protected void beforeInit() {
		inAvliable_memory = MemoryManager.getInAvailbleMemory();
		inTotal_memory = MemoryManager.getInTotalMemory();
		outAvliable_memory = MemoryManager.getOutAvaliableMemory();
		outTotal_memory = MemoryManager.getOutTotalMemory();
		inper = (int) ((inTotal_memory - inAvliable_memory) * 100 / inTotal_memory);
		if (outTotal_memory == 0) {
			outper = 0;
			handler.sendEmptyMessageDelayed(1, 1600);
		} else {
			outper = (int) ((outTotal_memory - outAvliable_memory) * 100 / outTotal_memory);
		}
		arcper = (int) (inTotal_memory * 360 / (inTotal_memory + outTotal_memory));
	}

	@Override
	protected void init() {
		softManagerArcView = (SoftManagerArcView) findViewById(R.id.softManagerArcView);
		softmanager_inside_progress = (ProgressBar) findViewById(R.id.softmanager_inside_progress);
		soft_manager_user_soft = (TextView) findViewById(R.id.soft_manager_user_soft);
		soft_manager_sys_soft = (TextView) findViewById(R.id.soft_manager_sys_soft);
		softmanager_outside_progress = (ProgressBar) findViewById(R.id.softmanager_outside_progress);
		soft_manager_all_soft = (TextView) findViewById(R.id.soft_manager_all_soft);
		inside_memory = (TextView) findViewById(R.id.inside_memory);
		outside_memory = (TextView) findViewById(R.id.outside_memory);
		softmanager_inside_progress.setProgress(inper);
		softmanager_outside_progress.setProgress(outper);
		inside_memory.setText("可用内存:"
				+ Formatter.formatFileSize(this, inAvliable_memory) + "/"
				+ Formatter.formatFileSize(this, inTotal_memory));
		outside_memory.setText("可用内存"
				+ Formatter.formatFileSize(this, outAvliable_memory) + "/"
				+ Formatter.formatFileSize(this, outTotal_memory));
		softManagerArcView.setAngle(arcper);
	}

	@Override
	protected void afterInit() {
		soft_manager_sys_soft.setOnClickListener(this);
		soft_manager_user_soft.setOnClickListener(this);
		soft_manager_all_soft.setOnClickListener(this);
	}

	@Override
	protected void clickEvent(View v) {
		Intent intent = new Intent(SoftManagerActivity.this,
				DetailSoftMangerActivtity.class);
		Bundle bundle = new Bundle();
		switch (v.getId()) {
		case R.id.action_bar_icon_left:
			finish();
			return;
		case R.id.soft_manager_all_soft:
			bundle.putString("title", "所有软件");
			bundle.putInt("state", ALL);
			break;
		case R.id.soft_manager_sys_soft:
			bundle.putString("title", "系统软件");
			bundle.putInt("state", SYS);
			break;
		case R.id.soft_manager_user_soft:
			bundle.putString("title", "用户软件");
			bundle.putInt("state", USUER);
			break;
		}
		intent.putExtras(bundle);
		startActivity(intent);
	}

}
