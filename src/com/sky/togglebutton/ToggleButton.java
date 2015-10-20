package com.sky.togglebutton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class ToggleButton extends View {

	private ToggleState mToggleState = ToggleState.OPEN;
	private Bitmap slidBg = null;
	private Bitmap bg = null;

	public ToggleButton(Context context, AttributeSet attrs) {
		// 在xml布局中使用时，需要改方法
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (null == bg) {
			return;
		}
		setMeasuredDimension(bg.getWidth(), bg.getHeight());
	}

	public void setBackgroundResource(int id) {
		bg = BitmapFactory.decodeResource(getResources(), id);
	}

	public enum ToggleState {
		OPEN, CLOSE
	}

	public void setToggleState(ToggleState state) {
		this.mToggleState = state;
	}

	public void setSwitchBackgroundResource(int id) {
		slidBg = BitmapFactory.decodeResource(getResources(), id);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		
		canvas.drawBitmap(bg, 0, 0, null);
		
		switch (mToggleState) {
		
			case CLOSE:
				canvas.drawBitmap(slidBg, 0, 0, null);
				break;
				
			case OPEN:
				canvas.drawBitmap(slidBg, bg.getWidth() - slidBg.getWidth(), 0, null);
			break;
		}

	}

	public ToggleButton(Context context) {
		// 代码中，通过new 创建，需要该方法
		super(context);
	}

}
