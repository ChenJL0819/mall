package com.oiios.suibian.mvp;

import com.oiios.suibian.bean.GoodsDetatilsBean;

public interface IGoodsDetailsView extends IView{
	void resultGoodsDetails(GoodsDetatilsBean bean);
	void resultGoodsToCar(String result);
}
