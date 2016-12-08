package com.oiios.suibian.listener;

import java.util.List;

import com.oiios.suibian.bean.RecommendListBean;

public interface RecommendListListener {
	void loadRecommendListComplete(List<RecommendListBean> list);
}
