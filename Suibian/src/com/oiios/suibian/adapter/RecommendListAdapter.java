package com.oiios.suibian.adapter;

import com.oiios.suibian.R;
import com.oiios.suibian.bean.RecommendListBean;
import com.oiios.suibian.utils.HttpUtil;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecommendListAdapter extends MyBaseAdapter<RecommendListBean> {

	public RecommendListAdapter(Context context) {
		super(context);
	}

	class ViewHolder {
		private TextView tvPrice;
		private TextView tvDescribe;
		private ImageView ivGoods;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.inflate_recommend_list_item, null);
			holder = new ViewHolder();
			holder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
			holder.tvDescribe = (TextView) convertView.findViewById(R.id.tvDescribe);
			holder.ivGoods = (ImageView) convertView.findViewById(R.id.ivGoods);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		RecommendListBean bean = getItem(position);
		holder.tvPrice.setText("гд  " + bean.getPrice());
		holder.tvDescribe.setText(bean.getDescribe());
		Log.i("TAG", bean.getImageUrl());
		HttpUtil.displayImage(bean.getImageUrl(), holder.ivGoods);
		return convertView;
	}
}
