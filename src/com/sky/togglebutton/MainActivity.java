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

		btn = (ToggleButton) findViewById(R.id.btn);// ȡ�ÿؼ�
		btn.setBackgroundResource(R.drawable.bg);// ���ÿ��ش�ʱ����״̬
		btn.setCloseStateBackgroundResource(R.drawable.bg_2);// ���ÿ��عر�ʱ����
		btn.setSwitchBackgroundResource(R.drawable.btnbg);// ���ÿ��ػ���
		btn.setToggleState(ToggleState.OPEN);// ����Ĭ��״̬

		btn.setToggleStateChangeListener(this);
	}

	@Override
	public void onToggleStateChanged(ToggleState state) {
		if (state == ToggleState.OPEN) {
			tv.setText("��");
		} else {
			tv.setText("��");
		}
	}

}
