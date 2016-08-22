package com.example.phonehelper.framework.adapter;

import java.util.ArrayList;

import com.example.phonehelper.R;
import com.example.phonehelper.bean.AppInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class SoftManagerAdapter extends BaseAdapter {
	private ArrayList<AppInfo> list;
	private Context context;

	public SoftManagerAdapter(Context context) {
		this.context = context;
		list = new ArrayList<AppInfo>();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SoftManagerViewHolder holder;
		if (convertView == null) {
			holder = new SoftManagerViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.layout_softmanager_item, null);
			holder.layout_softmager_item_imageView = (ImageView) convertView
					.findViewById(R.id.layout_soft_manager_item_image);
			holder.layout_softmanager_item_appName = (TextView) convertView
					.findViewById(R.id.layout_soft_manager_item_appname);
			holder.layout_softmanager_item_appversion = (TextView) convertView
					.findViewById(R.id.layout_soft_manager_item_appversion);
			holder.layout_softmanager_item_cb = (CheckBox) convertView
					.findViewById(R.id.layout_softmanager_item_cb);
			holder.layout_softmanager_item_packgeName = (TextView) convertView
					.findViewById(R.id.layout_soft_manager_item_packgename);
			convertView.setTag(holder);
		} else {
			holder = (SoftManagerViewHolder) convertView.getTag();
		}
		final AppInfo info = list.get(position);
		holder.layout_softmager_item_imageView.setImageDrawable(info.getIcon());
		holder.layout_softmanager_item_appName.setText(info.getLableName());
		holder.layout_softmanager_item_appversion.setText(info.getPid() + "");
		holder.layout_softmanager_item_packgeName.setText(info.getPackgeName());
		holder.layout_softmanager_item_cb
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						info.setClean(isChecked);
					}
				});
		holder.layout_softmanager_item_cb.setChecked(info.isClean());
		return convertView;
	}

	private class SoftManagerViewHolder {
		CheckBox layout_softmanager_item_cb;
		TextView layout_softmanager_item_appName;
		TextView layout_softmanager_item_packgeName;
		TextView layout_softmanager_item_appversion;
		ImageView layout_softmager_item_imageView;
	}

	public ArrayList<AppInfo> getList() {
		return list;
	}

	/**
	 * notifyDataSetChanged()提示数据改变
	 * */
	public void setList(ArrayList<AppInfo> list) {
		this.list = list;
		notifyDataSetChanged();
	}

}
