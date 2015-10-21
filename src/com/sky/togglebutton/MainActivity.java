package com.sky.togglebutton;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.sky.togglebutton.R;
import com.sky.togglebutton.ToggleButton.ToggleState;
import com.sky.togglebutton.ToggleButton.onToggleStateChangeListener;

public class MainActivity extends Activity implements
		onToggleStateChangeListener {

	private ToggleButton btn = null;
	private TextView tv = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		tv = (TextView) findViewById(R.id.tv);

		btn = (ToggleButton) findViewById(R.id.btn);// 取得控件
		btn.setBackgroundResource(R.drawable.bg);// 设置开关打开时背景状态
		btn.setCloseStateBackgroundResource(R.drawable.bg_2);// 设置开关关闭时背景
		btn.setSwitchBackgroundResource(R.drawable.btnbg);// 设置开关滑块
		btn.setToggleState(ToggleState.OPEN);// 设置默认状态

		btn.setToggleStateChangeListener(this);
	}

	@Override
	public void onToggleStateChanged(ToggleState state) {
		if (state == ToggleState.OPEN) {
			tv.setText("开");
		} else {
			tv.setText("关");
		}
	}

}
