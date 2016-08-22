package com.example.phonehelper;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phonehelper.framework.BaseActivity;
import com.example.phonehelper.framework.db.PhoneCleanHelper;
import com.example.phonehelper.framework.db.SQLiteHelper;
import com.example.phonehelper.framework.tool.AppInfoManager;
import com.example.phonehelper.framework.tool.MemoryManager;
import com.example.phonehelper.framework.view.CleanArcView;

/**
 * homePage页面
 * */
public class HomePageActivity extends BaseActivity {
	/**
	 * 自定义的清理弧形控件
	 * */
	private CleanArcView home_score_arcview;
	/**
	 * 加速按键
	 * */
	private TextView home_scoretext_start;
	/**
	 * 加速按钮大饼图标
	 * */
	private ImageView home_score_imageview;
	/**
	 * 已经占用内存文本
	 * */
	private TextView home_scoretext_preview;
	private AppInfoManager applicationInfo;
	/**
	 * 通讯大全
	 * */
	private TextView home_item_contacts;
	/**
	 * 硬件检测
	 * */
	private TextView home_item_hardware;
	/**
	 * 手机加速
	 * */
	private TextView home_item_rocket;
	/**
	 * 软件管理
	 * */
	private TextView home_item_software;
	/**
	 * 文件管理
	 * */
	private TextView home_item_filemanager;
	/**
	 * 垃圾清理
	 * */
	private TextView home_item_sdcardclean;

	@Override
	protected void setFullScreen(boolean fullScreen) {
		super.setFullScreen(true);
	}

	@Override
	protected void setLayout() {
		setContentView(R.layout.activtity_home_page);
		applicationInfo = AppInfoManager.getInstance(this);
		SQLiteHelper.saveDate(this);
		PhoneCleanHelper.saveDate(this);
	}

	@Override
	protected void onResume() {
		clean();
		super.onResume();
	}

	@Override
	protected void beforeInit() {

	}

	@Override
	protected void init() {
		home_item_filemanager = (TextView) findViewById(R.id.home_item_filemanage);
		home_score_arcview = (CleanArcView) findViewById(R.id.home_score_arcview);
		home_score_imageview = (ImageView) findViewById(R.id.home_score_imageview);
		home_scoretext_start = (TextView) findViewById(R.id.home_scoretext_start);
		home_scoretext_preview = (TextView) findViewById(R.id.home_scoretext_preview);
		home_item_contacts = (TextView) findViewById(R.id.home_item_contacts);
		home_item_hardware = (TextView) findViewById(R.id.home_item_hardware);
		home_item_rocket = (TextView) findViewById(R.id.home_item_rocket);
		home_item_software = (TextView) findViewById(R.id.home_item_software);
		home_item_sdcardclean = (TextView) findViewById(R.id.home_item_sdcardclean);
	}

	/**
	 * 一键加速方法
	 */
	private void clean() {
		// 获取总内存
		double totalMemory = MemoryManager.getTotalMemory();
		// 获取可用内存
		double availMemory = MemoryManager.availableMemory(this);
		// 计算已用内存百分比
		int per = (int) ((totalMemory - availMemory) / totalMemory * 100);
		// 设置文本 自定义控件开始转动
		home_scoretext_preview.setText(per + "");
		home_score_arcview.setAngle(per * 360 / 100);
	}

	@Override
	protected void afterInit() {
		home_score_arcview.setOnClickListener(this);
		home_score_imageview.setOnClickListener(this);
		home_scoretext_start.setOnClickListener(this);
		home_item_contacts.setOnClickListener(this);
		home_item_hardware.setOnClickListener(this);
		home_item_rocket.setOnClickListener(this);
		home_item_software.setOnClickListener(this);
		home_item_filemanager.setOnClickListener(this);
		home_item_sdcardclean.setOnClickListener(this);
		clean();
		setActionBar(getResources().getString(R.string.app_name),
				R.drawable.ic_launcher, R.drawable.ic_child_configs, true);
	}

	public void homeItemHit(View v) {
		clickEvent(v);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			showDialog();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void showDialog() {
		AlertDialog.Builder dialog = new Builder(this);
		dialog.setMessage("你就这样的走了?");
		dialog.setNegativeButton("我留在这里", null);
		dialog.setNeutralButton("我犹豫不决", null);
		dialog.setPositiveButton("我轻轻的走了",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						System.exit(0);
					}
				});
		dialog.create().show();
	}

	@Override
	protected void clickEvent(View v) {
		switch (v.getId()) {
		case R.id.home_score_imageview:
		case R.id.home_scoretext_start:
			applicationInfo.killAllProgress();
			clean();
			break;
		case R.id.home_item_contacts:
			warpActivtiy(ContactActivity.class);
			overridePendingTransition(R.anim.otherpage_come,
					R.anim.homepage_newexit);
			break;
		case R.id.action_bar_icon_left:
			warpActivtiy(AboutActivity.class);
			break;
		case R.id.action_bar_icon_right:
			warpActivtiy(SettingActivity.class);
			break;
		case R.id.home_item_hardware:
			warpActivtiy(HardWareActivity.class);
			overridePendingTransition(R.anim.otherpage_come,
					R.anim.homepage_newexit);
			break;
		case R.id.home_item_rocket:
			warpActivtiy(PhoneSpeedUpActivity.class);
			overridePendingTransition(R.anim.phone_speed_come_in,
					R.anim.homepage_exit);
			break;
		case R.id.home_item_software:
			warpActivtiy(SoftManagerActivity.class);
			overridePendingTransition(R.anim.phone_speed_come_in,
					R.anim.homepage_exit);
			break;
		case R.id.home_item_filemanage:
			warpActivtiy(FileManagerActivity.class);
			overridePendingTransition(R.anim.otherpage_come,
					R.anim.homepage_newexit);
			break;
		case R.id.home_item_sdcardclean:
			warpActivtiy(PhoneCleanActivity.class);
			overridePendingTransition(R.anim.otherpage_come,
					R.anim.homepage_newexit);
			break;
		}
	}
}
