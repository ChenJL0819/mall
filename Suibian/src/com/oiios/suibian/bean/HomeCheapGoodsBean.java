package com.oiios.suibian.bean;

/**
 * HomeFragment中的特价商品实体类
 * 
 * @author admim
 *
 */
public class HomeCheapGoodsBean {
	private String url;// 点击后的跳转URL
	private String name;// 商品名字
	private String price;// 商品价格
	private String imageUrl;// 商品图标的URL
	private String describe;// 商品描述
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	@Override
	public String toString() {
		return "CategoryGoodsBean [url=" + url + ", name=" + name + ", price=" + price + ", imageUrl=" + imageUrl
				+ ", describe=" + describe + "]";
	}

}
