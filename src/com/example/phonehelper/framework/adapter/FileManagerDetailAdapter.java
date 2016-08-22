package com.example.phonehelper.framework.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.phonehelper.R;
import com.example.phonehelper.bean.FileInfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class FileManagerDetailAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<FileInfo> infos;
	private LruCache<String, Bitmap> chCache = new LruCache<String, Bitmap>(100);
	private SimpleDateFormat format;

	@SuppressLint("SimpleDateFormat")
	public FileManagerDetailAdapter(Context context) {
		this.context = context;
		infos = new ArrayList<FileInfo>();
		format = new SimpleDateFormat("yyyy-MM-dd	hh:mm:ss");
	}

	public ArrayList<FileInfo> getInfos() {
		return infos;
	}

	public void setInfos(ArrayList<FileInfo> infos) {
		this.infos = infos;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return infos.size();
	}

	@Override
	public Object getItem(int position) {
		return infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		FileMangerViewHolder holder;
		if (convertView == null) {
			holder = new FileMangerViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.filemanager_detail_item, null);
			holder.cb = (CheckBox) convertView
					.findViewById(R.id.filemanager_detail_item_cb);
			holder.icon = (ImageView) convertView
					.findViewById(R.id.filemanager_detail_item_icon);
			holder.name = (TextView) convertView
					.findViewById(R.id.filemanager_detail_item_name);
			holder.size = (TextView) convertView
					.findViewById(R.id.filemanager_detail_item_size);
			holder.date = (TextView) convertView
					.findViewById(R.id.filemanager_detail_item_data);
			convertView.setTag(holder);
		} else {
			holder = (FileMangerViewHolder) convertView.getTag();
		}
		final FileInfo info = infos.get(position);
		holder.icon.setImageBitmap(getIcon(info));
		holder.size.setText(Formatter.formatFileSize(context, info.getFile()
				.length())
				+ "");
		holder.date.setText(format.format(new Date(info.getFile()
				.lastModified())) + "");
		holder.name.setText(info.getFile().getName());
		holder.cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				info.setSelect(isChecked);
			}
		});
		holder.cb.setChecked(info.isSelect());
		return convertView;
	}

	private Bitmap getIcon(FileInfo info) {
		String filename = info.getIconname();
		Bitmap bitmap = chCache.get(filename);
		if (bitmap == null) {
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.icon_file);
		}
		return bitmap;
	}

	private class FileMangerViewHolder {
		CheckBox cb;
		ImageView icon;
		TextView name;
		TextView date;
		TextView size;
	}
}
