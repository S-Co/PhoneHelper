package com.example.phonehelper;

import java.util.ArrayList;

import com.example.phonehelper.framework.BaseActivity;
import com.example.phonehelper.framework.adapter.GuideAdapter;
import com.example.phonehelper.framework.sf.SheredFreHelper;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

;
public class GuideActivity extends BaseActivity implements OnTouchListener {
	/**
	 * 导航页面的ViewPager
	 */
	private ViewPager guide_viewPager;
	/**
	 * 导航页面ViewPager的ChildView集合
	 */
	private ArrayList<ImageView> guide_views;
	/**
	 * 自定义的ViewPager适配器
	 */
	private GuideAdapter guide_adapter;

	/**
	 * 图标数组
	 * */
	private ImageView[] icons;
	// 记录图标是第几个
	private int current;
	// 记录进入状态的数据库
	private SheredFreHelper sheredFreHelper;

	// 设置为全屏
	@Override
	protected void setFullScreen(boolean fullScreen) {
		// TODO Auto-generated method stub
		// 获取sheredfreHelper对象,如果已经观看过则直接跳转
		sheredFreHelper = SheredFreHelper.getInstance(this);
		if (sheredFreHelper.getDate()) {
			warpActivtiy(LogoActivity.class);
			finish();
		}
		super.setFullScreen(true);
	}

	@Override
	public void setLayout() {
		setContentView(R.layout.activtiy_guide);
	}

	@Override
	public void beforeInit() {
		// 初始化相关数据
		guide_views = new ArrayList<ImageView>();
		// 得到相对布局的布局参数集合对象
		// 构造方法参数一和参数二分别是宽和高
		RelativeLayout.LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		ImageView iv1 = new ImageView(this);
		iv1.setLayoutParams(params);
		ImageView iv2 = new ImageView(this);
		iv2.setLayoutParams(params);
		ImageView iv3 = new ImageView(this);
		iv3.setLayoutParams(params);
		// 第四个空界面
		ImageView iv4 = new ImageView(this);
		iv4.setLayoutParams(params);

		// 设置放大类型 适应中心
		iv1.setScaleType(ScaleType.FIT_CENTER);
		iv2.setScaleType(ScaleType.FIT_CENTER);
		iv3.setScaleType(ScaleType.FIT_CENTER);
		// 第四个空界面
		iv4.setScaleType(ScaleType.FIT_CENTER);

		// 设置图片资源
		iv1.setImageResource(R.drawable.adware_style_applist);
		iv2.setImageResource(R.drawable.adware_style_banner);
		iv3.setImageResource(R.drawable.adware_style_creditswall);
		// 添加图片
		guide_views.add(iv1);
		guide_views.add(iv2);
		guide_views.add(iv3);
		// 第四个空界面
		guide_views.add(iv4);

		guide_adapter = new GuideAdapter(guide_views, this);
	}

	@Override
	public void init() {
		guide_viewPager = (ViewPager) findViewById(R.id.guide_viewPager);
		// 初始化三个小点
		initIcons();
		// 给guide_viewpager实现触摸监听
		guide_viewPager.setOnTouchListener(this);
		// 给ViewPager实现翻页监听
		guide_viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {

				for (int i = 0; i < icons.length; i++) {
					// 把当前位置的设置成可见
					icons[i].setEnabled(true);
					if (position != i) {
						icons[i].setEnabled(false);
					}
				}
				current = position;
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
	}

	@Override
	public void afterInit() {
		// 设置适配器
		guide_viewPager.setAdapter(guide_adapter);
	}

	// 初始化小图标
	private void initIcons() {
		icons = new ImageView[3];
		icons[0] = (ImageView) findViewById(R.id.iv1);
		icons[1] = (ImageView) findViewById(R.id.iv2);
		icons[2] = (ImageView) findViewById(R.id.iv3);
		icons[0].setEnabled(true);
		icons[1].setEnabled(false);
		icons[2].setEnabled(false);
	}

	// 记录初始位置
	private float fistx;

	// 重写onTouch方法实现界面跳转
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		// 如果手势是按下,记录那个时刻的x坐标
		case MotionEvent.ACTION_DOWN:
			fistx = event.getX();
			break;
		// 记录手指移动的坐标,做判断,然后跳转页面
		case MotionEvent.ACTION_MOVE:
			if (fistx - event.getX() > 100 && current == icons.length - 1) {
				// 实现界面跳转
				warpActivtiy(HomePageActivity.class);
				// 记录已经观看过
				sheredFreHelper.putDate();
				finish();
			}
		}
		return false;
	}

	@Override
	protected void clickEvent(View v) {
	
	}
}
