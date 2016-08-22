package com.example.phonehelper.framework.adapter;

import java.util.ArrayList;

import com.example.phonehelper.R;
import com.example.phonehelper.bean.ClassInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactAdapter extends BaseAdapter {
	// private ArrayList<TextView> tv;
	private Context context;
	private ArrayList<ClassInfo> list;
	/**
	 * 颜色数组
	 * */
	private int[] colorID = { R.drawable.contact_select_style1,
			R.drawable.contact_select_style2, R.drawable.contact_select_style3 };

	public ContactAdapter(Context context, ArrayList<ClassInfo> list) {
		this.context = context;
		this.list = list;
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
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.contacts_item, null);
			holder.tv = (TextView) convertView
					.findViewById(R.id.yellow_activity_textview);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv.setText(list.get(position).getName());
		holder.tv.setBackgroundResource(colorID[position % 3]);
		return convertView;
	}

	private class ViewHolder {
		TextView tv;
	}
}
