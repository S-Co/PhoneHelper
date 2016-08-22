package com.example.phonehelper;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.phonehelper.bean.TableInfo;
import com.example.phonehelper.framework.BaseActivity;
import com.example.phonehelper.framework.adapter.ContactDetailAdapter;
import com.example.phonehelper.framework.db.SQLiteHelper;

/**
 * 通讯大全详细信息
 * */
public class DetailContactActivity extends BaseActivity implements
		OnItemClickListener {
	private ArrayList<TableInfo> list;
	private ListView listView;
	private ContactDetailAdapter adapter;

	@Override
	protected void setFullScreen(boolean fullScreen) {
		// TODO Auto-generated method stub
		super.setFullScreen(true);
	}

	@Override
	protected void setLayout() {
		setContentView(R.layout.activity_detail_contact);
	}

	@Override
	protected void beforeInit() {
		listView = (ListView) findViewById(R.id.detail_contact_lv);
	}

	@Override
	protected void init() {
		Bundle bundle = getIntent().getExtras();
		// 获取title
		String title = bundle.getString("title");
		int index = bundle.getInt("index");
		setActionBar(title, R.drawable.btn_homeasup_default, 0, false);
		list = SQLiteHelper.getTableInfos(index);
		adapter = new ContactDetailAdapter(list, this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
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
	 * item监听拨打电话
	 * */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		long number = list.get(position).getNumber();
		Intent intent = new Intent(Intent.ACTION_DIAL);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setData(Uri.parse("tel:" + number));
		startActivity(intent);
	}

}
