package com.example.phonehelper.framework.adapter;

import java.util.ArrayList;

import com.example.phonehelper.R;
import com.example.phonehelper.bean.TableInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactDetailAdapter extends BaseAdapter {
	private ArrayList<TableInfo> list;
	private Context context;
	private int[] itemColorId = { 0xff99ff00, 0xff33ff00, 0xff33ff66,
			0xff33ffcc, 0xff0099cc, 0xff00ccff, 0xff0033ff, 0xff3300cc,
			0xff9900ff, 0xffcc00ff, 0xff9900cc, 0xffff33cc, 0xffff0099,
			0xffff0033, 0xffcc0033, 0xffff6600, 0xffffff33, 0xffccff99,
			0xff33ffff, 0xffff33ff, 0xffff3333 };

	public ContactDetailAdapter(ArrayList<TableInfo> list, Context context) {
		this.list = list;
		this.context = context;
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
		MyViewHolder holder;
		if (convertView == null) {
			holder = new MyViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.detail_contact_item, null);
			holder.name = (TextView) convertView
					.findViewById(R.id.detail_contact_item_name);
			holder.number = (TextView) convertView
					.findViewById(R.id.detail_contact_item_num);
			holder.iv = (TextView) convertView
					.findViewById(R.id.detail_contact_item_iv);
			convertView.setTag(holder);
		} else {
			holder = (MyViewHolder) convertView.getTag();
		}
		// 小细节....决定成败
		holder.iv.setText(list.get(position).getName().charAt(0) + "");
		holder.iv.setTextColor(itemColorId[position % 21]);
		// 标题
		holder.name.setText(list.get(position).getName().substring(1));
		holder.name.setTextColor(itemColorId[position % 21]);
		// 电话
		holder.number.setText(list.get(position).getNumber() + "");
		holder.number.setTextColor(itemColorId[position % 21]);
		return convertView;
	}

	// 一个数据尽量不要用ViewHolder,多个数据使用可以加快运行效率
	private class MyViewHolder {
		TextView name;
		TextView number;
		TextView iv;
	}
}
