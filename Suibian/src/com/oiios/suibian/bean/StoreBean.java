package com.oiios.suibian.bean;

public class StoreBean {
	private String storeUrl;//�õ��̵�URL
	private String storeName;//������
	private boolean isChecked;//ѡ��״̬
	private boolean editState;//�༭״̬

	public boolean isEditState() {
		return editState;
	}
	public void setEditState(boolean editState) {
		this.editState = editState;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public String getStoreUrl() {
		return storeUrl;
	}

	public void setStoreUrl(String storeUrl) {
		this.storeUrl = storeUrl;
	}

	@Override
	public String toString() {
		return "StoreBean [storeName=" + storeName + "]";
	}
	
}
