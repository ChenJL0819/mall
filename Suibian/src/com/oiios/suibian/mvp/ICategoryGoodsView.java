package com.oiios.suibian.mvp;

import java.util.List;

import com.oiios.suibian.bean.CategoryBean;
import com.oiios.suibian.bean.CategoryGoodsBean;

public interface ICategoryGoodsView extends IView {
	void resultCategoryGoods(List<CategoryGoodsBean> list);
	void resultCategory(List<CategoryBean> list);
}
