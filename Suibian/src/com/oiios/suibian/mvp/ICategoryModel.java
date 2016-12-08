package com.oiios.suibian.mvp;

import com.oiios.suibian.listener.CategoryGoodsListener;

public interface ICategoryModel extends IModel{
	void getCategoryGoods(String url, int page,CategoryGoodsListener listener);
}
