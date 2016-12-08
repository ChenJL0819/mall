package com.oiios.suibian.bean;
/**
 * HomeFragment中推荐商品的实体类
 * @author admim
 *
 */
public class HomeRecommendGoodsBean {
	private String skipUrl;//点击后跳转的URL
	private String imageUrl;//图片的URL
	public String getSkipUrl() {
		return skipUrl;
	}
	public void setSkipUrl(String skipUrl) {
		this.skipUrl = skipUrl;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public HomeRecommendGoodsBean(String skipUrl, String imageUrl) {
		super();
		this.skipUrl = skipUrl;
		this.imageUrl = imageUrl;
	}
	public HomeRecommendGoodsBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "HomeRecommendGoodsBean [skipUrl=" + skipUrl + ", imageUrl=" + imageUrl + "]";
	}
	
}
