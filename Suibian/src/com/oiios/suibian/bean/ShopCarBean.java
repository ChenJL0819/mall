package com.oiios.suibian.bean;

import cn.bmob.v3.BmobObject;

/**
 * ���ﳵʵ����
 * @author admim
 *
 */
public class ShopCarBean extends BmobObject{
	private String userId;//�û�id
	private String storeName;//������
	private float nowPrice;//��Ʒ�۸�
	private float oldPrice;//֮ǰ��Ʒ�۸�
	private String imgUrl;//��ƷͼƬ��URL
	private String goodsUrl;//��Ʒ��URL
	private int goodsCount;//��Ʒ����
	private String goodsId;//��Ʒid
//	private String favorableInfo;//�Ż���Ϣ
	private String descible;//��Ʒ����
	private boolean checked;
	
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getUserId() {
		return userId;
	}
	public float getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(float oldPrice) {
		this.oldPrice = oldPrice;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public float getNowPrice() {
		return nowPrice;
	}
	public void setNowPrice(float nowPrice) {
		this.nowPrice = nowPrice;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getGoodsUrl() {
		return goodsUrl;
	}
	public void setGoodsUrl(String goodsUrl) {
		this.goodsUrl = goodsUrl;
	}
	public int getGoodsCount() {
		return goodsCount;
	}
	public void setGoodsCount(int goodsCount) {
		this.goodsCount = goodsCount;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getDescible() {
		return descible;
	}
	public void setDescible(String descible) {
		this.descible = descible;
	}
	@Override
	public String toString() {
		return "ShopCarBean [userId=" + userId + ", storeName=" + storeName + ", nowPrice=" + nowPrice + ", imgUrl="
				+ imgUrl + ", goodsUrl=" + goodsUrl + ", goodsCount=" + goodsCount + ", goodsId=" + goodsId
			    + ", descible=" + descible + "]";
	}
}
