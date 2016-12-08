package com.oiios.suibian.mvp;

import com.oiios.suibian.listener.HomeCheapGoodsListener;
import com.oiios.suibian.listener.HomeRecommendGoodsListener;

/**
 * http://www.bdysc.com/
 */
public interface IHomeModel extends IModel {
	void recommendGoods(HomeRecommendGoodsListener listener);
	void cheapGoods(int page,HomeCheapGoodsListener listener);
	
}
