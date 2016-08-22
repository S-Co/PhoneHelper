package com.example.phonehelper.framework.adapter;

import java.util.ArrayList;
import android.content.Context;
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

import com.example.phonehelper.R;
import com.example.phonehelper.bean.AppInfo;

public class PhoneSpeedAdapter extends BaseAdapter {
	private ArrayList<AppInfo> list;
	private Context context;

	public PhoneSpeedAdapter(Context context) {
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
		PhoneSpeedViewHolder holder;
		if (convertView == null) {
			holder = new PhoneSpeedViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.phone_speed_item, null);
			holder.checkBox = (CheckBox) convertView
					.findViewById(R.id.phone_speed_item_cb);
			holder.appname = (TextView) convertView
					.findViewById(R.id.phone_item_appname);
			holder.appmemory = (TextView) convertView
					.findViewById(R.id.phone_item_appmemory);
			holder.isSys = (TextView) convertView
					.findViewById(R.id.phone_item_applevel);
			holder.appicon = (ImageView) convertView
					.findViewById(R.id.phone_speed_item_appiv);
			convertView.setTag(holder);
		} else {
			holder = (PhoneSpeedViewHolder) convertView.getTag();
		}
		final AppInfo info = list.get(position);
		holder.appname.setText(info.getLableName());
		long size = info.getSize();
		String memory = Formatter.formatFileSize(context, size);
		holder.appmemory.setText("内存"+memory);
		holder.appicon.setImageDrawable(info.getIcon());
		holder.isSys.setText(info.isSys() ? "系统应用" : "用户应用");
		// 监听放在设置之前
		holder.checkBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						info.setClean(isChecked);
					}
				});
		holder.checkBox.setChecked(info.isClean());
		return convertView;
	}

	public ArrayList<AppInfo> getList() {
		return list;
	}

	public void setList(ArrayList<AppInfo> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	private class PhoneSpeedViewHolder {
		CheckBox checkBox;
		TextView appname;
		TextView appmemory;
		TextView isSys;
		ImageView appicon;
	}
}
