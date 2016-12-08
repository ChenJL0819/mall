package com.oiios.suibian.activity;

import com.oiios.suibian.R;
import com.oiios.suibian.fragment.CategoryFragment;
import com.oiios.suibian.fragment.HomeFragment;
import com.oiios.suibian.fragment.MyFragment;
import com.oiios.suibian.fragment.ShoppingCarFragment;
import com.oiios.suibian.utils.TitleUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends BaseActivity implements OnCheckedChangeListener {
	private RadioGroup rGroup;
	private View frameLayout;

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		MyApplication.listActivity.add(this);
		TitleUtil.setSystemBarColor(this, R.color.mainColor);
		
		initView();
		bindListener();
		RadioButton rButton = (RadioButton) rGroup.getChildAt(0);
		rButton.setChecked(true);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		String key = intent.getStringExtra("Key");
		if ("car".equals(key)) {
			RadioButton rButton = (RadioButton) rGroup.getChildAt(2);
			rButton.setChecked(true);
		}
	}

	@Override
	public void initView() {
		frameLayout = findViewById(R.id.frameLayout);
		rGroup = (RadioGroup) findViewById(R.id.radioGroup);
	}

	@Override
	public void bindListener() {
		rGroup.setOnCheckedChangeListener(this);
	}

	Fragment homeFragment, categoryFragment, myFragment, shoppingCarFragment;
	String[] tags = { "home", "category", "my", "car" };

	/**
	 * 使用attach和detach优化：不用重新new对象
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		FragmentManager fManager = getFragmentManager();
		FragmentTransaction ft = fManager.beginTransaction();
//		detachFrgs(fManager, ft);
		if (checkedId == R.id.radio0) {// 主页
//			if (homeFragment == null) {
//				homeFragment = new HomeFragment();
//				ft.add(R.id.frameLayout, homeFragment, "home");
//			} else {
//				ft.show(homeFragment);
//			}
			ft.replace(R.id.frameLayout, new HomeFragment());
		} else if (checkedId == R.id.radio3) {// 分类
//			if (categoryFragment == null) {
//				categoryFragment = new CategoryFragment();
//				ft.add(R.id.frameLayout, categoryFragment, "category");
//			} else {
//				ft.show(categoryFragment);
//			}
			ft.replace(R.id.frameLayout, new CategoryFragment());
		} else if (checkedId == R.id.radio1) {// 购物车
//			if (shoppingCarFragment == null) {
//				shoppingCarFragment = new ShoppingCarFragment();
//				ft.add(R.id.frameLayout, shoppingCarFragment, "car");
//			} else {
//				ft.show(shoppingCarFragment);
//			}
			ft.replace(R.id.frameLayout, new ShoppingCarFragment());
		} else if (checkedId == R.id.radio2) {
//			if (myFragment == null) {
//				myFragment = new MyFragment();
//				ft.add(R.id.frameLayout, myFragment, "my");
//			} else {
//				ft.show(myFragment);
//			}
			ft.replace(R.id.frameLayout, new MyFragment());
		}
		ft.commit();
	}

	public void detachFrgs(FragmentManager fManager, FragmentTransaction ft) {
		for (int i = 0; i < tags.length; i++) {
			Fragment frg = fManager.findFragmentByTag(tags[i]);
			if (frg != null)
				ft.hide(frg);
		}
	}

	// 退出程序
	boolean flag = false;

	@Override
	public void onBackPressed() {
		if (flag) {
			for (Activity activity : MyApplication.listActivity) {
				activity.finish();
			}
		} else {
			toast("再按一次退出程序");
			flag = true;
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					flag = false;
				}
			}, 3000);
		}
	}
}
