package com.oiios.suibian.mvp;

import com.oiios.suibian.bean.User;
import com.oiios.suibian.listener.MyListener;

public interface ILoginModel extends IModel{
	void login(User user, MyListener listener);
	void login(String username,String password,MyListener listener);

}
