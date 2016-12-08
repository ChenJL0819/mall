package com.oiios.suibian.activity;

import com.oiios.suibian.R;
import com.oiios.suibian.utils.NetUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import cn.bmob.v3.BmobUser;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (!NetUtil.isNetworkAvailable(SplashActivity.this)) {
					Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
					startActivity(intent);
					finish();
					return;
				}
				BmobUser user = BmobUser.getCurrentUser(SplashActivity.this);
				Intent intent = new Intent();
				if (user != null) {
					intent.setClass(SplashActivity.this, MainActivity.class);
				} else {
					intent.setClass(SplashActivity.this, LoginActivity.class);
				}
				startActivity(intent);
				finish();
			}
		}, 1500);
	}
}
