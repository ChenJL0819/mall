package com.oiios.suibian.mvp;

import java.util.List;

import com.oiios.suibian.bean.SearchGoodsBean;

public interface ISearchGoodsView extends IView{
	void resultSearchGoods(List<SearchGoodsBean> list);
}
