package com.oiios.suibian.listener;

import java.util.List;

import com.oiios.suibian.bean.AddressBean;

public interface AddressListener {
	void loadAddressComplete(List<AddressBean> list);
	void deleteSuccess(String s);
	void deleteFail(String s);
}
