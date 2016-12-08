package com.oiios.suibian.adapter;

import com.oiios.suibian.R;
import com.oiios.suibian.bean.HomeRecommendGoodsBean;
import com.oiios.suibian.utils.HttpUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class RecommendGoodsAdapter extends MyBaseAdapter<HomeRecommendGoodsBean> {
	public RecommendGoodsAdapter(Context context) {
		super(context);
	}
	class ViewHolder{
		private ImageView ivRecommend;
	}
	@SuppressLint("InflateParams") 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.inflate_recommend_goods_item, null);
			holder = new ViewHolder();
			holder.ivRecommend = (ImageView) convertView.findViewById(R.id.ivRecommend);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		HomeRecommendGoodsBean bean = list.get(position);
		HttpUtil.displayImage(bean.getImageUrl(), holder.ivRecommend);
		return convertView;
	}

}
