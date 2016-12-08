package com.oiios.suibian.presenter;

import java.util.List;

import com.oiios.suibian.bean.SearchGoodsBean;
import com.oiios.suibian.listener.SearchGoodsListener;
import com.oiios.suibian.model.SearchGoodsModelImpl;
import com.oiios.suibian.mvp.ISearchGoodsPresenter;
import com.oiios.suibian.mvp.ISearchGoodsView;

public class SearchGoodsPresenterImpl implements ISearchGoodsPresenter {
	private SearchGoodsModelImpl model;
	private ISearchGoodsView view;
	
	public SearchGoodsPresenterImpl(ISearchGoodsView view) {
		super();
		this.model = new SearchGoodsModelImpl();
		this.view = view;
	}

	@Override
	public void loadSearchGoods(String content,int page) {
		if (content == null||content.equals("")||page<1) {
			return;
		}
		model.getSearchGoods(content,page, new SearchGoodsListener() {
			@Override
			public void loadSearchGoodsComplete(List<SearchGoodsBean> list) {
				view.resultSearchGoods(list);
			}
		});
	}

}
