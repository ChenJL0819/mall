package com.oiios.suibian.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oiios.suibian.R;
import com.oiios.suibian.bean.ShopCarBean;
import com.oiios.suibian.bean.StoreBean;
import com.oiios.suibian.listener.ShopCarChangedListener;
import com.oiios.suibian.model.BmobManager;
import com.oiios.suibian.utils.HttpUtil;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * ��Ϊû��ץȡ������id������������õ�������ΪΨһ��
 */
public class ShopCarAdapter extends BaseExpandableListAdapter {
	private List<StoreBean> groupList; // ������Ϣ
	private Map<String, List<ShopCarBean>> childMap;// ��������Ϊkeyֵ
	private Context context;
	private ShopCarChangedListener listener;

	public ShopCarAdapter(Context context, ShopCarChangedListener listener) {
		super();
		this.groupList = new ArrayList<StoreBean>();
		this.childMap = new HashMap<String, List<ShopCarBean>>();
		this.context = context;
		this.listener = listener;
	}

	public void addChild(String key, List<ShopCarBean> list) {
		childMap.put(key, list);
		notifyDataSetChanged();
	}

	public void addChildAll(Map<String, List<ShopCarBean>> map, boolean flag) {
		if (flag) {
			childMap.clear();
		}
		childMap.putAll(map);
		notifyDataSetChanged();
	}

	public void addGroupAll(List<StoreBean> list, boolean flag) {
		if (flag) {
			groupList.clear();
		}
		groupList.addAll(list);
		notifyDataSetChanged();
	}

	public void addGroup(StoreBean entity) {
		groupList.add(entity);
		notifyDataSetChanged();
	}

	@Override
	public int getGroupCount() {
		return groupList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return childMap.get(groupList.get(groupPosition).getStoreName()).size();
	}

	@Override
	public StoreBean getGroup(int groupPosition) {
		return groupList.get(groupPosition);
	}

	@Override
	public ShopCarBean getChild(int groupPosition, int childPosition) {
		return childMap.get(groupList.get(groupPosition).getStoreName()).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		GroupHolder holder;
		if (convertView == null) {
			holder = new GroupHolder();
			convertView = View.inflate(context, R.layout.inflate_shop_car_group, null);
			holder.cbGroup = (CheckBox) convertView.findViewById(R.id.cbStore);
			holder.tvStoreName = (TextView) convertView.findViewById(R.id.tvStoreName);
			holder.ivStorePhoto = (ImageView) convertView.findViewById(R.id.ivStoreIcon);
			holder.cbEdit = (CheckBox) convertView.findViewById(R.id.cbEdit);
			convertView.setTag(holder);
		} else {
			holder = (GroupHolder) convertView.getTag();
		}
		final StoreBean bean = getGroup(groupPosition);
		// ͨ��getStoreState���ı���̵�ѡ��״̬
		holder.cbGroup.setChecked(bean.isChecked());
		holder.cbGroup.setOnClickListener(new MyOnClickListener(bean));
		holder.tvStoreName.setText(bean.getStoreName());
		holder.cbEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean flag = ((CheckBox) v).isChecked();
				if (flag) {
					flag = true;
					((CheckBox) v).setText("���");
				} else {
					((CheckBox) v).setText("�༭");
				}
				bean.setEditState(flag);
				notifyDataSetChanged();
			}
		});
		return convertView;
	}

	ChildHolder childHolder;

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		if (convertView == null) {
			childHolder = new ChildHolder();
			convertView = View.inflate(context, R.layout.inflate_shop_car_child, null);
			childHolder.cbChild = (CheckBox) convertView.findViewById(R.id.cbGoods);
			childHolder.ivGoodsPhoto = (ImageView) convertView.findViewById(R.id.ivGoodsIcon);
			childHolder.tvDescribe = (TextView) convertView.findViewById(R.id.tvGoodsDescribe);
			childHolder.tvNowPrice = (TextView) convertView.findViewById(R.id.tvNowPrice);
			childHolder.tvOldPrice = (TextView) convertView.findViewById(R.id.tvOldPrice);
			childHolder.tvCount = (TextView) convertView.findViewById(R.id.tvCount);
			childHolder.tvCount2 = (TextView) convertView.findViewById(R.id.tvCount2);
			childHolder.tvAdd = (TextView) convertView.findViewById(R.id.tvAdd);
			childHolder.tvSub = (TextView) convertView.findViewById(R.id.tvSub);
			childHolder.btnDelete = (TextView) convertView.findViewById(R.id.btnDelete);
			childHolder.layoutCount = (LinearLayout) convertView.findViewById(R.id.layoutCount);
			childHolder.layoutContent = (RelativeLayout) convertView.findViewById(R.id.layoutContent);
			convertView.setTag(childHolder);
		} else {
			childHolder = (ChildHolder) convertView.getTag();
		}
		StoreBean storeBean = getGroup(groupPosition);
		ShopCarBean bean = getChild(groupPosition, childPosition);
		childHolder.cbChild.setChecked(bean.isChecked());
		childHolder.cbChild.setOnClickListener(new MyOnClickListener(getGroup(groupPosition), bean));
		HttpUtil.displayImage(bean.getImgUrl(), childHolder.ivGoodsPhoto);
		childHolder.tvCount.setText("x" + bean.getGoodsCount());
		childHolder.tvCount2.setText(bean.getGoodsCount() + "");
		childHolder.tvNowPrice.setText("�� " + bean.getNowPrice());
		childHolder.tvDescribe.setText(bean.getDescible());
		EditOnClickListener editOnClickListener = new EditOnClickListener(groupPosition, childPosition);
		// ɾ��
		childHolder.btnDelete.setOnClickListener(editOnClickListener);
		childHolder.tvAdd.setOnClickListener(editOnClickListener);
		childHolder.tvSub.setOnClickListener(editOnClickListener);
		if (!String.valueOf(bean.getOldPrice()).equals("")) {
			childHolder.tvOldPrice.setText("�� " + bean.getOldPrice() + " ");
			// �в�����
			childHolder.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		} else {
			childHolder.tvOldPrice.setVisibility(View.GONE);
		}
		childHolder.tvDescribe.setText(bean.getDescible());

		if (!storeBean.isEditState()) {// û�е���༭ʱ
			childHolder.layoutContent.setVisibility(View.VISIBLE);
			childHolder.layoutCount.setVisibility(View.GONE);
			childHolder.btnDelete.setVisibility(View.GONE);
		} else {// ����༭ʱ
			childHolder.layoutContent.setVisibility(View.GONE);
			childHolder.layoutCount.setVisibility(View.VISIBLE);
			childHolder.btnDelete.setVisibility(View.VISIBLE);
		}
		return convertView;
	}

	boolean flag = false;

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	// �༭
	private class EditOnClickListener implements OnClickListener {
		private int groupPosition;
		private int childPosition;
		private ShopCarBean bean;

		public EditOnClickListener(int groupPosition, int childPosition) {
			bean = getChild(groupPosition, childPosition);
			this.childPosition = childPosition;
			this.groupPosition = groupPosition;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tvAdd:
				int count = bean.getGoodsCount() + 1;
				bean.setGoodsCount(count);
				childHolder.tvCount2.setText(count + "");
				notifyDataSetChanged();
				BmobManager.updateGoodsCount(bean,listener);
				break;
			case R.id.tvSub:
				int count2 = bean.getGoodsCount();
				if (count2 >= 1) {
					count2 -= 1;
					childHolder.tvCount2.setClickable(true);
				} else {
					childHolder.tvCount2.setClickable(false);
				}
				bean.setGoodsCount(count2);
				childHolder.tvCount2.setText(count2 + "");
				notifyDataSetChanged();
				BmobManager.updateGoodsCount(bean,listener);
				break;
			case R.id.btnDelete:
				listener.removeCarGoods(groupPosition, childPosition);
				break;
			}
		}
	}

	public void removeChild(int groupPosition, int childPosition) {
		List<ShopCarBean> list = childMap.get(groupList.get(groupPosition).getStoreName());
		list.remove(childPosition);
		if (list.size() == 0) {
			groupList.remove(groupPosition);
		}
		notifyDataSetChanged();
	}

	public void removeAll() {
		childMap.clear();
		groupList.clear();
		notifyDataSetChanged();
	}

	// ���checkboxʱ����
	private class MyOnClickListener implements OnClickListener {
		private StoreBean storeBean;
		private ShopCarBean goodsBean;

		public MyOnClickListener(StoreBean storeBean) {
			this.storeBean = storeBean;
		}

		public MyOnClickListener(StoreBean storeBean, ShopCarBean goodsBean) {
			this(storeBean);
			this.goodsBean = goodsBean;
		}

		@Override
		public void onClick(View v) {
			// �õ�checkbox��ѡ��״̬��falseδѡ�У�
			boolean flag = ((CheckBox) v).isChecked();
			switch (v.getId()) {
			case R.id.cbStore:
				storeBean.setChecked(flag);
				// flag = true:ѡ�иõ����µ�������Ʒ
				selectGoodsAll(storeBean, flag);
				if (flag) {
					// �������ȫ��ѡ����ص�checkedStoreAll��ʹȫѡ��checkboxΪѡ��״̬
					if (isCheckedGroupAll()) {
						listener.checkedStoreAll(true);
					}
				} else {
					listener.checkedStoreAll(false);
				}
				break;
			case R.id.cbGoods:
				selectGoods(storeBean, goodsBean, flag);
				break;
			case R.id.cbEdit:
				break;
			}
		}
	}

	// �жϸõ����µ���Ʒ�Ƿ�ȫ��ѡ��
	private boolean isCheckedChildAll(String storeName) {
		boolean flag = true;
		List<ShopCarBean> list = childMap.get(storeName);
		for (ShopCarBean shopCarBean : list) {
			if (!shopCarBean.isChecked()) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	// �Ƿ����е��̶���ѡ��
	public boolean isCheckedGroupAll() {
		boolean flag = true;
		for (StoreBean storeBean : groupList) {
			if (!storeBean.isChecked()) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	// ���ݵ���idѡ�иõ���ȫ����Ʒ
	public void selectGoodsAll(StoreBean storeBean, boolean state) {
		List<ShopCarBean> list = childMap.get(storeBean.getStoreName());
		for (ShopCarBean shopCarEntity : list) {
			selectGoods(storeBean, shopCarEntity, state);
		}
		notifyDataSetChanged();
	}

	// ѡ�����еĵ���
	public void selectStoreAll(boolean storeState) {
		for (int i = 0; i < getGroupCount(); i++) {
			StoreBean entity = getGroup(i);
			entity.setChecked(storeState);
			selectGoodsAll(entity, storeState);
		}
	}

	int freight = 0;
	// ����ѡ����Ʒ�ļ۸�
	public void queryCheckedGoods() {
		float money = 0.0f;
		for (StoreBean storeBean : groupList) {
			for (ShopCarBean shopCarBean : childMap.get(storeBean.getStoreName())) {
				if (shopCarBean.isChecked()) {
					money += shopCarBean.getNowPrice() * shopCarBean.getGoodsCount();
				}
			}
		}
		listener.moneyChange(money, freight);
	}

	// ѡ��ĳһ����Ʒ
	public void selectGoods(StoreBean storeBean, ShopCarBean goodsBean, boolean state) {
		// ������Ʒ֮ǰ��״̬
		goodsBean.setChecked(state);
		// ������еĺ��Ӷ�ѡ�У��ǵ���ҲӦ��ѡ��
		if (isCheckedChildAll(goodsBean.getStoreName())) {
			storeBean.setChecked(true);
			if (isCheckedGroupAll()) {
				listener.checkedStoreAll(true);
			}
		} else {
			storeBean.setChecked(false);
			listener.checkedStoreAll(false);
		}
		notifyDataSetChanged();
		queryCheckedGoods();
	}

	private class GroupHolder {
		CheckBox cbGroup;
		ImageView ivStorePhoto;
		CheckBox cbEdit;
		TextView tvStoreName;
	}

	private class ChildHolder {
		CheckBox cbChild;
		ImageView ivGoodsPhoto;
		TextView tvDescribe;
		TextView tvNowPrice;
		TextView tvOldPrice;
		TextView tvCount;
		TextView tvCount2;
		TextView tvSub;
		TextView tvAdd;
		TextView btnDelete;
		LinearLayout layoutCount;
		RelativeLayout layoutContent;
	}

}
