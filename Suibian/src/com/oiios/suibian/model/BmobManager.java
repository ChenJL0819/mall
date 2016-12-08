package com.oiios.suibian.model;

import java.util.List;

import com.oiios.suibian.activity.MyApplication;
import com.oiios.suibian.bean.AddressBean;
import com.oiios.suibian.bean.ShopCarBean;
import com.oiios.suibian.bean.User;
import com.oiios.suibian.listener.AddAddressListener;
import com.oiios.suibian.listener.AddressListener;
import com.oiios.suibian.listener.MyListener;
import com.oiios.suibian.listener.ShopCarChangedListener;
import com.oiios.suibian.listener.UpdateUserListener;
import com.oiios.suibian.mvp.IGoodsDetailsView;

import android.content.Context;
import android.util.Log;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class BmobManager {
	// ����û�
	public static void addUser(final Context context, User user, final MyListener listener) {
		// �������ύ�û���Ϣ
		user.signUp(context, new SaveListener() {
			@Override
			public void onSuccess() {
				Log.i("TAG", "ע��ɹ�");
				listener.success("ע��ɹ�");
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Log.i("TAG", "ע��ʧ��" + arg1);
				listener.fail("ע��ʧ��" + arg1);
			}
		});
	}
	public static void updateUser(Context context,User user,final MyListener listener){
		user.update(context, new UpdateListener() {
			@Override
			public void onSuccess() {
				listener.success("�ϴ�ͷ��ɹ�");
			}
			@Override
			public void onFailure(int arg0, String arg1) {
				listener.fail("�ϴ�ͷ��ʧ��"+arg1);
				Log.i("TAG", "�ϴ�ͷ��ʧ��"+arg0+"===="+arg1);
			}
		});
	}
	
	/**
	 * ���빺�ﳵ ǰ���жϹ��ﳵ���Ƿ��Ѵ��ڸ���Ʒ������Ѵ��ڣ����޸ĸ���Ʒ������
	 * 
	 * @param context
	 * @param bean
	 * @param listener
	 */
	public static void addCar(final Context context, final ShopCarBean bean, final IGoodsDetailsView listener) {
		BmobQuery<ShopCarBean> query = new BmobQuery<ShopCarBean>();
		query.addWhereEqualTo("userId", bean.getUserId());
		query.addWhereEqualTo("goodsId", bean.getGoodsId());
		query.findObjects(context, new FindListener<ShopCarBean>() {
			@Override
			public void onSuccess(List<ShopCarBean> arg0) {
				if (arg0.size() == 0) {// �����������
					// �����¼
					bean.save(context, new SaveListener() {
						@Override
						public void onSuccess() {
							Log.i("TAG", "��ӳɹ�");
							listener.resultGoodsToCar("��ӳɹ�");
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							Log.i("TAG", "arg1 = " + arg1);
							listener.resultGoodsToCar("���ʧ��" + arg1);
						}
					});
					return;
				}
				// �������������
				// ͨ��userId��goodsId��ѯֻ��һ����¼
				ShopCarBean carBean = arg0.get(0);
				Log.i("TAG", carBean.toString());
				carBean.setGoodsCount(carBean.getGoodsCount() + 1);
				carBean.update(context, new UpdateListener() {
					@Override
					public void onSuccess() {
						Log.i("TAG", "��ӳɹ�");
						listener.resultGoodsToCar("��ӳɹ�");
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						Log.i("TAG", "���ʧ��");
						listener.resultGoodsToCar("���ʧ��" + arg1);
					}
				});
			}

			@Override
			public void onError(int arg0, String arg1) {
				Log.i("TAG", "arg0 = " + arg0 + ";arg1 = " + arg1);
				listener.resultGoodsToCar("arg0 = " + arg0 + ";arg1 = " + arg1);
			}

		});
	}

	// ��ѯ���ﳵ
	public static void searchShoppingCar(Context context, String userId, final ShopCarChangedListener listener) {
		BmobQuery<ShopCarBean> query = new BmobQuery<ShopCarBean>();
		query.addWhereEqualTo("userId", userId);
		query.findObjects(context, new FindListener<ShopCarBean>() {
			@Override
			public void onSuccess(List<ShopCarBean> arg0) {
				listener.loadShopCarComplete(arg0);
			}

			@Override
			public void onError(int arg0, String arg1) {
				Log.i("TAG", "onError===>" + arg1);
			}
		});
	}

	// ɾ�����ﳵ�е���Ʒ
	public static void delCarGoods(ShopCarBean bean, final ShopCarChangedListener listener) {
		bean.delete(MyApplication.context, new DeleteListener() {
			@Override
			public void onSuccess() {
				Log.i("TAG", "ɾ���ɹ�");
				listener.delGoodsSuccess("ɾ���ɹ�");
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Log.i("TAG", "ɾ��ʧ��" + arg1);
				listener.delGoodsSuccess("ɾ��ʧ��" + arg1);
			}
		});
	}

	// ������Ʒ����
	public static void updateGoodsCount(final ShopCarBean bean,final ShopCarChangedListener listener) {
		bean.update(MyApplication.context, new UpdateListener() {
			@Override
			public void onSuccess() {
				Log.i("TAG", "��Ʒ�������³ɹ�");
				listener.goodsCountSuccess("��Ʒ�������³ɹ�", bean);
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Log.i("TAG", "��Ʒ��������ʧ��" + arg1);
				listener.goodsCountFail("��Ʒ��������ʧ��"+arg1);
			}
		});
	}

	public static void updateUserInfo(Context context, User user, final UpdateUserListener listener) {
		user.update(context, new UpdateListener() {
			@Override
			public void onSuccess() {
				listener.success("�û���Ϣ���³ɹ�");
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				listener.fail("�û���Ϣ����ʧ��" + arg1);
				Log.i("TAG", "�û���Ϣ����ʧ��===>" + arg1);
			}
		});
	}

	// ����ջ���ַ
	public static void addAddress(Context context, AddressBean bean, final AddAddressListener listener) {
		bean.save(context, new SaveListener() {
			@Override
			public void onSuccess() {
				listener.success("����ջ���ַ�ɹ�");
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				listener.fail("����ջ���ַʧ��" + arg1);
			}
		});
	}

	// ��ѯ�ջ���ַ
	public static void searchAddress(Context context, String username, final AddressListener listener) {
		BmobQuery<AddressBean> query = new BmobQuery<AddressBean>();
		query.addWhereEqualTo("username", username);
		query.findObjects(context, new FindListener<AddressBean>() {
			@Override
			public void onSuccess(List<AddressBean> arg0) {
				Log.i("TAG", "��ѯ�ջ���ַ�ɹ�");
				listener.loadAddressComplete(arg0);
			}

			@Override
			public void onError(int arg0, String arg1) {
				Log.i("TAG", "��ѯ�ջ���ַʧ��" + arg1);
			}
		});
	}

	// ɾ���ջ���ַ
	public static void deleteAddress(Context context, AddressBean bean, final AddressListener listener) {
		bean.delete(MyApplication.context, new DeleteListener() {
			@Override
			public void onSuccess() {
				Log.i("TAG", "ɾ���ɹ�");
				listener.deleteSuccess("ɾ���ɹ�");
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Log.i("TAG", "ɾ��ʧ��" + arg1);
				listener.deleteFail("ɾ��ʧ��" + arg1);
			}
		});
	}
}
