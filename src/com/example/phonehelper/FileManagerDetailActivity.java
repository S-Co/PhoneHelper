package com.example.phonehelper;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.phonehelper.bean.FileInfo;
import com.example.phonehelper.framework.BaseActivity;
import com.example.phonehelper.framework.adapter.FileManagerDetailAdapter;
import com.example.phonehelper.framework.tool.FileManager;

public class FileManagerDetailActivity extends BaseActivity {
	private FileManagerDetailAdapter adapter;
	/**
	 * 加载程序的列表
	 * */
	private ListView filemanager_detail_listiew;
	private FileManager manager;
	/**
	 * 保存item信息的list
	 * */
	private ArrayList<FileInfo> list;
	/**
	 * 文件数量
	 * */
	private long fileNumber;
	/**
	 * 文件大小
	 * */
	private long size;
	private TextView filemanager_detail_usedspace_number;
	private TextView filemanager_detail_filecount_number;
	private Button filemanager_detail_button;
	private String title;
	private static int state = 0;

	@Override
	protected void setFullScreen(boolean fullScreen) {
		// TODO Auto-generated method stub
		super.setFullScreen(true);
	}

	@Override
	protected void setLayout() {
		setContentView(R.layout.filemanager_detail);
		manager = FileManager.getInstance();
		Bundle bundle = getIntent().getExtras();
		title = bundle.getString("title");
		fileNumber = bundle.getLong("counts");
		size = bundle.getLong("size");
		setActionBar(title, R.drawable.btn_homeasup_default, 0, false);
		if (title.equals("全部")) {
			list = manager.getAllFileInfo();
		} else if (title.equals("文档")) {
			state = 1;
			list = manager.getTxtFileInfo();
		} else if (title.equals("视频")) {
			state = 2;
			list = manager.getVideoFileInfo();
		} else if (title.equals("音频")) {
			state = 3;
			list = manager.getAudioFileInfo();
		} else if (title.equals("图像")) {
			state = 4;
			list = manager.getImageFileInfo();
		} else if (title.equals("压缩包")) {
			state = 5;
			list = manager.getZipFileInfo();
		} else if (title.equals("程序包")) {
			state = 6;
			list = manager.getAppFileInfo();
		}
	}

	@Override
	protected void beforeInit() {
		adapter = new FileManagerDetailAdapter(this);
	}

	@Override
	protected void init() {
		filemanager_detail_listiew = (ListView) findViewById(R.id.filemanager_detail_listiew);
		filemanager_detail_filecount_number = (TextView) findViewById(R.id.filemanager_detail_filecount_number);
		filemanager_detail_usedspace_number = (TextView) findViewById(R.id.filemanager_detail_usedspace_number);
		filemanager_detail_button = (Button) findViewById(R.id.filemanager_detail_button);
	}

	@Override
	protected void afterInit() {
		filemanager_detail_button.setOnClickListener(this);
		filemanager_detail_filecount_number.setText(fileNumber + "个");
		filemanager_detail_usedspace_number.setText(Formatter.formatFileSize(
				this, size) + "");
		adapter.setInfos(list);
		filemanager_detail_listiew.setAdapter(adapter);
	}

	@Override
	protected void clickEvent(View v) {
		switch (v.getId()) {
		case R.id.action_bar_icon_left:
			finish();
			break;
		case R.id.filemanager_detail_button:
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).isSelect()) {
					size -= list.get(i).getFile().length();
					change(i);
					list.get(i).getFile().delete();
					list.remove(i);
					i--;
				}
			}
			filemanager_detail_filecount_number.setText(list.size() + "个");
			filemanager_detail_usedspace_number.setText(Formatter
					.formatFileSize(this, size) + "");
			adapter.notifyDataSetChanged();
			break;
		}
	}

	private void change(int i) {
		manager.setAllFileSize(manager.getAllFileSize()- list.get(i).getFile().length());
		manager.getAllFileInfo().remove(list.get(i));
		switch (state) {
		case 1:
			manager.setTxtFileSize(manager.getTxtFileSize()- list.get(i).getFile().length());
			break;
		case 2:
			manager.setVideoFileSize(manager.getVideoFileSize()- list.get(i).getFile().length());
			break;
		case 3:
			manager.setAudioFileSize(manager.getAudioFileSize()- list.get(i).getFile().length());
			break;
		case 4:
			manager.setImageFileSize(manager.getImageFileSize()- list.get(i).getFile().length());
			break;
		case 5:
			manager.setZipFileSize(manager.getZipFileSize()- list.get(i).getFile().length());
			break;
		case 6:
			manager.setAppFileSize(manager.getAppFileSize()- list.get(i).getFile().length());
			break;
		}

	}
}
