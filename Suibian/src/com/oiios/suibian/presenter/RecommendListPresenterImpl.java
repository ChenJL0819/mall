package com.oiios.suibian.presenter;

import java.util.List;

import com.oiios.suibian.bean.RecommendListBean;
import com.oiios.suibian.listener.RecommendListListener;
import com.oiios.suibian.model.RecommendListModelImpl;
import com.oiios.suibian.mvp.IRecommendListModel;
import com.oiios.suibian.mvp.IRecommendListPresenter;
import com.oiios.suibian.mvp.IRecommendListView;

public class RecommendListPresenterImpl implements IRecommendListPresenter {
	private IRecommendListModel model;
	private IRecommendListView view;
	
	public RecommendListPresenterImpl(IRecommendListView view) {
		super();
		this.model = new RecommendListModelImpl();
		this.view = view;
	}

	@Override
	public void getRecommendListGoods(String url) {
		model.getRecommendList(url, new RecommendListListener() {
			@Override
			public void loadRecommendListComplete(List<RecommendListBean> list) {
				view.resultRecommendList(list);
			}
		});
	}

}
