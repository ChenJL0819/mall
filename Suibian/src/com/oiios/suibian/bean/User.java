package com.oiios.suibian.bean;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser{
	private static final long serialVersionUID = 1L;
	private String nickname;//êÇ³Æ
	private boolean sex;//ÐÔ±ð
	private String avatar;//Í·ÏñURL
	
	public User() {
		super();
	}
	public User(String user_account, String password,String nick_name, boolean sex, String phone,
			String avatarUrl) {
		this();
		this.sex = sex;
		this.avatar = avatarUrl;
		this.nickname = nick_name;
	}
	public boolean getSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public String getAvatar() {
		return avatar;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatar = avatarUrl;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
