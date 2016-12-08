package com.oiios.suibian.mvp;

import java.util.List;

import com.oiios.suibian.bean.RecommendListBean;

public interface IRecommendListView extends IView{
	void resultRecommendList(List<RecommendListBean> list);
}
