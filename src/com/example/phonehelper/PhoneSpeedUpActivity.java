package com.example.phonehelper;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.phonehelper.bean.AppInfo;
import com.example.phonehelper.framework.BaseActivity;
import com.example.phonehelper.framework.adapter.PhoneSpeedAdapter;
import com.example.phonehelper.framework.tool.AppInfoManager;
import com.example.phonehelper.framework.tool.MemoryManager;
import com.example.phonehelper.framework.tool.PhoneInfoManager;

@SuppressLint("UseSparseArrays")
public class PhoneSpeedUpActivity extends BaseActivity implements
		OnCheckedChangeListener {
	/**
	 * 手机品牌
	 * */
	private TextView phone_speed_brand;
	/**
	 * 手机信号
	 * */
	private TextView phone_speed_name;
	/**
	 * 手机系统版本
	 * */
	private TextView phone_speed_version;
	/**
	 * 手机内存使用情况
	 * */
	private TextView phone_speed_memory;
	/**
	 * 手机内存使用情况进度条
	 * */
	private ProgressBar phone_speed_progress;
	/**
	 * 加速效果进度条
	 * */
	private ProgressBar phone_speed_buttom_progress;
	/**
	 * 一件加速
	 * */
	private Button phone_speed_cleanbtn;
	/**
	 * 显示系统进程或者用户应用
	 * */
	private Button phone_showprobtn;
	/**
	 * 加速适配器
	 * */
	private PhoneSpeedAdapter adapter;
	/**
	 * 设配器map
	 * */
	private HashMap<Integer, ArrayList<AppInfo>> map;

	/**
	 * 显示状态
	 * */
	private int state;
	/**
	 * 应用程序列表listView
	 * */
	private ListView phone_speed_listview;

	private AppInfoManager appInfoManager;
	/**
	 * 是否被清理勾选框
	 * */
	private CheckBox phone_speed_checkbox;

	@Override
	protected void setFullScreen(boolean fullScreen) {
		super.setFullScreen(true);
	}

	@Override
	protected void setLayout() {
		setContentView(R.layout.activtiy_phone_speed);
		setActionBar("手机加速", R.drawable.btn_homeasup_default, 0, false);
		adapter = new PhoneSpeedAdapter(this);
	}

	@Override
	protected void beforeInit() {
		appInfoManager = AppInfoManager.getInstance(this);
		map = new HashMap<Integer, ArrayList<AppInfo>>();
		state = AppInfoManager.SHOW_USER_PROCESS;
		initListView();
	}

	private void initListView() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				map = appInfoManager.getRunningAppInfos();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// Log.v("tag", map.get(state).toString());
						adapter.setList(map.get(state));
						phone_speed_buttom_progress.setVisibility(View.GONE);
						phone_speed_listview.setVisibility(View.VISIBLE);
					}
				});
			}
		}).start();
	}

	@Override
	protected void onResume() {
		initListView();
		setProGress();
		super.onResume();
	}

	@Override
	protected void init() {
		phone_speed_brand = (TextView) findViewById(R.id.phone_speed_brand);
		phone_speed_memory = (TextView) findViewById(R.id.phone_speed_memory);
		phone_speed_version = (TextView) findViewById(R.id.phone_speed_version);
		phone_speed_name = (TextView) findViewById(R.id.phone_speed_name);
		phone_speed_progress = (ProgressBar) findViewById(R.id.phone_speed_progress);
		phone_speed_buttom_progress = (ProgressBar) findViewById(R.id.phone_speed_buttom_progress);
		phone_speed_cleanbtn = (Button) findViewById(R.id.phone_speed_cleanbtn);
		phone_showprobtn = (Button) findViewById(R.id.phone_showprobtn);
		phone_speed_listview = (ListView) findViewById(R.id.phone_speed_listview);
		phone_speed_checkbox = (CheckBox) findViewById(R.id.phone_speed_checkbox);
		phone_speed_cleanbtn.setOnClickListener(this);
		phone_speed_checkbox.setOnCheckedChangeListener(this);
	}

	@Override
	protected void afterInit() {
		setProGress();
		phone_speed_listview.setAdapter(adapter);
		phone_showprobtn.setOnClickListener(this);
	}

	private void setProGress() {
		double total = MemoryManager.getTotalMemory();
		double availble = MemoryManager.availableMemory(this);
		int per = (int) ((total - availble) / total * 100);
		phone_speed_brand.setText(PhoneInfoManager.getPhonebrand());
		phone_speed_memory.setText("已用内存："
				+ PhoneInfoManager.getUsedMemory(this) + "/"
				+ PhoneInfoManager.getMemoryTotal(this));
		phone_speed_name.setText(PhoneInfoManager.getPhoneName());
		phone_speed_version.setText(PhoneInfoManager.getPhoneVersion());
		phone_speed_progress.setProgress(per);
	}

	@Override
	protected void clickEvent(View v) {
		switch (v.getId()) {
		case R.id.action_bar_icon_left:
			finish();
			break;
		// 按下最底端按钮时 根据状态切换显示在listView中的数据
		case R.id.phone_showprobtn:
			if (state == AppInfoManager.SHOW_SYSTEM_PROCESS) {
				state = AppInfoManager.SHOW_USER_PROCESS;
				phone_showprobtn.setText("显示系统进程");
			} else if (state == AppInfoManager.SHOW_USER_PROCESS) {
				state = AppInfoManager.SHOW_SYSTEM_PROCESS;
				phone_showprobtn.setText("显示用户进程");
			}
			adapter.setList(map.get(state));
			break;
		// 按下一键加速 杀死选中的进程 提醒数据变动
		case R.id.phone_speed_cleanbtn:
			if (state == AppInfoManager.SHOW_USER_PROCESS
					&& map.get(state).size() > 0) {
				appInfoManager.killProgress(map.get(state));
				// 清空map
				map.clear();
				// 让进度条可见 listView不可见
				phone_speed_buttom_progress.setVisibility(View.VISIBLE);
				phone_speed_listview.setVisibility(View.GONE);
				// 重新获取运行中的应用数据
				initListView();
				adapter.notifyDataSetChanged();
			} else if (state == AppInfoManager.SHOW_SYSTEM_PROCESS) {
				Toast.makeText(this, "无法清理系统应用", Toast.LENGTH_SHORT).show();
			}
			setProGress();
			break;
		}
	}

	// 监听checkeBox
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// 遍历集合 将集合中所有的应用的清楚标记变为true
		for (AppInfo checkedList : map.get(state)) {
			checkedList.setClean(isChecked);
		}
		// 提醒adapter数据发生变动
		adapter.notifyDataSetChanged();
	}

}
