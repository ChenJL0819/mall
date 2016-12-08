package com.oiios.suibian.activity;

import java.util.List;

import com.oiios.suibian.R;
import com.oiios.suibian.adapter.AddressAdapter;
import com.oiios.suibian.bean.AddressBean;
import com.oiios.suibian.listener.AddressListener;
import com.oiios.suibian.model.BmobManager;
import com.oiios.suibian.utils.TitleUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import cn.bmob.v3.BmobUser;

public class AddressActivity extends BaseActivity implements OnClickListener, AddressListener {
	private ListView lvAddress;
	private AddressAdapter addressAdapter;
	private Button btnAddAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address);
		TitleUtil.setSystemBarColor(this, R.color.mainColor);
		initView();
		bindListener();
		loadData();
	}
	public void loadData(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				String username = BmobUser.getCurrentUser(getApplicationContext()).getUsername();
				BmobManager.searchAddress(getApplicationContext(), username, AddressActivity.this);
			}
		}).start();
	}
	@Override
	public void initView() {
		myActionBar = (RelativeLayout) findViewById(R.id.myActionBar);
		initActionBar(R.drawable.previous, "管理收货地址", -1);
		addressAdapter = new AddressAdapter(this,this);
		lvAddress = (ListView) findViewById(R.id.lvAddress);
		lvAddress.setAdapter(addressAdapter);
		btnAddAddress = (Button) findViewById(R.id.btnAddAddress);
	}

	@Override
	public void bindListener() {
		btnAddAddress.setOnClickListener(this);
		ivHeadLeft.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivHeadLeft:
			finish();
			break;
		case R.id.btnAddAddress:
			Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
			startActivity(intent);
			finish();
			break;
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LOAD_ADDRESS_COMPLETE_WHAT:
				List<AddressBean> list = (List<AddressBean>) msg.obj;
				addressAdapter.addAll(list, true);
				break;
			case DELETE_ADDRESS_SUCCESS_WHAT:
				MyApplication.toast((String)msg.obj);
				addressAdapter.notifyDataSetChanged();;
				break;
			case DELETE_ADDRESS_FAIL_WHAT:
				MyApplication.toast((String)msg.obj);
				break;
			default:
				break;
			}
		};
	};

	public static final int LOAD_ADDRESS_COMPLETE_WHAT = 1;
	public static final int DELETE_ADDRESS_SUCCESS_WHAT = 2;
	public static final int DELETE_ADDRESS_FAIL_WHAT = 3;
	@Override
	public void loadAddressComplete(List<AddressBean> list) {
		Message msg = Message.obtain();
		msg.what = LOAD_ADDRESS_COMPLETE_WHAT;
		msg.obj = list;
		handler.sendMessage(msg);
	}
	@Override
	public void deleteSuccess(String s) {
		Message msg = Message.obtain();
		msg.what = LOAD_ADDRESS_COMPLETE_WHAT;
		msg.obj = s;
		handler.sendMessage(msg);
	}
	@Override
	public void deleteFail(String s) {
		Message msg = Message.obtain();
		msg.what = LOAD_ADDRESS_COMPLETE_WHAT;
		msg.obj = s;
		handler.sendMessage(msg);
	}
}
