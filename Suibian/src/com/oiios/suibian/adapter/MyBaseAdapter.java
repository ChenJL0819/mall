package com.oiios.suibian.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<T> extends BaseAdapter {
	protected List<T> list;
	protected Context context;
	protected LayoutInflater inflater;

	public MyBaseAdapter(Context context) {
		super();
		this.list = new ArrayList<T>();
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	// ���һ������
	public void add(T t) {
		list.add(t);
		notifyDataSetChanged();
	}

	// ��Ӷ�������
	public void addAll(List<T> list, boolean isClear) {
		if (isClear) {
			this.list.clear();
		}
		this.list.addAll(list);
		notifyDataSetChanged();
	}

	// �Ƴ�ĳ��Ԫ��
	public void remove(T t) {
		if (list.contains(t)) {
			list.remove(t);
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public T getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
