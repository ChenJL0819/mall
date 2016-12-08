package com.oiios.suibian.activity;

import com.oiios.suibian.R;
import com.oiios.suibian.utils.TitleUtil;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ModifyInfoActivity extends BaseActivity implements OnClickListener {
	private TextView tvContent;
	private EditText etContent;
	private EditText etCode;
	private Button btnGetCode;
	private Button btnConfirm;
	
	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_info);
		TitleUtil.setSystemBarColor(this, R.color.mainColor);
		initView();
		bindListener();
	}
	
	public void initView(){
		myActionBar = (RelativeLayout) findViewById(R.id.myActionBar);
		initActionBar(R.drawable.previous, "修改用户信息", -1);
		tvContent = (TextView) findViewById(R.id.tvContent);
		etContent = (EditText) findViewById(R.id.etContent);
		etCode = (EditText) findViewById(R.id.etCode);
		btnGetCode = (Button) findViewById(R.id.btnGetCode);
		btnConfirm = (Button) findViewById(R.id.btnConfirm);
	}
	
	public void bindListener(){
		btnGetCode.setOnClickListener(this);
		btnConfirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
	}
}
