package com.sky.togglebutton;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import com.sky.togglebutton.R;
import com.sky.togglebutton.ToggleButton.ToggleState;

public class MainActivity extends Activity {

	private ToggleButton btn = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		btn = (ToggleButton) findViewById(R.id.btn);
		btn.setBackgroundResource(R.drawable.bg);
		btn.setSwitchBackgroundResource(R.drawable.btnbg);
		btn.setToggleState(ToggleState.CLOSE);
	}

}
