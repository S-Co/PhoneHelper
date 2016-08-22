package com.example.phonehelper.framework.view;

//画弧形的类
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

@SuppressLint("DrawAllocation")
public class CleanArcView extends View {
	private Paint paint;
	private int state;
	private final int BACK = 0;
	private final int FORWARD = 1;
	private final int START_ANGLE = -90;
	private float sweepAngle;
	private RectF oval;
	private boolean isRunning;
	private int[] colors = {0xfff5fffa,0xff3cb371,0xff00ff00,0xffadff2f,0xffff8c00
			,0xffff4500,0xffff0000};
	
	public CleanArcView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		paint.setColor(0xff00ff00);
		// 设置抗锯齿
		paint.setAntiAlias(true);
		// 设置抗抖动
		paint.setDither(true);
		forWatch(360);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 获取宽高
		int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
		int viewHeight = MeasureSpec.getSize(heightMeasureSpec);
		oval = new RectF(0, 0, viewWidth, viewHeight);
		LinearGradient gradient = new LinearGradient(viewWidth, 0, 0,
				viewHeight, colors, null, Shader.TileMode.MIRROR);
		paint.setShader(gradient);
		// 重设宽高
		setMeasuredDimension(viewWidth, viewHeight);
	}

	public void setAngle(final int angle) {
		// 当弧形正在绘制的时候 退出方法
		if (isRunning) {
			return;
		}
		isRunning = true;
		state = BACK;
		sweepAngle = angle;
		// 定义定时任务
		final Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				switch (state) {
				case BACK:
					// 当状态是回退时
					sweepAngle -= 6;
					if (sweepAngle <= 0) {
						state = FORWARD;
					}
					break;
				case FORWARD:
					// 当状态是向前的时候
					// 如果转角超过了预设角度 停止任务
					sweepAngle += 6;
					if (sweepAngle > angle) {
						timer.cancel();
						isRunning = false;
					}
					break;
				}
				// 刷新
				postInvalidate();
			}
		};
		// 安排任务 参数一是任务 参数二是延时 参数三是周期
		timer.schedule(timerTask, 0, 20);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// 绘制弧形
		for (int i = 0; i < 12; i++) {
//			paint.setColor(itemColorId[i]);
			canvas.drawArc(oval, START_ANGLE,
					sweepAngle, true, paint);
		}
	}

	public void forWatch(final int angle) {
		sweepAngle = angle;
	}
}
