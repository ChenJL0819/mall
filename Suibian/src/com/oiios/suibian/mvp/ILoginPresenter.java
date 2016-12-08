package com.oiios.suibian.mvp;

import com.oiios.suibian.bean.User;

public interface ILoginPresenter extends IPresenter{
	void login(String username,String password);
	void login(User user);
}
