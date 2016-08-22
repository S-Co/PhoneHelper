package com.example.phonehelper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.phonehelper.framework.BaseActivity;
import com.example.phonehelper.framework.tool.FileManager;
import com.example.phonehelper.framework.tool.FileManager.FileSearchListener;
import com.example.phonehelper.framework.tool.FileTypeTool;

public class FileManagerActivity extends BaseActivity implements
		FileSearchListener, Callback {
	private TextView file_manager_file_priview;
	private TextView file_manager_allfile;
	private TextView file_manager_file;
	private TextView file_manager_video;
	private TextView file_manager_audio;
	private TextView file_manager_image;
	private TextView file_manager_zip;
	private TextView file_manager_app;
	private LinearLayout file_manager_l1;
	private LinearLayout file_manager_l2;
	private LinearLayout file_manager_l3;
	private LinearLayout file_manager_l4;
	private LinearLayout file_manager_l5;
	private LinearLayout file_manager_l6;
	private LinearLayout file_manager_l7;
	private ProgressBar p1, p2, p3, p4, p5, p6, p7;
	private ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7;
	private FileManager manager;
	private Thread thread;
	private Handler handler = new Handler(this);

	private void setText() {
		file_manager_file_priview.setText(Formatter.formatFileSize(
				getBaseContext(), manager.getAllFileSize()) + "");
		file_manager_allfile.setText(Formatter.formatFileSize(getBaseContext(),
				manager.getAllFileSize()) + "");
		file_manager_zip.setText(Formatter.formatFileSize(getBaseContext(),
				manager.getZipFileSize()) + "");
		file_manager_video.setText(Formatter.formatFileSize(getBaseContext(),
				manager.getVideoFileSize()) + "");
		file_manager_file.setText(Formatter.formatFileSize(getBaseContext(),
				manager.getTxtFileSize()) + "");
		file_manager_image.setText(Formatter.formatFileSize(getBaseContext(),
				manager.getImageFileSize()) + "");
		file_manager_audio.setText(Formatter.formatFileSize(getBaseContext(),
				manager.getAudioFileSize()) + "");
		file_manager_app.setText(Formatter.formatFileSize(getBaseContext(),
				manager.getAppFileSize()) + "");
	}

	@Override
	protected void setFullScreen(boolean fullScreen) {
		super.setFullScreen(true);
	}

	@Override
	protected void onResume() {
		super.onResume();
		setText();
	}

	// 当activity销毁时 停止查找 并且中断线程
	@Override
	protected void onDestroy() {
		super.onDestroy();
		manager.setStop(true);
		thread.interrupt();
		thread = null;
	}

	@Override
	protected void setLayout() {
		setContentView(R.layout.activity_filemanager);
		setActionBar("文件管理", R.drawable.btn_homeasup_default, 0, false);
	}

	@Override
	protected void beforeInit() {
		manager = FileManager.getInstance();
	}

	@Override
	protected void init() {
		findIDs();
		manager.setFileSearchListener(this);
	}

	private void findIDs() {
		file_manager_allfile = (TextView) findViewById(R.id.file_manager_allfile);
		file_manager_app = (TextView) findViewById(R.id.file_manager_app);
		file_manager_audio = (TextView) findViewById(R.id.file_manager_audio);
		file_manager_file = (TextView) findViewById(R.id.file_manager_file);
		file_manager_image = (TextView) findViewById(R.id.file_manager_image);
		file_manager_video = (TextView) findViewById(R.id.file_manager_video);
		file_manager_zip = (TextView) findViewById(R.id.file_manager_zip);
		file_manager_file_priview = (TextView) findViewById(R.id.file_manager_file_priview);
		file_manager_l1 = (LinearLayout) findViewById(R.id.file_manager_l1);
		file_manager_l2 = (LinearLayout) findViewById(R.id.file_manager_l2);
		file_manager_l3 = (LinearLayout) findViewById(R.id.file_manager_l3);
		file_manager_l4 = (LinearLayout) findViewById(R.id.file_manager_l4);
		file_manager_l5 = (LinearLayout) findViewById(R.id.file_manager_l5);
		file_manager_l6 = (LinearLayout) findViewById(R.id.file_manager_l6);
		file_manager_l7 = (LinearLayout) findViewById(R.id.file_manager_l7);

		p1 = (ProgressBar) findViewById(R.id.file_manager_l1_progress);
		p2 = (ProgressBar) findViewById(R.id.file_manager_l2_progress);
		p3 = (ProgressBar) findViewById(R.id.file_manager_l3_progress);
		p4 = (ProgressBar) findViewById(R.id.file_manager_l4_progress);
		p5 = (ProgressBar) findViewById(R.id.file_manager_l5_progress);
		p6 = (ProgressBar) findViewById(R.id.file_manager_l6_progress);
		p7 = (ProgressBar) findViewById(R.id.file_manager_l7_progress);

		iv1 = (ImageView) findViewById(R.id.file_manager_l1_imageview);
		iv2 = (ImageView) findViewById(R.id.file_manager_l2_imageview);
		iv3 = (ImageView) findViewById(R.id.file_manager_l3_imageview);
		iv4 = (ImageView) findViewById(R.id.file_manager_l4_imageview);
		iv5 = (ImageView) findViewById(R.id.file_manager_l5_imageview);
		iv6 = (ImageView) findViewById(R.id.file_manager_l6_imageview);
		iv7 = (ImageView) findViewById(R.id.file_manager_l7_imageview);
	}

	@Override
	protected void afterInit() {
		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				manager.init();
				manager.searchFiles();
			}
		});
		thread.start();
	}

	@Override
	protected void clickEvent(View v) {
		Intent intent = new Intent(FileManagerActivity.this,
				FileManagerDetailActivity.class);
		Bundle bundle = new Bundle();
		switch (v.getId()) {
		case R.id.action_bar_icon_left:
			finish();
			break;
		case R.id.file_manager_l1:
			bundle.putString("title", "全部");
			bundle.putLong("counts", manager.getAllFileInfo().size());
			bundle.putLong("size", manager.getAllFileSize());
			break;
		case R.id.file_manager_l2:
			bundle.putString("title", "文档");
			bundle.putLong("counts", manager.getTxtFileInfo().size());
			bundle.putLong("size", manager.getTxtFileSize());
			break;
		case R.id.file_manager_l3:
			bundle.putString("title", "视频");
			bundle.putLong("counts", manager.getVideoFileInfo().size());
			bundle.putLong("size", manager.getVideoFileSize());
			break;
		case R.id.file_manager_l4:
			bundle.putString("title", "音频");
			bundle.putLong("counts", manager.getAudioFileInfo().size());
			bundle.putLong("size", manager.getAudioFileSize());
			break;
		case R.id.file_manager_l5:
			bundle.putString("title", "图像");
			bundle.putLong("counts", manager.getImageFileInfo().size());
			bundle.putLong("size", manager.getImageFileSize());
			break;
		case R.id.file_manager_l6:
			bundle.putString("title", "压缩包");
			bundle.putLong("counts", manager.getZipFileInfo().size());
			bundle.putLong("size", manager.getZipFileSize());
			break;
		case R.id.file_manager_l7:
			bundle.putString("title", "程序包");
			bundle.putLong("counts", manager.getAppFileInfo().size());
			bundle.putLong("size", manager.getAppFileSize());
			break;
		}
		intent.putExtras(bundle);
		startActivityForResult(intent, 2);
	}

	@Override
	public void searching(String typeName) {
		Message msg = handler.obtainMessage();
		msg.what = 1;
		msg.obj = typeName;
		handler.sendMessage(msg);
	}

	@Override
	public void end(boolean isException) {
		handler.sendEmptyMessage(2);
	}

	@Override
	public boolean handleMessage(Message msg) {
		if (msg.what == 1) {
			String typeName = (String) msg.obj;
			file_manager_file_priview.setText(Formatter.formatFileSize(
					getBaseContext(), manager.getAllFileSize()) + "");
			file_manager_allfile.setText(Formatter.formatFileSize(
					getBaseContext(), manager.getAllFileSize()) + "");
			if (typeName.equals(FileTypeTool.TYPE_APK)) {
				file_manager_app.setText(Formatter.formatFileSize(
						getBaseContext(), manager.getAppFileSize()) + "");
			} else if (typeName.equals(FileTypeTool.TYPE_AUDIO)) {
				file_manager_audio.setText(Formatter.formatFileSize(
						getBaseContext(), manager.getAudioFileSize()) + "");
			} else if (typeName.equals(FileTypeTool.TYPE_IMAGE)) {
				file_manager_image.setText(Formatter.formatFileSize(
						getBaseContext(), manager.getImageFileSize()) + "");
			} else if (typeName.equals(FileTypeTool.TYPE_TXT)) {
				file_manager_file.setText(Formatter.formatFileSize(
						getBaseContext(), manager.getTxtFileSize()) + "");
			} else if (typeName.equals(FileTypeTool.TYPE_VIDEO)) {
				file_manager_video.setText(Formatter.formatFileSize(
						getBaseContext(), manager.getVideoFileSize()) + "");
			} else if (typeName.equals(FileTypeTool.TYPE_ZIP)) {
				file_manager_zip.setText(Formatter.formatFileSize(
						getBaseContext(), manager.getZipFileSize()) + "");
			}
		} else if (msg.what == 2) {
			setText();
			iv1.setVisibility(View.VISIBLE);
			p1.setVisibility(View.GONE);
			iv2.setVisibility(View.VISIBLE);
			p2.setVisibility(View.GONE);
			iv3.setVisibility(View.VISIBLE);
			p3.setVisibility(View.GONE);
			iv4.setVisibility(View.VISIBLE);
			p4.setVisibility(View.GONE);
			iv5.setVisibility(View.VISIBLE);
			p5.setVisibility(View.GONE);
			iv6.setVisibility(View.VISIBLE);
			p6.setVisibility(View.GONE);
			iv7.setVisibility(View.VISIBLE);
			p7.setVisibility(View.GONE);
			file_manager_l1.setOnClickListener(FileManagerActivity.this);
			file_manager_l2.setOnClickListener(FileManagerActivity.this);
			file_manager_l3.setOnClickListener(FileManagerActivity.this);
			file_manager_l4.setOnClickListener(FileManagerActivity.this);
			file_manager_l5.setOnClickListener(FileManagerActivity.this);
			file_manager_l6.setOnClickListener(FileManagerActivity.this);
			file_manager_l7.setOnClickListener(FileManagerActivity.this);
		}
		return false;
	}

}
