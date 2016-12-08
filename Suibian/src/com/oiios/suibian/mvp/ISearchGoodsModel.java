package com.oiios.suibian.mvp;

import com.oiios.suibian.listener.SearchGoodsListener;

public interface ISearchGoodsModel extends IModel {
	void getSearchGoods(String content,int page,SearchGoodsListener listener);
}
