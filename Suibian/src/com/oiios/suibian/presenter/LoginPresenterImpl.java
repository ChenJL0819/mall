package com.oiios.suibian.presenter;

import com.oiios.suibian.bean.User;
import com.oiios.suibian.listener.MyListener;
import com.oiios.suibian.model.LoginModelImpl;
import com.oiios.suibian.mvp.ILoginModel;
import com.oiios.suibian.mvp.ILoginPresenter;
import com.oiios.suibian.mvp.ILoginView;

public class LoginPresenterImpl implements ILoginPresenter{
	private ILoginModel model;
	private ILoginView view;
	
	public LoginPresenterImpl(ILoginView view) {
		super();
		this.model = new LoginModelImpl();
		this.view = view;
	}

	@Override
	public void login(String username, String password) {
		model.login(username, password, new MyListener() {
			@Override
			public void success(String s) {
				view.loginSuccess(s);
			}
			@Override
			public void fail(String s) {
				view.loginFail(s);
			}
		});
	}

	@Override
	public void login(User user) {
		
	}

}
