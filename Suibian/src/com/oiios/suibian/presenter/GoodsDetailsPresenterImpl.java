package com.oiios.suibian.presenter;

import com.oiios.suibian.bean.GoodsDetatilsBean;
import com.oiios.suibian.listener.GoodsDetailsListener;
import com.oiios.suibian.model.GoodsDetailsModelIpml;
import com.oiios.suibian.mvp.IGoodsDetailsPresenter;
import com.oiios.suibian.mvp.IGoodsDetailsView;

public class GoodsDetailsPresenterImpl implements IGoodsDetailsPresenter {
	private GoodsDetailsModelIpml model;
	private IGoodsDetailsView view;
	
	
	public GoodsDetailsPresenterImpl(IGoodsDetailsView view) {
		super();
		this.model = new GoodsDetailsModelIpml();
		this.view = view;
	}


	@Override
	public void loadGoodsDetails(String goodsUrl) {
		model.getGoodsDetails(goodsUrl,new GoodsDetailsListener() {
			@Override
			public void loadGoodsDetailsComplete(GoodsDetatilsBean bean) {
				view.resultGoodsDetails(bean);
			}
		});
	}

}
