package com.example.phonehelper;

import android.view.View;

import com.example.phonehelper.framework.BaseActivity;

/**
 * 关于界面
 * */
public class AboutActivity extends BaseActivity  {
	@Override
	protected void setFullScreen(boolean fullScreen) {
		// TODO Auto-generated method stub
		super.setFullScreen(true);
	}

	@Override
	protected void setLayout() {
		setContentView(R.layout.activity_about);
	}

	@Override
	protected void beforeInit() {

	}

	@Override
	protected void init() {
		setActionBar(getResources().getString(R.string.about_title),
				R.drawable.btn_homeasup_default, R.drawable.ic_child_configs,
				false);
	}

	@Override
	protected void afterInit() {

	}

	@Override
	protected void clickEvent(View v) {
		finish();
	}

}
