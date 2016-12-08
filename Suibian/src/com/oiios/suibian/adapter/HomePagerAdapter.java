package com.oiios.suibian.adapter;

import java.util.ArrayList;
import java.util.List;

import com.oiios.suibian.activity.GoodsDetailsActivity;
import com.oiios.suibian.activity.SearchGoodsActivity;
import com.oiios.suibian.bean.HomePagerBean;
import com.oiios.suibian.model.HttpData;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class HomePagerAdapter extends PagerAdapter {
	private List<ImageView> list;
	private Context context;

	public HomePagerAdapter(List<ImageView> list,Context context) {
		super();
		this.list = list;
		this.context =context;
	}

	public HomePagerAdapter() {
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
	public Object instantiateItem(ViewGroup container, final int position) {
		ImageView iv = list.get(position);
		container.addView(iv);
		iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, SearchGoodsActivity.class);
				intent.putExtra("SearchKey", HttpData.content[position]);
				context.startActivity(intent);
			}
		});
		return iv;
	}
}
