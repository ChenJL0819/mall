package com.oiios.suibian.bean;
/**
 * HomeFragment���Ƽ���Ʒ��ʵ����
 * @author admim
 *
 */
public class HomeRecommendGoodsBean {
	private String skipUrl;//�������ת��URL
	private String imageUrl;//ͼƬ��URL
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
