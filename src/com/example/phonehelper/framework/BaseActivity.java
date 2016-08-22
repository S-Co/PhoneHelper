package com.example.phonehelper.framework;

import com.example.phonehelper.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class BaseActivity extends Activity implements OnClickListener {
	/**
	 * 是否全屏的变量
	 * */
	public static boolean isFullScreen;
	public ImageView action_bar_icon_right, action_bar_icon_left;
	public TextView action_bar_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setFullScreen(isFullScreen);
		setLayout();
		beforeInit();
		init();
		afterInit();
	}

	/**
	 * 设置是否全屏
	 * */
	protected void setFullScreen(boolean fullScreen) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置导航栏透明
		// getWindow()
		// .addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		// getWindow().addFlags(
		// WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		if (fullScreen) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}

	}

	/**
	 * 绑定布局的方法
	 * */
	protected abstract void setLayout();

	/**
	 * 初始化控件之前的事物处理
	 * */
	protected abstract void beforeInit();

	/**
	 * 初始化方法
	 * */
	protected abstract void init();

	/**
	 * 初始化控件之后的事物处理
	 * */
	protected abstract void afterInit();

	@Override
	public void onClick(View v) {
		clickEvent(v);
	}

	/**
	 * 设置actionBar的方法
	 * 
	 * @param title
	 *            文本内容
	 * @param iconLeft
	 *            左边图标资源id
	 * @param isRight
	 *            是否有右边
	 * @param iconRight
	 *            右边图片资源id
	 * */
	protected void setActionBar(String title, int iconLeft, int iconRight,
			boolean isRight) {
		action_bar_icon_left = (ImageView) findViewById(R.id.action_bar_icon_left);
		action_bar_icon_left.setImageResource(iconLeft);
		action_bar_icon_left.setOnClickListener(this);
		action_bar_title = (TextView) findViewById(R.id.action_bar_title);
		action_bar_title.setText(title);
		action_bar_icon_right = (ImageView) findViewById(R.id.action_bar_icon_right);
		if (isRight) {
			action_bar_icon_right.setImageResource(iconRight);
			action_bar_icon_right.setOnClickListener(this);
		} else {
			action_bar_icon_right.setVisibility(View.GONE);
		}
	}

	protected abstract void clickEvent(View v);

	/**
	 * 跳转方法
	 * */
	protected void warpActivtiy(Class<?> targetClass) {
		Intent intent = new Intent(this, targetClass);
		startActivity(intent);
	}
}
