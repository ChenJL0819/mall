package com.oiios.suibian.bean;

import android.widget.ImageView;

public class HomePagerBean {
	private ImageView iv;
	private String url;//点击图片后的跳转
	public ImageView getIv() {
		return iv;
	}
	public void setIv(ImageView iv) {
		this.iv = iv;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public HomePagerBean(ImageView iv, String url) {
		super();
		this.iv = iv;
		this.url = url;
	}
	public HomePagerBean() {
		super();
	}
}
