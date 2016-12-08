package com.oiios.suibian.listener;

import java.util.List;

import com.oiios.suibian.bean.SearchGoodsBean;

public interface SearchGoodsListener {
	void loadSearchGoodsComplete(List<SearchGoodsBean> list);
}
