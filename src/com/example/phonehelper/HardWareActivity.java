package com.example.phonehelper;

import java.util.ArrayList;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.phonehelper.bean.PhoneInfo;
import com.example.phonehelper.framework.BaseActivity;
import com.example.phonehelper.framework.adapter.HardWareAdapter;
import com.example.phonehelper.framework.tool.PhoneInfoManager;
/**
 * 硬件信息
 * */
public class HardWareActivity extends BaseActivity {
	/**
	 * 电池的ProgressBar
	 */
	private ProgressBar hardware_battery;
	/**
	 * 电池百分比的TextView
	 */
	private TextView hardware_battery_text;
	/**
	 * 当前电量
	 */
	private int current;
	/**
	 * 自定义的电池广播接收器
	 */
	/**
	 * 电池当前电量百分比
	 * */
	private int per;
	/**
	 * 电池温度
	 * */
	private int temperature;
	/**
	 * 定义接收器
	 * */
	private BatteryBroadReceiver receiver;
	/**
	 * 布局点击的layout
	 * */
	private LinearLayout layout;
	/**
	 * hardware适配器
	 * */
	private HardWareAdapter hardWareAdapter;
	/**
	 * hardware的list
	 * */
	private ArrayList<PhoneInfo> list;
	/**
	 * 详细信息的listView
	 * */
	private ListView listView;
	private int[] iconsId = { R.drawable.setting_info_icon_version,
			R.drawable.setting_info_icon_space,
			R.drawable.setting_info_icon_camera,
			R.drawable.setting_info_icon_cpu, R.drawable.setting_info_icon_root };
	private String[] topContext;
	private String[] buttomContext;

	// 设置字体大小
	// private float size =
	// TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20,
	// getResources().getDisplayMetrics());
	@Override
	protected void setFullScreen(boolean fullScreen) {
		super.setFullScreen(true);
	}

	@Override
	protected void setLayout() {
		setContentView(R.layout.activity_hardware);
		// 意图过滤
		IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		// 创建广播接收器
		receiver = new BatteryBroadReceiver();
		// 注册广播接收器
		registerReceiver(receiver, filter);
	}

	@Override
	protected void beforeInit() {
		setActionBar("硬件信息", R.drawable.btn_homeasup_default, 0, false);
	}

	@Override
	protected void init() {
		hardware_battery = (ProgressBar) findViewById(R.id.hardware_battery);
		hardware_battery_text = (TextView) findViewById(R.id.hardware_battery_text);
		layout = (LinearLayout) findViewById(R.id.hardware_linearLayout);
		listView = (ListView) findViewById(R.id.hardware_listview);
		buttomInit();
		topInit();
	}

	/**
	 * 初始化phone界面上面textView的内容
	 * */
	private void topInit() {
		topContext = new String[5];
		topContext[0] = "设备名称：" + PhoneInfoManager.getPhoneName();
		topContext[1] = "总共内存：" + PhoneInfoManager.getMemoryTotal(this);
		topContext[2] = "手机分辨率："
				+ PhoneInfoManager.getScreenPixels(HardWareActivity.this);
		topContext[3] = "CPU名称：" + PhoneInfoManager.getCPUName2();
		topContext[4] = "基带版本：" + PhoneInfoManager.getBaseband_Ver();
	}

	/**
	 * 初始化phone界面下面textView的内容
	 * */
	private void buttomInit() {
		buttomContext = new String[5];
		buttomContext[0] = "安卓版本：" + PhoneInfoManager.getPhoneVersion();
		buttomContext[1] = "剩余内存：" + PhoneInfoManager.getMemoryAvalible(this);
		buttomContext[2] = "相机最大像素：" + PhoneInfoManager.getCamerPixels();
		buttomContext[3] = PhoneInfoManager.getCPUNumber();
		buttomContext[4] = "是否ROOT：" + PhoneInfoManager.isRoot();
	}

	@Override
	protected void afterInit() {
		list = new ArrayList<PhoneInfo>();
		for (int i = 0; i < 5; i++) {
			list.add(new PhoneInfo(iconsId[i], topContext[i], buttomContext[i]));
		}
		hardWareAdapter = new HardWareAdapter(list, this);
		listView.setAdapter(hardWareAdapter);
		layout.setOnClickListener(this);
	}

	@Override
	protected void clickEvent(View v) {
		switch (v.getId()) {
		case R.id.action_bar_icon_left:
			finish();
			break;
		case R.id.hardware_linearLayout:
			AlertDialog();
			break;
		}
	}

	/**
	 * 系统dialog
	 * */
	private void AlertDialog() {
		AlertDialog.Builder dialog = new Builder(this);
		// dialog.setTitle("电池信息");字体看不清
		String[] items = new String[2];
		items[0] = "电池当前电量:" + current;
		items[1] = "电池温度:" + temperature * 0.1 + "℃";
		dialog.setItems(items, null);
		dialog.create().show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在销毁页面的时候解除注册
		unregisterReceiver(receiver);
	}

	/**
	 * 自定义电池广播接收器
	 */
	class BatteryBroadReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// 判断意图是否是由系统发出的电池信息
			if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
				// 从意图中获取数据包
				Bundle bundle = intent.getExtras();
				// 获取当前电量
				current = bundle.getInt(BatteryManager.EXTRA_LEVEL);
				// 获取总电量
				int total = bundle.getInt(BatteryManager.EXTRA_SCALE);
				// 设置总电量
				hardware_battery.setMax(total);
				// 设置当前电量
				hardware_battery.setProgress(current);
				// 计算电量百分比并设置到textView上
				per = current * 100 / total;
				hardware_battery_text.setText(per + "%");
				// 获取手机电池温度
				temperature = bundle.getInt(BatteryManager.EXTRA_TEMPERATURE);
			}
		}
	}
}
