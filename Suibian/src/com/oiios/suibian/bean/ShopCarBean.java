package com.oiios.suibian.bean;

import cn.bmob.v3.BmobObject;

/**
 * 购物车实体类
 * @author admim
 *
 */
public class ShopCarBean extends BmobObject{
	private String userId;//用户id
	private String storeName;//店铺名
	private float nowPrice;//商品价格
	private float oldPrice;//之前商品价格
	private String imgUrl;//商品图片的URL
	private String goodsUrl;//商品的URL
	private int goodsCount;//商品数量
	private String goodsId;//商品id
//	private String favorableInfo;//优惠信息
	private String descible;//商品描述
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
