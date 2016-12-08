package com.oiios.suibian.presenter;

import java.util.List;

import com.oiios.suibian.bean.HomeCheapGoodsBean;
import com.oiios.suibian.bean.HomeRecommendGoodsBean;
import com.oiios.suibian.listener.HomeCheapGoodsListener;
import com.oiios.suibian.listener.HomeRecommendGoodsListener;
import com.oiios.suibian.model.HomeModelImpl;
import com.oiios.suibian.mvp.IHomeModel;
import com.oiios.suibian.mvp.IHomePresenter;
import com.oiios.suibian.mvp.IHomeView;

public class HomePresenterImpl implements IHomePresenter{
	private IHomeView view;
	private IHomeModel model;
	
	public HomePresenterImpl(IHomeView view) {
		super();
		this.view = view;
		this.model = new HomeModelImpl();
	}

	@Override
	public void loadRecommendGoods() {
		model.recommendGoods(new HomeRecommendGoodsListener() {
			@Override
			public void loadHomeGoodsComplete(List<HomeRecommendGoodsBean> list) {
				view.resultRecommendGoods(list);
			}

		});
	}

	@Override
	public void loadCheapGoods(int page) {
		model.cheapGoods(page, new HomeCheapGoodsListener() {
			@Override
			public void loadCheapGoodsComplete(List<HomeCheapGoodsBean> list) {
				view.resultCheapGoods(list);				
			}
		});
	}
	
}
