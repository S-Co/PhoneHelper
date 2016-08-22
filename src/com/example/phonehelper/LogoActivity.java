package com.example.phonehelper;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;

import com.example.phonehelper.framework.BaseActivity;

/**
 * loGo动画页面
 * */
public class LogoActivity extends BaseActivity implements Callback {
	Handler handler = new Handler(this);

	@Override
	protected void setFullScreen(boolean fullScreen) {
		super.setFullScreen(true);
	}

	@Override
	protected void setLayout() {
		setContentView(R.layout.activtiy_logo);
	}

	@Override
	protected void beforeInit() {
		handler.sendEmptyMessageDelayed(1, 1500);

	}

	@Override
	protected void init() {
	}

	@Override
	protected void afterInit() {

	}

	@Override
	public boolean handleMessage(Message msg) {
		if (msg.what == 1) {
			warpActivtiy(HomePageActivity.class);
			overridePendingTransition(R.anim.menu_come_in, R.anim.log_end);
			finish();
		}
		return false;
	}

	@Override
	protected void clickEvent(View v) {
	}
}
