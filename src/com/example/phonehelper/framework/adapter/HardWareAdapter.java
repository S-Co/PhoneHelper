package com.example.phonehelper.framework.adapter;

import java.util.ArrayList;

import com.example.phonehelper.R;
import com.example.phonehelper.bean.PhoneInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HardWareAdapter extends BaseAdapter {
	// 手机详情信息list
	private ArrayList<PhoneInfo> list;
	private Context context;
	private int[] backId = {
			R.drawable.notification_information_progress_green,
			R.drawable.notification_information_progress_red,
			R.drawable.notification_information_progress_yellow };

	public HardWareAdapter(ArrayList<PhoneInfo> list, Context context) {
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HardWareViewHolder holder;
		if (convertView == null) {
			holder = new HardWareViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.hardware_phone_items, null);
			holder.hardware_phone_item_iv = (ImageView) convertView
					.findViewById(R.id.hardware_phone_item_iv);
			holder.hardware_phone_item_top = (TextView) convertView
					.findViewById(R.id.hardware_phone_item_top);
			holder.hardware_phone_item_buttom = (TextView) convertView
					.findViewById(R.id.hardware_phone_item_buttom);
			convertView.setTag(holder);
		} else {
			holder = (HardWareViewHolder) convertView.getTag();
		}

		holder.hardware_phone_item_iv
				.setBackgroundResource(backId[position % 3]);
		holder.hardware_phone_item_iv.setImageResource(list.get(position)
				.getImageView());
		holder.hardware_phone_item_top.setText(list.get(position).getTop());
		holder.hardware_phone_item_buttom.setText(list.get(position)
				.getButtom());
		return convertView;
	}

	private class HardWareViewHolder {
		ImageView hardware_phone_item_iv;
		TextView hardware_phone_item_top, hardware_phone_item_buttom;
	}
}
