package com.oiios.suibian.activity;

import com.oiios.suibian.R;
import com.oiios.suibian.mvp.ILoginView;
import com.oiios.suibian.presenter.LoginPresenterImpl;
import com.oiios.suibian.utils.FileUtil;
import com.oiios.suibian.utils.NetUtil;
import com.oiios.suibian.utils.TitleUtil;
import com.oiios.suibian.view.CircleImageView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends BaseActivity implements OnClickListener, ILoginView {
	CircleImageView ivIcon;
	EditText etUsername;
	EditText etPassword;
	Button btnLogin;
	TextView tvRegister;
	private LoginPresenterImpl presenter;

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		TitleUtil.setSystemBarColor(this, R.color.mainColor);
		MyApplication.listActivity.add(this);
		presenter = new LoginPresenterImpl(this);
		initView();
		bindListener();
	}

	@Override
	public void initView() {
		ivIcon = (CircleImageView) findViewById(R.id.ivIcon);
		etPassword = (EditText) findViewById(R.id.etPassword);
		etUsername = (EditText) findViewById(R.id.etUsername);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		tvRegister = (TextView) findViewById(R.id.tvRegister);
		initData();
	}

	public void initData() {
		SharedPreferences preferences = getSharedPreferences("suibian",MODE_PRIVATE);
		etUsername.setText(preferences.getString("username", ""));
		Bitmap bitmap = FileUtil.getDiskBitmap(preferences.getString("filePath", null));
		if (bitmap != null) {
			ivIcon.setVisibility(View.VISIBLE);
			ivIcon.setImageBitmap(bitmap);
		}
		if (!NetUtil.isNetworkAvailable(this)) {
			MyApplication.toast("Çë¼ì²éÄãµÄÍøÂç");
		}
	}

	@Override
	public void bindListener() {
		btnLogin.setOnClickListener(this);
		tvRegister.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLogin:
			if (!NetUtil.isNetworkAvailable(this)) {
				MyApplication.toast("Çë¼ì²éÄãµÄÍøÂç");
				return;
			}
			String username = etUsername.getText().toString();
			String password = etPassword.getText().toString();
			SharedPreferences.Editor editor = getSharedPreferences("suibian", MODE_PRIVATE).edit();
			editor.putString("username", username);
			editor.commit();
			presenter.login(username, password);
			break;
		case R.id.tvRegister:
			Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	public void loginSuccess(final String s) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				MyApplication.toast(s);
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});

	}

	@Override
	public void loginFail(final String s) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				MyApplication.toast(s);
			}
		});
	}

}
