package com.oiios.suibian.mvp;

import com.oiios.suibian.listener.GoodsDetailsListener;

public interface IGoodsDetailsModel extends IModel{
	void getGoodsDetails(String goodsId,GoodsDetailsListener listener);
}
