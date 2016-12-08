package com.oiios.suibian.activity;

import com.oiios.suibian.R;
import com.oiios.suibian.listener.UpdateUserListener;
import com.oiios.suibian.utils.TitleUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import cn.bmob.v3.BmobUser;

public class SetActivity extends BaseActivity implements OnClickListener, UpdateUserListener {
	Button btnLogout;
	RelativeLayout layoutNickname;
	RelativeLayout layoutPhone;

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);
		MyApplication.listActivity.add(this);
		TitleUtil.setSystemBarColor(this, R.color.mainColor);
		initView();
		bindListener();
	}
	
	@Override
	public void initView() {
		myActionBar = (RelativeLayout) findViewById(R.id.myActionBar);
		initActionBar(R.drawable.previous, "ÉèÖÃ", -1);
		layoutPhone = (RelativeLayout) findViewById(R.id.layoutPhone);
		btnLogout = (Button) findViewById(R.id.btnLogout);
		layoutNickname = (RelativeLayout) findViewById(R.id.layoutNickname);
	}

	@Override
	public void bindListener() {
		btnLogout.setOnClickListener(this);
		layoutNickname.setOnClickListener(this);
		layoutPhone.setOnClickListener(this);
		ivHeadLeft.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.ivHeadLeft:
			finish();
			break;
		case R.id.btnLogout:
			// ×¢ÏúµÇÂ¼
			BmobUser.logOut(getApplicationContext());
			intent.setClass(SetActivity.this, LoginActivity.class);
			startActivity(intent);
			for (Activity activity : MyApplication.listActivity) {
				activity.finish();
			}
			break;
		case R.id.layoutNickname:
			break;
		case R.id.layoutPhone:
			Intent intent2 = new Intent(SetActivity.this, ModifyInfoActivity.class);
			startActivity(intent2);
			break;
		default:
			break;
		}
	}

	@Override
	public void success(String s) {
		MyApplication.toast(s);
	}

	@Override
	public void fail(String s) {
		MyApplication.toast(s);
	}
}
