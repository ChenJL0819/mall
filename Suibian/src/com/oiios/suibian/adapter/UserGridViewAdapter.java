package com.oiios.suibian.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.oiios.suibian.R;


public class UserGridViewAdapter extends MyBaseAdapter<Integer> {
	
	public UserGridViewAdapter(Context context) {
		super(context);
	}

	class ViewHolder{
		private ImageView ivUser;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.inflate_user_item, null);
			holder = new ViewHolder();
			holder.ivUser = (ImageView) convertView.findViewById(R.id.ivUser);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.ivUser.setImageResource(getItem(position));
		return convertView;
	}

}
