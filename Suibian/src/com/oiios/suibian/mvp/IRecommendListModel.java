package com.oiios.suibian.mvp;

import com.oiios.suibian.listener.RecommendListListener;

public interface IRecommendListModel extends IModel{
	void getRecommendList(String url,RecommendListListener listener);
}
