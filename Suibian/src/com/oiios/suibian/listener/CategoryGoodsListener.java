package com.oiios.suibian.listener;

import java.util.List;

import com.oiios.suibian.bean.CategoryGoodsBean;

public interface CategoryGoodsListener {
	void loadCategoryGoodsComplete(List<CategoryGoodsBean> list);
}
