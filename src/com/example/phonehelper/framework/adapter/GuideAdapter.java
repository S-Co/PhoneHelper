package com.example.phonehelper.framework.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class GuideAdapter extends PagerAdapter {
	/**
	 * 图片集合
	 */
	ArrayList<ImageView> guide_views;
	/**
	 * 上下文内容
	 */
	Context context;

	/**
	 * 适配器构造方法
	 * 
	 * @param context
	 *            上下文内容
	 * @param guide_views
	 *            源图片集合
	 */
	public GuideAdapter(ArrayList<ImageView> guide_views, Context context) {
		this.guide_views = guide_views;
		this.context = context;
	}

	// 销毁item的方法
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(guide_views.get(position));
	}

	// 初始化item的方法
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(guide_views.get(position));
		return guide_views.get(position);
	}

	// 获取item数量
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return guide_views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

}
