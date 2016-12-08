package com.oiios.suibian.activity;

import com.oiios.suibian.R;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public abstract class BaseActivity extends Activity {
	// ×Ô¶¨ÒåActionBar
	protected RelativeLayout myActionBar;
	protected TextView tvTitle;
	protected ImageView ivHeadLeft;
	protected ImageView ivHeadRight;

	public void initActionBar(int leftResId, String title, int rightResId) {
		if (myActionBar == null) {
			return;
		}

		if (leftResId != -1) {
			ivHeadLeft = (ImageView) myActionBar.findViewById(R.id.ivHeadLeft);
			ivHeadLeft.setImageResource(leftResId);
		}
		if (title != null && !title.equals("")) {
			tvTitle = (TextView) myActionBar.findViewById(R.id.tvTitle);
			tvTitle.setText(title);
		}
		if (rightResId != -1) {
			ivHeadRight = (ImageView) myActionBar.findViewById(R.id.ivHeadRight);
			ivHeadRight.setImageResource(rightResId);
		}
	}

	public abstract void bindListener();
	public abstract void initView();
	public void toast(String str) {
		Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
	}

}
