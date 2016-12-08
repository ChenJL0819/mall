package com.oiios.suibian.model;

import com.oiios.suibian.activity.MyApplication;
import com.oiios.suibian.bean.User;
import com.oiios.suibian.listener.MyListener;
import com.oiios.suibian.mvp.ILoginModel;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class LoginModelImpl implements ILoginModel {

	@Override
	public void login(User user, MyListener listener) {

	}

	@Override
	public void login(String username, String password, final MyListener listener) {
		BmobUser.loginByAccount(MyApplication.context, username, password, new LogInListener<User>() {
			@Override
			public void done(User arg0, BmobException arg1) {
				if (arg0 != null) {
					listener.success("µÇÂ¼³É¹¦");
				} else {
					listener.fail("µÇÂ¼Ê§°Ü" + arg1.getMessage());
				}

			}
		});
	}

}
