package com.example.phonehelper;

import java.io.File;
import java.util.ArrayList;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.phonehelper.bean.RubbishInfo;
import com.example.phonehelper.framework.BaseActivity;
import com.example.phonehelper.framework.adapter.PhoneCleanAdapter;
import com.example.phonehelper.framework.db.PhoneCleanHelper;
import com.example.phonehelper.framework.tool.FileManager;

public class PhoneCleanActivity extends BaseActivity implements
		OnCheckedChangeListener, Callback {
	private ListView phone_clean_listview;
	private TextView phone_clean_totalsize;
	private Button phone_clean_button;
	private CheckBox phone_clean_cb;
	private ProgressBar phone_clean_progressbar;
	/**
	 * 垃圾总共大小
	 * */
	private long totalSize;
	private ArrayList<RubbishInfo> infos;
	/**
	 * 垃圾清理
	 * */
	private PhoneCleanAdapter adapter;
	private Handler handler = new Handler(this);

	@Override
	protected void setFullScreen(boolean fullScreen) {
		super.setFullScreen(true);
	}

	@Override
	protected void setLayout() {
		setContentView(R.layout.activity_phone_clean);
		setActionBar("垃圾清理", R.drawable.btn_homeasup_default, 0, false);
		adapter = new PhoneCleanAdapter(this);
	}

	@Override
	protected void beforeInit() {

	}

	@Override
	protected void init() {
		phone_clean_listview = (ListView) findViewById(R.id.phone_clean_listview);
		phone_clean_button = (Button) findViewById(R.id.phone_clean_button);
		phone_clean_totalsize = (TextView) findViewById(R.id.phone_clean_totalsize);
		phone_clean_cb = (CheckBox) findViewById(R.id.phone_clean_cb);
		phone_clean_progressbar = (ProgressBar) findViewById(R.id.phone_clean_progressbar);
		phone_clean_cb.setChecked(true);
	}

	@Override
	protected void afterInit() {
		phone_clean_button.setOnClickListener(this);
		phone_clean_listview.setAdapter(adapter);
		infos = PhoneCleanHelper.getPhoneRubbishfile(getBaseContext());
		asyncLoaddata();
	}

	private void asyncLoaddata() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (RubbishInfo info : infos) {
					File file = new File(info.getFilepath());
					long size = FileManager.getFileSize(file);
					info.setSize(size);
					totalSize += info.getSize();
					handler.sendEmptyMessage(1);
				}
				handler.sendEmptyMessage(2);
			}
		}).start();
	}

	@Override
	protected void clickEvent(View v) {
		switch (v.getId()) {
		case R.id.action_bar_icon_left:
			finish();
			break;
		case R.id.phone_clean_button:
			deleteFile();
			break;
		}
	}

	private void deleteFile() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					public void run() {
						for (int i = 0; i < infos.size(); i++) {
							if (infos.get(i).isClean()) {
								File file = new File(infos.get(i).getFilepath());
								long size = FileManager.getFileSize(file);
								totalSize -= size;
								FileManager.deleteFile(file);
								infos.remove(i);
								i--;
							}
						}
						adapter.notifyDataSetChanged();
						Log.e("tag", totalSize+"");
						phone_clean_totalsize.setText(Formatter.formatFileSize(
								getBaseContext(), totalSize) + "");
					}

				});
			}
		}).start();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		for (RubbishInfo info : infos) {
			info.setClean(isChecked);
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean handleMessage(Message msg) {
		if (msg.what == 1) {
			Log.e("tag", totalSize+"");
			phone_clean_totalsize.setText(Formatter.formatFileSize(
					getBaseContext(), totalSize) + "");
		} else if (msg.what == 2) {
			phone_clean_progressbar.setVisibility(View.GONE);
			phone_clean_listview.setVisibility(View.VISIBLE);
			adapter.setList(infos);
			phone_clean_cb.setOnCheckedChangeListener(PhoneCleanActivity.this);
		}
		return false;
	}

}
