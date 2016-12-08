package com.oiios.suibian.mvp;

import com.oiios.suibian.bean.User;
import com.oiios.suibian.listener.MyListener;

public interface IRegisterModel extends IModel{
	void register(User user, MyListener listener);
	void register(String username,String password,Boolean sex,String phone, MyListener listener);
}
