package com.oiios.suibian.listener;

import java.util.List;

import com.oiios.suibian.bean.ShopCarBean;

// money改变时回调moneyChange()方法
public interface ShopCarChangedListener {
	void moneyChange(float money, float freight);
	void checkedStoreAll(boolean flag);
	void removeCarGoods(int groupPosition,int childPosition);
	void loadShopCarComplete(List<ShopCarBean> list);
	
	void delGoodsSuccess(String s);
	void delGoodsFail(String s);
	//商品数量改变
	void goodsCountSuccess(String s,ShopCarBean bean);
	void goodsCountFail(String s);
}
