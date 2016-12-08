package com.oiios.suibian.activity;

import com.oiios.suibian.R;
import com.oiios.suibian.mvp.IRegisterView;
import com.oiios.suibian.presenter.RegisterPresenterImpl;
import com.oiios.suibian.utils.PhoneUtil;
import com.oiios.suibian.utils.TitleUtil;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends BaseActivity implements OnClickListener, IRegisterView {
	private EditText etUsername;
	private EditText etPassword;
	private EditText etRepassword;
	private RadioButton radio0;
	private RadioButton radio1;
	private EditText etPhone;
	private LinearLayout linerlayout_code;
	private EditText etCode;
	private Button btnGetCode;
	private Button btnRegist;
	private int time = 120;
	private RegisterPresenterImpl presenter;

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		TitleUtil.setSystemBarColor(this, R.color.mainColor);
		presenter = new RegisterPresenterImpl(this);

		initView();
		// 短信验证码
		SMSSDK.initSDK(this, "189a9148781f0", "da63ca759b76d216e985876832731436");
		initMobSDK();
		bindListener();
	}

	@Override
	public void initView() {
		myActionBar = (RelativeLayout) findViewById(R.id.myActionBar);
		initActionBar(R.drawable.previous, "注册", -1);
		etUsername = (EditText) findViewById(R.id.etUsername);
		etPassword = (EditText) findViewById(R.id.etPassword);
		etRepassword = (EditText) findViewById(R.id.etRepassword);
		radio0 = (RadioButton) findViewById(R.id.radio0);
		radio1 = (RadioButton) findViewById(R.id.radio1);
		etPhone = (EditText) findViewById(R.id.etPhone);
		linerlayout_code = (LinearLayout) findViewById(R.id.linerlayout_code);
		etCode = (EditText) findViewById(R.id.etCode);
		btnGetCode = (Button) findViewById(R.id.btnGetCode);
		btnRegist = (Button) findViewById(R.id.btnRegist);
	}

	@Override
	public void bindListener() {
		ivHeadLeft.setOnClickListener(this);
		btnRegist.setOnClickListener(this);
		btnGetCode.setOnClickListener(this);
	}

	// 验证码
	private void initMobSDK() {
		EventHandler eh = new EventHandler() {
			@Override
			public void afterEvent(int event, int result, Object data) {
				if (result == SMSSDK.RESULT_COMPLETE) {
					// 回调完成
					Log.i("TAG", "回调完成");
					if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
						// 提交验证码成功
						Log.i("TAG", "提交验证码成功");
						saveUserInfo();
					} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
						Log.i("TAG", "event == SMSSDK.EVENT_GET_VERIFICATION_CODE");
						// 获取验证码成功
						runOnUiThread(new Runnable() {
							public void run() {
								MyApplication.toast("获取验证码成功");
							}
						});
					} else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
						// 返回支持发送验证码的国家列表
					}
				} else {
					Log.i("TAG", "initMobSDK失败");
					((Throwable) data).printStackTrace();
				}
			}
		};
		SMSSDK.registerEventHandler(eh); // 注册短信回调
	}

	// 保存用户信息
	private void saveUserInfo() {
		String username = etUsername.getText().toString();
		String password = etPassword.getText().toString();
		String phone = etPhone.getText().toString();
		Boolean sex = radio0.isChecked();
		presenter.register(username, password, sex, phone);
	}

	// 改变获取验证码按钮的点击状态
	Handler handler = new Handler();
	private void changeButtonState() {
		btnGetCode.setClickable(false);
		time = 120;
		Runnable run = new Runnable() {
			@Override
			public void run() {
				if (time >= 0) {
					btnGetCode.setText(time + "s后可重发");
					handler.postDelayed(this, 1000);
					time--;
				} else {
					btnGetCode.setText("获取验证码");
					btnGetCode.setClickable(true);
				}
			}
		};
		handler.postDelayed(run, 1000);
	}
	
	//判断输入是否为空
	public boolean isEmptys(EditText ...editTexts){
		boolean flag = false;//不为空
		for (EditText editText : editTexts) {
			String str = editText.getText().toString();
			if ("".equals(str) || str == null) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	
	@Override
	public void onClick(View v) {
		final String phone = etPhone.getText().toString();
		switch (v.getId()) {
		case R.id.btnRegist:
			if(isEmptys(etPhone,etUsername,etPassword,etRepassword,etCode)){
				toast("信息不能为空");
				return;
			};
			final String code = etCode.getText().toString();
			String password = etPassword.getText().toString();
			String repassword = etRepassword.getText().toString();
			if (!password.equals(repassword)) {
				toast("两次密码不一致");
				return;
			}
			
			//默认为1111也能通过注册
			if ("1111".equals(code)) {
				saveUserInfo();
				return;
			}
			// 提交验证码
			if (PhoneUtil.isTel(phone)) {
				SMSSDK.submitVerificationCode("86", phone, code);
			} else {
				Toast.makeText(RegisterActivity.this, "号码格式不正确", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.btnGetCode:
			changeButtonState();
			SMSSDK.getVerificationCode("86", phone, new OnSendMessageHandler() {
				@Override
				public boolean onSendMessage(String arg0, String arg1) {
					boolean flag = PhoneUtil.isTel(phone);
					return !flag;
				}
			});
			break;
		case R.id.ivHeadLeft:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		SMSSDK.unregisterAllEventHandler();
	}

	@Override
	public void registerResult(final String s, final boolean flag) {
		Log.i("TAG", "registerResult");
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				MyApplication.toast(s);
				if (flag) {// 成功
					Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				}
			}
		});

	}
}
