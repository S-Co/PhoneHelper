package com.example.phonehelper;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.example.phonehelper.bean.ClassInfo;
import com.example.phonehelper.framework.BaseActivity;
import com.example.phonehelper.framework.adapter.ContactAdapter;
import com.example.phonehelper.framework.db.SQLiteHelper;

/**
 * 通讯大全
 * */
public class ContactActivity extends BaseActivity implements
		OnItemClickListener {
	/**
	 * 通讯大全adapter
	 * */
	private ContactAdapter adapter;
	/**
	 * 通讯大全里面的 gridView
	 * */
	private GridView yellow_activty_gridview;
	/**
	 * 封装了每个类别名字和下标的类
	 * */
	private ArrayList<ClassInfo> list;

	@Override
	protected void setFullScreen(boolean fullScreen) {
		super.setFullScreen(true);
	}

	@Override
	protected void setLayout() {
		setContentView(R.layout.activtiy_contact);
		setActionBar("通讯大全", R.drawable.btn_homeasup_default, 0, false);
	}

	@Override
	protected void beforeInit() {
		yellow_activty_gridview = (GridView) findViewById(R.id.yellow_activty_gridview);
		list = SQLiteHelper.getClassInfos();
		adapter = new ContactAdapter(this, list);
	}

	@Override
	protected void init() {
		yellow_activty_gridview.setAdapter(adapter);
		yellow_activty_gridview.setOnItemClickListener(this);
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
		}
	}

	/**
	 * item监听事件,根据item下标传送相应的名字
	 * */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(this, DetailContactActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("title", list.get(position).getName());
		bundle.putInt("index", list.get(position).getIndex());
		intent.putExtras(bundle);
		// Log.d("tag", "ssssssssssssssssss");
		startActivity(intent);
	}

}
