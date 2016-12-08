package com.oiios.suibian.mvp;

import java.util.List;

import com.oiios.suibian.bean.HomeCheapGoodsBean;
import com.oiios.suibian.bean.HomeRecommendGoodsBean;

public interface IHomeView {
	void resultRecommendGoods(List<HomeRecommendGoodsBean> list);
	void resultCheapGoods(List<HomeCheapGoodsBean> list);
}
