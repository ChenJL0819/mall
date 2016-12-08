package com.oiios.suibian.bean;

public class RecommendListBean {
	private String imageUrl;
	private String skipUrl;
	private String discountImgUrl;//ÕÛ¿ÛÍ¼Æ¬µÄURL
	private float price;
	private String describe;
	private String storeName;
	public RecommendListBean() {
		super();
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getSkipUrl() {
		return skipUrl;
	}
	public void setSkipUrl(String skipUrl) {
		this.skipUrl = skipUrl;
	}
	public String getDiscountImgUrl() {
		return discountImgUrl;
	}
	public void setDiscountImgUrl(String discountImgUrl) {
		this.discountImgUrl = discountImgUrl;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	@Override
	public String toString() {
		return "RecommendListBean [imageUrl=" + imageUrl + ", skipUrl=" + skipUrl + ", discountImgUrl=" + discountImgUrl
				+ ", price=" + price + ", title=" + describe + "]";
	}
	
}
