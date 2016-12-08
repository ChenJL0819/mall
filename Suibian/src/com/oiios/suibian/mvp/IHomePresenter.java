package com.oiios.suibian.mvp;

public interface IHomePresenter extends IPresenter {
	void loadRecommendGoods();
	void loadCheapGoods(int page);
}
