package com.oiios.suibian.bean;
/**
 * CategoryFragment�е���Ʒʵ����
 * @author admim
 *
 */
public class CategoryGoodsBean {
	private String url;//��ת��URL
	private String name;//��Ʒ����
	private String price;//��Ʒ�۸�
	private String imageUrl;//��ƷͼƬ��URL
	private String describe;//��Ʒ������
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
