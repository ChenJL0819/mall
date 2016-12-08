package com.oiios.suibian.presenter;

import java.util.List;

import com.oiios.suibian.bean.CategoryGoodsBean;
import com.oiios.suibian.listener.CategoryGoodsListener;
import com.oiios.suibian.model.CategoryModelImpl;
import com.oiios.suibian.mvp.ICategoryGoodsView;
import com.oiios.suibian.mvp.ICategoryModel;
import com.oiios.suibian.mvp.ICategoryPresenter;

public class CategoryPresenterImpl implements ICategoryPresenter{
	private ICategoryModel model;
	private ICategoryGoodsView view;
	
	public CategoryPresenterImpl(ICategoryGoodsView view) {
		super();
		this.model = new CategoryModelImpl();
		this.view = view;
	}

	@Override
	public void getCategoryGoods(String url, int page) {
		model.getCategoryGoods(url, page, new CategoryGoodsListener() {
			@Override
			public void loadCategoryGoodsComplete(List<CategoryGoodsBean> list) {
				view.resultCategoryGoods(list);
			}
		});
	}
	
}
