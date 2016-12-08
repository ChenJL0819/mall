package com.oiios.suibian.mvp;

import com.oiios.suibian.bean.User;

public interface IRegisterPresenter extends IPresenter {

	void register(String username,String password,Boolean sex,String phone);
	void register(User user);
}
