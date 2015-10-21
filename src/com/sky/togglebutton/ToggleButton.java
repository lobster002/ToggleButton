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

	private Bitmap reSetBitmap(Bitmap bp, boolean isSwitch) {// ���¼�������Bitmap
		// ԭBitmap �ߴ�
		int bpWidth = bp.getWidth();
		int bpHeight = bp.getHeight();

		// ���ű���
		float widthScale = Width * 1.0f / bpWidth;
		float heightScale = Height * 1.0f / bpHeight;

		Matrix matrix = new Matrix();
		if (isSwitch) {// ����ǻ��� ���ű���Ϊ�߶�
			matrix.postScale(heightScale, heightScale);
		} else {// ����Ǳ���
			matrix.postScale(widthScale, heightScale);
		}
		Bitmap bitmap = Bitmap.createBitmap(bp, 0, 0, bp.getWidth(),
				bp.getHeight(), matrix, false);
		bp.recycle();// ����ԭBitmap
		return bitmap;
	}

	public ToggleButton(Context context, AttributeSet attrs) {
		// ��xml������ʹ��ʱ����Ҫ�ķ���
		super(context, attrs);
	}

	public void setCloseStateBackgroundResource(int id) {// ���ݴ��ݵ�id ����Bitmap
		slidBg_close = BitmapFactory.decodeResource(getResources(), id);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {// �Զ���Touch�¼�
		if (null == switchBg) {// ����Ҫ�л���ͼƬ
			return true;
		}
		currentX = (int) event.getX();// ��ǰ��ָ��λ��

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:// ����ʱ
			isSliding = true;// �Ƿ����ڻ��� ��
			break;
		case MotionEvent.ACTION_UP:// ̧��ʱ
			isSliding = false;// ��

			if (currentX > centerX) {
				if (ToggleState.OPEN != mToggleState) {
					mToggleState = ToggleState.OPEN;
					if (null != listener) {// �ص�
						listener.onToggleStateChanged(mToggleState);
					}
				}
			} else {
				if (ToggleState.CLOSE != mToggleState) {
					mToggleState = ToggleState.CLOSE;
					if (null != listener) {// �ص�
						listener.onToggleStateChanged(mToggleState);
					}
				}
			}
			break;
		}

		invalidate();// ����
		return true;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (null == switchBg) {// ������Ҫ�л���ͼƬ
			return;
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		Height = getMeasuredHeight();//��ȡ������Ϻ�ؼ��ĸ�
		Width = getMeasuredWidth();//��ȡ������Ϻ�ؼ��Ŀ�
		centerX = Width / 2;// �ؼ�����
		
		// ���¼���Bitmap�ĳߴ� ����ؼ���������õĻ���
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

		if (ToggleState.CLOSE == mToggleState && null != slidBg_close) {// ��������˹ر�ʱ����
																		// �ҵ�ǰ״̬Ϊ�ر�
			canvas.drawBitmap(slidBg_close, 0, 0, null);
		} else if (null != slidBg) {// ���״̬Ϊ�� �������д򿪵�ͼƬ
			canvas.drawBitmap(slidBg, 0, 0, null);
		}

		left = currentX - switchBg.getWidth() / 2;// ���ƴ���߿�ʼ��λ��

		if (isSliding) {// ������ڻ��� ����λ��
			if (left < 0) {
				left = 0;
			}
			if (left > (Width - switchBg.getWidth())) {
				left = Width - switchBg.getWidth();
			}
			canvas.drawBitmap(switchBg, left, 0, null);
		} else { // ������ݿ���״̬ ����
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
		// �����У�ͨ��new ��������Ҫ�÷���
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
