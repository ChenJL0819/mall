package com.oiios.suibian.adapter;

import java.util.Random;

import com.oiios.suibian.R;
import com.oiios.suibian.bean.SearchGoodsBean;
import com.oiios.suibian.utils.HttpUtil;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchGoodsAdapter extends MyBaseAdapter<SearchGoodsBean> {

	public SearchGoodsAdapter(Context context) {
		super(context);
	}

	private class ViewHolder {
		TextView tvPrice;
		TextView tvName;
		TextView tvDescribe;
		ImageView iv;
	}
	int seed = 1;
	Random random = new Random(1000);
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			int i = random.nextInt(seed++) % 3;
			Log.i("TAG", "i == "+i);
			if (i == 0) {
				convertView = inflater.inflate(R.layout.inflate_cheap_goods_item, null);
			} else if (i == 1) {
				convertView = inflater.inflate(R.layout.inflate_cheap_goods_item2,null);
			}else{
				convertView = inflater.inflate(R.layout.inflate_cheap_goods_item3,null);
			}
			holder = new ViewHolder();
			holder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			holder.tvDescribe = (TextView) convertView.findViewById(R.id.tvDescribe);
			holder.iv = (ImageView) convertView.findViewById(R.id.ivImg);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		SearchGoodsBean bean = getItem(position);
		holder.tvPrice.setText(bean.getPrice());
		holder.tvName.setText(bean.getName());
		holder.tvDescribe.setText(bean.getDescribe());
		HttpUtil.displayImage(bean.getImageUrl(), holder.iv);
		return convertView;
	}

}
