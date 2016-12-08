package com.oiios.suibian.presenter;

import com.oiios.suibian.bean.User;
import com.oiios.suibian.listener.MyListener;
import com.oiios.suibian.model.RegisterModelImpl;
import com.oiios.suibian.mvp.IRegisterModel;
import com.oiios.suibian.mvp.IRegisterPresenter;
import com.oiios.suibian.mvp.IRegisterView;

import android.util.Log;

public class RegisterPresenterImpl implements IRegisterPresenter{
	private IRegisterModel model;
	private IRegisterView view;
	
	public RegisterPresenterImpl(IRegisterView view) {
		super();
		this.model = new RegisterModelImpl();
		this.view = view;
	}
	
	@Override
	public void register(String username, String password, Boolean sex, String phone) {
		Log.i("TAG", "register");
		model.register(username, password, sex, phone, new MyListener() {
			@Override
			public void success(String s) {
				Log.i("TAG", "success");
				view.registerResult(s,true);
			}
			@Override
			public void fail(String s) {
				Log.i("TAG", "fail");
				view.registerResult(s,false);
			}
		});
	}

	@Override
	public void register(User user) {
		
	}
}
