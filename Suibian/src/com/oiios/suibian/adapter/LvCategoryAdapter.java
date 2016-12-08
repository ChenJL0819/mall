package com.oiios.suibian.adapter;

import com.oiios.suibian.R;
import com.oiios.suibian.bean.CategoryBean;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LvCategoryAdapter extends MyBaseAdapter<CategoryBean> {
	private class ViewHolder {
		TextView tvCategoryName;
	}

	public LvCategoryAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.inflate_category_lv_item, null);
			holder = new ViewHolder();
			holder.tvCategoryName = (TextView) convertView.findViewById(R.id.tvCategoryName);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvCategoryName.setText(getItem(position).getName());
		return convertView;
	}

}
