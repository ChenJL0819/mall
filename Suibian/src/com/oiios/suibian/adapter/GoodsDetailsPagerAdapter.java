package com.oiios.suibian.adapter;

import java.util.ArrayList;
import java.util.List;

import com.oiios.suibian.activity.GoodsDetailsActivity;
import com.oiios.suibian.bean.HomePagerBean;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class GoodsDetailsPagerAdapter extends PagerAdapter {
	private List<ImageView> list;

	public GoodsDetailsPagerAdapter(List<ImageView> list) {
		super();
		this.list = list;
	}

	public GoodsDetailsPagerAdapter() {
		list = new ArrayList<ImageView>();
	}

	public void addAll(List<ImageView> list, boolean flag) {
		if (flag) {
			this.list.clear();
		}
		this.list.addAll(list);
		notifyDataSetChanged();
	}

	public void add(ImageView bean) {
		this.list.add(bean);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(list.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView iv = list.get(position);
		container.addView(iv);
		return iv;
	}
}
