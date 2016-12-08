package com.oiios.suibian.model;

import com.oiios.suibian.activity.MyApplication;
import com.oiios.suibian.bean.User;
import com.oiios.suibian.listener.MyListener;
import com.oiios.suibian.mvp.IRegisterModel;

public class RegisterModelImpl implements IRegisterModel {

	@Override
	public void register(User user, MyListener listener) {
		// �������ύ�û���Ϣ
		BmobManager.addUser(MyApplication.context, user,listener);
	}

	@Override
	public void register(String username, String password, Boolean sex, String phone, MyListener listener) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setMobilePhoneNumber(phone);
		user.setSex(sex);
		// �������ύ�û���Ϣ
		BmobManager.addUser(MyApplication.context, user,listener);
	}

}
