package com.oiios.suibian.adapter;

import com.oiios.suibian.R;
import com.oiios.suibian.bean.CategoryGoodsBean;
import com.oiios.suibian.utils.HttpUtil;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class GvCategoryAdapter extends MyBaseAdapter<CategoryGoodsBean>{

	public GvCategoryAdapter(Context context) {
		super(context);
	}

	
	private class ViewHolder{
		TextView tvPrice;
		TextView tvName;
		TextView tvDescribe;
		ImageView iv;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.inflate_category_gv_item,null);
			holder = new ViewHolder();
			holder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			holder.tvDescribe = (TextView) convertView.findViewById(R.id.tvDescribe);
			holder.iv = (ImageView) convertView.findViewById(R.id.ivImg);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		CategoryGoodsBean bean = getItem(position);
		holder.tvPrice.setText(bean.getPrice());
		holder.tvName.setText(bean.getName());
		holder.tvDescribe.setText(bean.getDescribe());
		Log.i("TAG", "bean.getImageUrl() = "+bean.getImageUrl());
		HttpUtil.displayImage(bean.getImageUrl(), holder.iv);
		return convertView;
	}

}
