package com.oiios.suibian.adapter;

import com.oiios.suibian.R;
import com.oiios.suibian.bean.AddressBean;
import com.oiios.suibian.listener.AddressListener;
import com.oiios.suibian.model.BmobManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

public class AddressAdapter extends MyBaseAdapter<AddressBean> {
	private AddressListener listener;
	public AddressAdapter(Context context,AddressListener listener) {
		super(context);
		this.listener = listener;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.inflate_address_item, null);
			holder.tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
			holder.tvPhone = (TextView) convertView.findViewById(R.id.tvPhone);
			holder.tvConsigneeName = (TextView) convertView.findViewById(R.id.tvConsigneeName);
			holder.cbEdit = (CheckBox) convertView.findViewById(R.id.cbEdit);
			holder.cbDel = (CheckBox) convertView.findViewById(R.id.cbDel);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		final AddressBean bean = getItem(position);
		holder.tvAddress.setText(bean.getAddress());
		holder.tvPhone.setText(bean.getPhone());
		holder.tvConsigneeName.setText(bean.getConsignee());
		holder.cbDel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(context)
				.setMessage("�Ƿ�ɾ�����ջ���ַ")
				.setNegativeButton("ȡ��", null)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						BmobManager.deleteAddress(context, bean, listener);
					}
				})
				.show();
				
			}
		});
		return convertView;
	}

	private class ViewHolder {
		TextView tvConsigneeName;// �ջ�������
		TextView tvPhone;
		TextView tvAddress;
		CheckBox cbEdit;
		CheckBox cbDel;
	}

}
