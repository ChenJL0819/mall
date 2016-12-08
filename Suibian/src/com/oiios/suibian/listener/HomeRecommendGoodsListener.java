package com.oiios.suibian.listener;

import java.util.List;

import com.oiios.suibian.bean.HomeRecommendGoodsBean;
public interface HomeRecommendGoodsListener {
	void loadHomeGoodsComplete(List<HomeRecommendGoodsBean> list);
}
