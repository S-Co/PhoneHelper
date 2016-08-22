package com.example.phonehelper.framework.view;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

@SuppressLint("DrawAllocation")
public class SoftManagerArcView extends View {
	/**
	 * 画笔
	 * */
	private Paint paint;
	/**
	 * 外部存储颜色
	 * */
	private int outColor;
	/**
	 * 内部存储颜色
	 * */
	private int inColor;
	/**
	 * 绘制的区域
	 * */
	private RectF oval;
	/**
	 * 转动角度
	 * */
	private static float sweepAngle;
	/**
	 * 起始角度
	 * */
	private float startAngle;

	/**
	 * public View (Context context)是在java代码创建视图的时候被调用，如果是从xml填充的视图，就不会调用这个
	 * public View (Context context,
	 * AttributeSetattrs)这个是在xml创建但是没有指定style的时候被调用 public View (Context
	 * context,AttributeSet attrs, int defStyle)这个是制定style
	 * */
	public SoftManagerArcView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 初始化画笔
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);
		// 初始化颜色 并且设置初始角度
		outColor = 0xff660099;
		inColor = 0xffff0038;
		startAngle = -90;
		// forWatch();
	}

	/**
	 * 测量屏幕宽高的方法
	 * */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int length = MeasureSpec.getSize(widthMeasureSpec);
		oval = new RectF(0, 0, length, length);
		setMeasuredDimension(length, length);
	}

	/**
	 * 用于预览的角度设置
	 * */
	public void forWatch() {
		sweepAngle = 180;
		postInvalidate();
	}

	/**
	 * 设置转动的角度
	 * */
	public void setAngle(final float angle) {
		final Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				// 如果转动的角度小于预设角度 就增加
				if (sweepAngle < angle) {
					sweepAngle += 4;
				} else if (sweepAngle > angle) {
					// 如果转动角度大于等于预设角度 停止任务
					sweepAngle = angle;
					timer.cancel();
				}
				// 刷新
				postInvalidate();
			}
		};
		// 安排任务
		timer.schedule(task, 0, 20);
	}
/**
 * 绘制view的方法所以看需要，一般情况下，直接用onDraw绘制view的content就可以了
 * ，如果绘制多一点的内容，可以调用draw()，不过Android官方推荐用只用onDraw就可以了。“
 * */
	@Override
	protected void onDraw(Canvas canvas) {
		// 绘制外部存储颜色
		paint.setColor(outColor);
		canvas.drawArc(oval, sweepAngle - 90, 360 - sweepAngle, true, paint);
		// 绘制内部存储颜色
		paint.setColor(inColor);
		canvas.drawArc(oval, startAngle, sweepAngle, true, paint);

	}
}
