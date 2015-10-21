package com.sky.togglebutton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public  class ToggleButton extends View {

	private ToggleState mToggleState = ToggleState.OPEN;
	private Bitmap slidBg = null;
	private Bitmap slidBg_close = null;
	private Bitmap switchBg = null;
	private int left = 0;
	private int centerX = 0;
	private int currentX = 0;
	private boolean isSliding = false;
	private onToggleStateChangeListener listener = null;

	private int Height = 0;
	private int Width = 0;

	private Bitmap reSetBitmap(Bitmap bp, boolean isSwitch) {// 重新计算生成Bitmap
		// 原Bitmap 尺寸
		int bpWidth = bp.getWidth();
		int bpHeight = bp.getHeight();

		// 缩放比例
		float widthScale = Width * 1.0f / bpWidth;
		float heightScale = Height * 1.0f / bpHeight;

		Matrix matrix = new Matrix();
		if (isSwitch) {// 如果是滑块 缩放比例为高度
			matrix.postScale(heightScale, heightScale);
		} else {// 如果是背景
			matrix.postScale(widthScale, heightScale);
		}
		Bitmap bitmap = Bitmap.createBitmap(bp, 0, 0, bp.getWidth(),
				bp.getHeight(), matrix, false);
		bp.recycle();// 回收原Bitmap
		return bitmap;
	}

	public ToggleButton(Context context, AttributeSet attrs) {
		// 在xml布局中使用时，需要改方法
		super(context, attrs);
	}

	public void setCloseStateBackgroundResource(int id) {// 根据传递的id 加载Bitmap
		slidBg_close = BitmapFactory.decodeResource(getResources(), id);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {// 自定义Touch事件
		if (null == switchBg) {// 至少要有滑块图片
			return true;
		}
		currentX = (int) event.getX();// 当前手指的位置

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:// 摁下时
			isSliding = true;// 是否正在滑动 是
			break;
		case MotionEvent.ACTION_UP:// 抬起时
			isSliding = false;// 否

			if (currentX > centerX) {
				if (ToggleState.OPEN != mToggleState) {
					mToggleState = ToggleState.OPEN;
					if (null != listener) {// 回调
						listener.onToggleStateChanged(mToggleState);
					}
				}
			} else {
				if (ToggleState.CLOSE != mToggleState) {
					mToggleState = ToggleState.CLOSE;
					if (null != listener) {// 回调
						listener.onToggleStateChanged(mToggleState);
					}
				}
			}
			break;
		}

		invalidate();// 更新
		return true;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (null == switchBg) {// 至少需要有滑块图片
			return;
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		Height = getMeasuredHeight();//获取测量完毕后控件的高
		Width = getMeasuredWidth();//获取测量完毕后控件的宽
		centerX = Width / 2;// 控件中心
		
		// 重新计算Bitmap的尺寸 适配控件（如果设置的话）
		if (null != slidBg) {
			slidBg = reSetBitmap(slidBg, false);
		}
		if (null != slidBg_close) {
			slidBg_close = reSetBitmap(slidBg_close, false);
		}
		if (null != switchBg) {
			switchBg = reSetBitmap(switchBg, true);
		}
	}

	public void setBackgroundResource(int id) {
		slidBg = BitmapFactory.decodeResource(getResources(), id);
	}

	public enum ToggleState {
		OPEN, CLOSE
	}

	public void setToggleState(ToggleState state) {
		this.mToggleState = state;
	}

	public void setSwitchBackgroundResource(int id) {
		switchBg = BitmapFactory.decodeResource(getResources(), id);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (null == switchBg) {
			return;
		}

		if (ToggleState.CLOSE == mToggleState && null != slidBg_close) {// 如果设置了关闭时背景
																		// 且当前状态为关闭
			canvas.drawBitmap(slidBg_close, 0, 0, null);
		} else if (null != slidBg) {// 如果状态为开 且设置有打开的图片
			canvas.drawBitmap(slidBg, 0, 0, null);
		}

		left = currentX - switchBg.getWidth() / 2;// 绘制从左边开始的位置

		if (isSliding) {// 如果正在滑动 修正位置
			if (left < 0) {
				left = 0;
			}
			if (left > (Width - switchBg.getWidth())) {
				left = Width - switchBg.getWidth();
			}
			canvas.drawBitmap(switchBg, left, 0, null);
		} else { // 否则根据开闭状态 绘制
			switch (mToggleState) {
			case CLOSE:
				canvas.drawBitmap(switchBg, 0, 0, null);
				break;
			case OPEN:
				canvas.drawBitmap(switchBg, Width - switchBg.getWidth(), 0,
						null);
				break;
			}
		}
	}

	public ToggleButton(Context context) {
		// 代码中，通过new 创建，需要该方法
		super(context);
	}

	public void setToggleStateChangeListener(
			onToggleStateChangeListener listener) {
		this.listener = listener;
	}

	public interface onToggleStateChangeListener {
		void onToggleStateChanged(ToggleState state);
	}
}
