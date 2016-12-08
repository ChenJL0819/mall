package com.oiios.suibian.bean;

import java.util.List;

public class GoodsDetatilsBean {
	private String title;
	private String describe;//��Ʒ����
	private float nowPrice;
	private float oldPrice;
	private float grade;//����
	private int appraiseCount;//��������
	private String storeName;//������
	private String goodId;//��Ʒid
	private String goodsUrl;//��Ʒ��URL:���ﳵ�õ�
	private List<String> imageUrls;//ͼƬ��URL
	private String favorableInfo;//�Ż���Ϣ
	
	public GoodsDetatilsBean() {
		super();
	}
	
	public String getGoodsUrl() {
		return goodsUrl;
	}

	public void setGoodsUrl(String goodsUrl) {
		this.goodsUrl = goodsUrl;
	}

	public List<String> getImageUrls() {
		return imageUrls;
	}


	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
	}


	public String getFavorableInfo() {
		return favorableInfo;
	}

	public void setFavorableInfo(String favorableInfo) {
		this.favorableInfo = favorableInfo;
	}

	public int getAppraiseCount() {
		return appraiseCount;
	}

	public void setAppraiseCount(int appraiseCount) {
		this.appraiseCount = appraiseCount;
	}

	public List<String> getImageUrl() {
		return imageUrls;
	}
	public void setImageUrl(List<String> imageUrl) {
		this.imageUrls = imageUrl;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public float getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(float nowPrice) {
		this.nowPrice = nowPrice;
	}


	public float getOldPrice() {
		return oldPrice;
	}


	public void setOldPrice(float oldPrice) {
		this.oldPrice = oldPrice;
	}


	public float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	@Override
	public String toString() {
		return "GoodsDetatilsBean [title=" + title + ", describe=" + describe + ", nowPrice=" + nowPrice + ", oldPrice="
				+ oldPrice + ", grade=" + grade + ", storeName=" + storeName + ", goodId=" + goodId + "]";
	}
	
	
}
