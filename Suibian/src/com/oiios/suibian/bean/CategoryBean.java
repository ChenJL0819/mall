package com.oiios.suibian.bean;
/**
 * categoryFragment��ർ����ʵ����
 * @author admim
 *
 */
public class CategoryBean {
	private long id; 
	private String name;//������
	private String url;//�÷����µ�URL
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "CategoryBean [id=" + id + ", name=" + name + ", url=" + url + "]";
	}
	
}
