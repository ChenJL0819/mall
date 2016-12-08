package com.oiios.suibian.utils;


import com.oiios.suibian.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class CircleIndicator extends View {
	private int radius = 10;
	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private float mWidth;
	private float mHeight;
	private float varWidth;
	private int count;

	public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.circle_indicator);
		count = array.getInteger(R.styleable.circle_indicator_count, 3);
		radius = array.getInteger(R.styleable.circle_indicator_radius, 10);
		array.recycle();
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(2);
	}

	public CircleIndicator(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CircleIndicator(Context context) {
		super(context);
	}

	public void setCount(int count){
		this.count = count;
		invalidate();
	}
	/**
	 * 如果有奇数个小圆圈其：计算公式为：w / 2 - 个数 * (radius + 2*paint.getStrokeWidth()); 
	 * 偶数：w / 2 - (个数 + 0.5)* (radius + 2*paint.getStrokeWidth());
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		if (count%2==0) {
			varWidth = mWidth = w / 2 - (count+0.5f) * (radius + 2*paint.getStrokeWidth());
		}else{
			varWidth = mWidth = w / 2 - count * (radius + 2*paint.getStrokeWidth());
		}
		mHeight = h / 2;
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// 设置绘制实心的圆
		paint.setStyle(Style.FILL);
		paint.setColor(Color.parseColor("#ff888888"));
		// 圆的个数
		for (int i = 0; i < count; i++) {
			canvas.drawCircle(mWidth + 3 * radius * i, mHeight, radius, paint);
		}

		paint.setColor(Color.RED);
		canvas.drawCircle(varWidth, mHeight, radius, paint);
	}

	public void updateDraw(int position, float offset) {
		varWidth = mWidth + 3 * radius * (position + offset);
		invalidate();// 重新调用onDraw
	}

}
