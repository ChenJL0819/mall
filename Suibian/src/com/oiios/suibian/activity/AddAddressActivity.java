package com.oiios.suibian.activity;

import com.oiios.suibian.R;
import com.oiios.suibian.bean.AddressBean;
import com.oiios.suibian.listener.AddAddressListener;
import com.oiios.suibian.model.BmobManager;
import com.oiios.suibian.utils.TitleUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.bmob.v3.BmobUser;

public class AddAddressActivity extends BaseActivity implements OnClickListener,AddAddressListener {
	private LinearLayout layoutReturn;
	private TextView tvSave;
	private EditText etName;
	private EditText etTel;
	private EditText etDistrict;
	private EditText etStreet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_address);
		TitleUtil.setSystemBarColor(this, R.color.mainColor);
		initView();
		bindListener();
	}

	@Override
	public void initView() {
		layoutReturn = (LinearLayout) findViewById(R.id.layoutReturn);
		tvSave = (TextView) findViewById(R.id.tvSave);
		etName = (EditText) findViewById(R.id.etName);
		etTel = (EditText) findViewById(R.id.etTel);
		etDistrict = (EditText) findViewById(R.id.etDistrict);
		etStreet = (EditText) findViewById(R.id.etStreet);
	}

	@Override
	public void bindListener() {
		tvSave.setOnClickListener(this);
		layoutReturn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvSave:
			String username = BmobUser.getCurrentUser(this).getUsername();
			String district = etDistrict.getText().toString();
			String phone = etTel.getText().toString();
			String address = etStreet.getText().toString();
			String consigneeName = etName.getText().toString();
			if (username == null || consigneeName == null || address == null || phone == null) {
				MyApplication.toast("²»ÄÜÎª¿Õ");
				return;
			}
			AddressBean bean = new AddressBean();
			bean.setAddress(district+address);
			bean.setConsignee(consigneeName);
			bean.setUsername(username);
			bean.setPhone(phone);
			BmobManager.addAddress(this, bean, this);
			break;
		case R.id.layoutReturn:
			Intent intent = new Intent(AddAddressActivity.this, AddressActivity.class);
			startActivity(intent);
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void success(String s) {
		MyApplication.toast(s);
		Intent intent = new Intent(AddAddressActivity.this, AddressActivity.class);
		startActivity(intent);
		finish();
	}
	@Override
	public void fail(String s) {
		MyApplication.toast(s);
	}
}
