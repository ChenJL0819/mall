package com.oiios.suibian.bean;

import cn.bmob.v3.BmobObject;

/**
 * 收货地址实体类
 * @author admim
 *
 */
public class AddressBean extends BmobObject {
	private String username;
	private String consignee;//收货人
	private String phone;//收货人电话
	private String address;//收货人地址
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "AddressBean [username=" + username + ", consignee=" + consignee + ", phone=" + phone + ", address="
				+ address + "]";
	}
	
}
